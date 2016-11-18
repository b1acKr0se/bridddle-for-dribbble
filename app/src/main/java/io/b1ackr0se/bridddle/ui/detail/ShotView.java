package io.b1ackr0se.bridddle.ui.detail;


import java.util.List;

import io.b1ackr0se.bridddle.base.BaseView;
import io.b1ackr0se.bridddle.data.model.Comment;

public interface ShotView extends BaseView {

    void bind();

    void showComments(List<Comment> comments);

    void showNoComment();

    void showError();

    void showLike(boolean liked);

    void showLikeInProgress();

    void failedToLike(boolean like);
}
