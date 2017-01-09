package io.b1ackr0se.bridddle.ui.home;

import java.util.List;

import io.b1ackr0se.bridddle.base.BaseView;
import io.b1ackr0se.bridddle.data.model.Shot;


public interface HomeView extends BaseView {
    void showInitialProgress(boolean show);

    void showShots(List<Shot> list);

    void showMoreShots(List<Shot> list);

    void showContinuousProgress(boolean show);

    void showLoadMoreError();

    void showError();
}
