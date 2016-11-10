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
import io.b1ackr0se.bridddle.MainActivity;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.ui.ProgressCallback;

public class HomeFragment extends Fragment implements HomeView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @Inject HomePresenter presenter;

    private HomeAdapter adapter;
    private List<Shot> shots = new ArrayList<>();
    private ProgressCallback progressCallback;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        progressCallback = (MainActivity) getActivity();

        adapter = new HomeAdapter(shots);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        presenter.attachView(this);

        presenter.loadShots();

        return view;
    }

    @Override
    public void showProgress(boolean show) {
        progressCallback.showProgress(show);
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
