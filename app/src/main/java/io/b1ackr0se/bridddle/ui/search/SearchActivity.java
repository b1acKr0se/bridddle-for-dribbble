package io.b1ackr0se.bridddle.ui.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleSearch;
import io.b1ackr0se.bridddle.ui.common.OnShotClickListener;
import io.b1ackr0se.bridddle.ui.common.ShotAdapter;
import io.b1ackr0se.bridddle.ui.detail.ShotActivity;
import io.b1ackr0se.bridddle.ui.widget.EndlessRecyclerOnScrollListener;
import io.b1ackr0se.bridddle.ui.widget.ResettableEditText;
import io.b1ackr0se.bridddle.util.SoftKey;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SearchActivity extends BaseActivity implements SearchView, OnShotClickListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_container) View toolbarContainer;
    @BindView(R.id.hex_color) TextView hexColor;
    @BindView(R.id.search_edit_text) ResettableEditText editText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.indication) TextView empty;

    @Inject SearchPresenter searchPresenter;

    private Subscription subscription;

    private ShotAdapter shotAdapter;

    private int originalColor, invertedColor;

    private int searchType;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private final GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            return shotAdapter.getItemViewType(position) == ShotAdapter.TYPE_ITEM ? 1 : 2;
        }
    };

    private List<Shot> shots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchType = getIntent().getIntExtra("search_type", SearchPresenter.SEARCH_QUERY);

        if (searchType == SearchPresenter.SEARCH_COLOR) {
            originalColor = getIntent().getIntExtra("color", Color.BLACK);
            invertedColor = getInvertedColor(originalColor);

            if (invertedColor == Color.BLACK)
                setTheme(R.style.AppTheme_Search_NoActionBar);
            else
                setTheme(R.style.AppTheme_Search_NoActionBar_Dark);
        }

        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        searchPresenter.attachView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupInterface();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(onSpanSizeLookup);

        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                shots.add(null);
                shotAdapter.notifyItemInserted(shots.size() - 1);
                searchPresenter.search(searchType, DribbbleSearch.SORT_POPULAR, true);
            }
        };

        shotAdapter = new ShotAdapter(this, shots, true);
        shotAdapter.setOnShotClickListener(this);

        recyclerView.setClipToPadding(false);
        if (SoftKey.isAvailable(this)) {
            recyclerView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(shotAdapter);
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);

    }

    private void setupInterface() {
        if (searchType == SearchPresenter.SEARCH_QUERY) {
            editText.setVisibility(View.VISIBLE);
            hexColor.setVisibility(View.GONE);
            subscription = RxTextView.textChanges(editText)
                    .debounce(1, TimeUnit.SECONDS)
                    .switchMap(Observable::just)
                    .filter(charSequence -> charSequence != null && charSequence.length() >= 2)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(charSequence -> {
                        searchPresenter.setQuery(charSequence.toString());
                        searchPresenter.search(searchType, DribbbleSearch.SORT_POPULAR, false);
                    });
        } else {
            editText.setVisibility(View.GONE);
            hexColor.setVisibility(View.VISIBLE);

            toolbarContainer.setBackgroundColor(originalColor);

            hexColor.setTextColor(invertedColor);

            String hex = String.format("%06X", (0xFFFFFF & originalColor));
            hexColor.setText("#" + hex);

            searchPresenter.setQuery(hex);
            searchPresenter.search(searchType, DribbbleSearch.SORT_POPULAR, false);
        }
    }

    private int getInvertedColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        if (r * 0.299 + g * 0.587 + b * 0.114 > 186) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_down);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress(boolean show) {
        recyclerView.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmpty() {
        showProgress(false);
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        showProgress(false);
        Toast.makeText(this, "Cannot retrieve search results", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResult(List<Shot> resuls, boolean newQuery) {
        recyclerView.setVisibility(View.VISIBLE);
        if (newQuery) {
            shots.clear();
        } else {
            if (!shots.isEmpty())
                shots.remove(shots.size() - 1);
        }
        endlessRecyclerOnScrollListener.setLoaded();
        shots.addAll(resuls);
        shotAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    @Override
    public void onClick(View view, Shot shot) {
        ShotActivity.navigate(this, view, shot);
    }

    @Override
    public void onLongClick(View view, Shot shot) {

    }
}
