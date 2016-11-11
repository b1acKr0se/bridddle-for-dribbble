package io.b1ackr0se.bridddle.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.MainActivity;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.ui.EndlessRecyclerOnScrollListener;
import io.b1ackr0se.bridddle.ui.ProgressCallback;

public class HomeFragment extends Fragment implements HomeView, OnShotClick {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.root) FrameLayout root;
    @Inject HomePresenter presenter;

    private HomeAdapter adapter;
    private List<Shot> shots = new ArrayList<>();
    private ProgressCallback progressCallback;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
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

        progressCallback = (MainActivity) getActivity();

        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));

        adapter = new HomeAdapter(getContext(), shots);
        adapter.setOnShotClick(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(onSpanSizeLookup);

        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                shots.add(null);
                adapter.notifyItemInserted(shots.size() - 1);
                presenter.loadShots();
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        recyclerView.setLayoutManager(gridLayoutManager);
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
        if (!shots.isEmpty())
            shots.remove(shots.size() - 1);
        else recyclerView.scheduleLayoutAnimation();
        endlessRecyclerOnScrollListener.setLoaded();
        shots.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onClick(Shot shot) {

    }

    @Override
    public void onLongClick(Shot shot) {

    }
}
