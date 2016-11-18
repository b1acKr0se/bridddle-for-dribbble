package io.b1ackr0se.bridddle.ui.search;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;

public class ColorActivity extends BaseActivity implements SearchView {

    @Inject SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
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
    public void showResult(List<Shot> resuls, boolean newQuery) {

    }
}
