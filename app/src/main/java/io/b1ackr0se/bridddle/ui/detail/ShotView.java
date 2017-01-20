package io.b1ackr0se.bridddle.ui.detail;


import java.util.List;

import io.b1ackr0se.bridddle.base.BaseView;
import io.b1ackr0se.bridddle.data.model.Comment;

public interface ShotView extends BaseView {

    void bind();

    void showComments(List<Comment> comments);

    void showLikeInProgress(boolean show);

    void showNoComment();

    void showError();

    void showLike(boolean liked);

    void failedToLike(boolean like);

    void performLogin();
}
