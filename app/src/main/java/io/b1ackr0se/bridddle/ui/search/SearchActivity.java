package io.b1ackr0se.bridddle.ui.search;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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
import io.b1ackr0se.bridddle.ui.common.ShotAdapter;
import io.b1ackr0se.bridddle.ui.widget.ResettableEditText;
import rx.Observable;
import rx.Subscription;

public class SearchActivity extends BaseActivity implements SearchView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_shadow) View shadow;
    @BindView(R.id.search_edit_text) ResettableEditText editText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Inject SearchPresenter searchPresenter;

    private Subscription subscription;
    private ShotAdapter shotAdapter;
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

        shotAdapter = new ShotAdapter(this, shots);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(shotAdapter);

        subscription = RxTextView.textChanges(editText)
                .debounce(1, TimeUnit.SECONDS)
                .switchMap(Observable::just)
                .filter(charSequence -> charSequence != null && charSequence.length() >= 2)
                .subscribe(charSequence -> searchPresenter.search(charSequence.toString(), DribbbleSearch.SORT_POPULAR, false));
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

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showResult(List<Shot> resuls) {
        shots.clear();
        shots.addAll(resuls);
        shotAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.detachView();
        subscription.unsubscribe();
    }
}
