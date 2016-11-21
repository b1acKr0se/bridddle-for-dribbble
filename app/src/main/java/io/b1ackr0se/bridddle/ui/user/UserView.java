package io.b1ackr0se.bridddle.ui.user;


import java.util.List;

import io.b1ackr0se.bridddle.base.BaseView;
import io.b1ackr0se.bridddle.data.model.Shot;

public interface UserView extends BaseView {

    void showShots(List<Shot> shots);

    void showEmptyShot();

    void showError();


}
