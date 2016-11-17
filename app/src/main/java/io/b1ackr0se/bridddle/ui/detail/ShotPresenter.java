package io.b1ackr0se.bridddle.ui.detail;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ShotPresenter extends BasePresenter<ShotView> {
    private Subscription subscription;
    private DribbbleApi dribbbleApi;

    @Inject
    public ShotPresenter(DribbbleApi api) {
        dribbbleApi = api;
    }

    void load(Shot shot) {
        getView().bind();

        if (subscription != null) subscription.unsubscribe();

        subscription = dribbbleApi.getComments(shot.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comments -> {
                    if (comments.isEmpty())
                        getView().showNoComment();
                    else
                        getView().showComments(comments);
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
