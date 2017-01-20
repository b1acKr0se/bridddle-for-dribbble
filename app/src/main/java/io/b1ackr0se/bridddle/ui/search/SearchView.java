package io.b1ackr0se.bridddle.ui.search;

import java.util.List;

import io.b1ackr0se.bridddle.base.BaseView;
import io.b1ackr0se.bridddle.data.model.Shot;


public interface SearchView extends BaseView {

    void showProgress(boolean show);

    void showContinuosProgress(boolean show);

    void showEmpty();

    void showError();

    void showResult(List<Shot> resuls);

    void showMoreResult(List<Shot> results);

}
