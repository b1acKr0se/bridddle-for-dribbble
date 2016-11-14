package io.b1ackr0se.bridddle.ui.profile;

import java.util.List;

import io.b1ackr0se.bridddle.base.BaseView;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;

public interface ProfileView extends BaseView {

    void showProfile(User user);

    void showLikedShots(List<Shot> list);

    void showProgress(boolean show);

}
