package io.b1ackr0se.bridddle.ui.home;


import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.ui.EndlessRecyclerOnScrollListener;

public class HomeFragment extends Fragment implements HomeView, OnShotClick, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.root) FrameLayout root;
    @BindView(R.id.no_internet) View noInternetIndicator;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    @Inject HomePresenter presenter;

    private HomeAdapter adapter;
    private List<Shot> shots = new ArrayList<>();
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private final GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            return adapter.getItemViewType(position) == HomeAdapter.TYPE_ITEM ? 1 : 2;
        }
    };

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), android.R.color.white));

        adapter = new HomeAdapter(getContext(), shots);
        adapter.setOnShotClick(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(onSpanSizeLookup);

        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                shots.add(null);
                adapter.notifyItemInserted(shots.size() - 1);
                presenter.loadShots(false);
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        presenter.attachView(this);

        presenter.loadShots(true);

        return view;
    }

    @Override
    public void showProgress(boolean show) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(show));
    }

    @Override
    public void showShots(List<Shot> list) {
        noInternetIndicator.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        showProgress(false);
        if (!shots.isEmpty())
            shots.remove(shots.size() - 1);
        else recyclerView.scheduleLayoutAnimation();
        endlessRecyclerOnScrollListener.setLoaded();
        shots.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        if(!shots.isEmpty()  && shots.get(shots.size() - 1) == null) {
            shots.remove(shots.size() - 1);
            adapter.notifyItemRemoved(shots.size());
            endlessRecyclerOnScrollListener.setLoaded();
            Toast.makeText(getContext(), "Failed to load more shots", Toast.LENGTH_SHORT).show();
        } else {
            showProgress(false);
            recyclerView.setVisibility(View.GONE);
            noInternetIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(Shot shot) {

    }

    @Override
    public void onLongClick(Shot shot) {

    }

    @Override
    public void onRefresh() {
        TransitionManager.beginDelayedTransition(recyclerView);
        shots.clear();
        adapter.notifyDataSetChanged();
        presenter.loadShots(true);
    }
}
