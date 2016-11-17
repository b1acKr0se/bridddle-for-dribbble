package io.b1ackr0se.bridddle.ui.search;

import android.os.Build;
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
import io.b1ackr0se.bridddle.ui.EndlessRecyclerOnScrollListener;
import io.b1ackr0se.bridddle.ui.common.ShotAdapter;
import io.b1ackr0se.bridddle.ui.detail.ShotActivity;
import io.b1ackr0se.bridddle.ui.common.OnShotClick;
import io.b1ackr0se.bridddle.ui.widget.ResettableEditText;
import io.b1ackr0se.bridddle.util.SoftKey;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SearchActivity extends BaseActivity implements SearchView, OnShotClick {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_shadow) View shadow;
    @BindView(R.id.search_edit_text) ResettableEditText editText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.indication) TextView empty;

    @Inject SearchPresenter searchPresenter;

    private Subscription subscription;
    private ShotAdapter shotAdapter;

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

        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        searchPresenter.attachView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shadow.setVisibility(View.GONE);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(onSpanSizeLookup);

        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                shots.add(null);
                shotAdapter.notifyItemInserted(shots.size() - 1);
                searchPresenter.search(DribbbleSearch.SORT_POPULAR, true);
            }
        };

        shotAdapter = new ShotAdapter(this, shots, true);
        shotAdapter.setOnShotClick(this);

        recyclerView.setClipToPadding(false);
        if (SoftKey.isAvailable(this)) {
            recyclerView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(shotAdapter);
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);

        subscription = RxTextView.textChanges(editText)
                .debounce(1, TimeUnit.SECONDS)
                .switchMap(Observable::just)
                .filter(charSequence -> charSequence != null && charSequence.length() >= 2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    searchPresenter.setQuery(charSequence.toString());
                    searchPresenter.search(DribbbleSearch.SORT_POPULAR, false);
                });
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
        subscription.unsubscribe();
    }

    @Override
    public void onClick(View view, Shot shot) {
        ShotActivity.navigate(this, view, shot);
    }

    @Override
    public void onLongClick(View view, Shot shot) {

    }
}
