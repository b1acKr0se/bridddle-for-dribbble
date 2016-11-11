package io.b1ackr0se.bridddle.ui.profile;

import io.b1ackr0se.bridddle.base.BaseView;
import io.b1ackr0se.bridddle.data.model.User;

public interface ProfileView extends BaseView {

    void showProfile(User user);

}
