package io.b1ackr0se.bridddle.ui.detail;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import io.b1ackr0se.bridddle.util.SharedPref;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ShotPresenter extends BasePresenter<ShotView> {
    public static final int PER_PAGE = 40;
    private int page = 1;
    private Shot shot;
    private CompositeSubscription compositeSubscription;
    private DribbbleApi dribbbleApi;
    private SharedPref sharedPref;

    @Inject
    public ShotPresenter(DribbbleApi api, SharedPref sharedPref) {
        compositeSubscription = new CompositeSubscription();
        this.sharedPref = sharedPref;
        dribbbleApi = api;
    }

    void setShot(Shot shot) {
        this.shot = shot;
    }

    void load() {
        getView().bind();
        loadComment(true);
    }

    void loadComment(boolean firstPage) {

        if (shot == null) return;

        if (firstPage) {
            page = 1;
        }

        Subscription subscription = dribbbleApi.getComments(shot.getId(), page, PER_PAGE)
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

        compositeSubscription.add(subscription);

    }

    void checkLike() {
        if (!sharedPref.isLoggedIn()) {
            getView().showLike(false);
            return;
        }

        if (shot == null) return;

        Subscription subscription = dribbbleApi.liked(shot.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(like -> {
                    getView().showLike(like != null);
                }, throwable -> {
                    getView().showLike(false);
                });
        compositeSubscription.add(subscription);
    }

    void like() {
        if (!sharedPref.isLoggedIn()) {
            getView().showLike(false);
            return;
        }

        if (shot == null) return;

        Subscription subscription = dribbbleApi.like(shot.getId())
                .subscribeOn(Schedulers.io())
                .doOnNext(like -> checkLike())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showLikeInProgress())
                .subscribe(result -> {}, throwable -> getView().failedToLike(false));
        compositeSubscription.add(subscription);

    }

    void unlike() {

        if (!sharedPref.isLoggedIn()) {
            getView().showLike(false);
            return;
        }

        if (shot == null) return;

        Subscription subscription = dribbbleApi.unlike(shot.getId())
                .subscribeOn(Schedulers.io())
                .doOnNext(like -> checkLike())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showLikeInProgress())
                .subscribe(result -> {}, throwable -> getView().failedToLike(false));
        compositeSubscription.add(subscription);
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeSubscription.unsubscribe();
    }
}
