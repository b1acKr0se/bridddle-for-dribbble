package io.b1ackr0se.bridddle.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;

public class HomeFragment extends Fragment implements HomeView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Inject HomePresenter presenter;

    private HomeAdapter adapter;
    private List<Shot> shots = new ArrayList<>();

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), android.R.color.white));
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new HomeAdapter(shots);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        presenter.attachView(this);

        showProgress(true);

        return view;
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            swipeRefreshLayout.post(() -> {
               swipeRefreshLayout.setRefreshing(true);
                presenter.loadShots();
            });
        } else
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showShots(List<Shot> list) {
        showProgress(false);
        shots.clear();
        shots.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onRefresh() {
        presenter.loadShots();
    }
}
