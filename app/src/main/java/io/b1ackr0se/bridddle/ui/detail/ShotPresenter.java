package io.b1ackr0se.bridddle.ui.detail;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ShotPresenter extends BasePresenter<ShotView> {
    public static final int PER_PAGE = 40;
    private int page = 1;
    private Shot shot;
    private Subscription subscription;
    private DribbbleApi dribbbleApi;


    @Inject
    public ShotPresenter(DribbbleApi api) {
        dribbbleApi = api;
    }

    void load(Shot shot) {
        getView().bind();

        this.shot = shot;

        loadComment(true);
    }

    void loadComment(boolean firstPage) {
        if (firstPage) {
            page = 1;
        }

        if (subscription != null) subscription.unsubscribe();

        subscription = dribbbleApi.getComments(shot.getId(), page, PER_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comments -> {
                    if (comments.isEmpty())
                        getView().showNoComment();
                    else {
                        page++;
                        getView().showComments(comments);
                    }
                }, throwable -> {
                    getView().showError();
                });

    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }
}
