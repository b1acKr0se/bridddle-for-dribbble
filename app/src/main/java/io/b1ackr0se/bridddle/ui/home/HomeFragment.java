package io.b1ackr0se.bridddle.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;

public class HomeFragment extends Fragment implements HomeView {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @Inject HomePresenter presenter;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        presenter.attachView(this);
        return view;
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showShots(List<Shot> list) {
        
    }

    @Override
    public void showError() {

    }
}
