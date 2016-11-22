package io.b1ackr0se.bridddle.ui.user;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import io.b1ackr0se.bridddle.util.SharedPref;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class UserPresenter extends BasePresenter<UserView> {
    private static final int PER_PAGE = 10;
    private CompositeSubscription compositeSubscription;
    private Subscription followSubscription;
    private Subscription unfollowSubscription;
    private DribbbleApi dribbbleApi;
    private SharedPref sharedPref;
    private int userId;
    private int page = 1;

    @Inject
    public UserPresenter(DribbbleApi dribbbleApi, SharedPref sharedPref) {
        this.dribbbleApi = dribbbleApi;
        this.sharedPref = sharedPref;
        this.compositeSubscription = new CompositeSubscription();
    }

    void setUserId(int userId) {
        this.userId = userId;
    }

    void loadShots(boolean firstPage) {
        if (firstPage) {
            page = 1;
        }
        compositeSubscription.add(
                dribbbleApi.getUserShots(userId, page, PER_PAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        shots -> {
                            if (shots.isEmpty()) {
                                getView().showEmptyShot();
                            } else {
                                getView().showShots(shots);
                                page++;
                            }
                        },
                        throwable -> getView().showError()
                ));
    }

    void checkFollow() {
        if (!sharedPref.isLoggedIn()) {
            getView().showFollowing(false);
            return;
        }

        if (userId == 0) return;

        Subscription subscription = dribbbleApi.following(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getView().showFollowing(response.isSuccessful());
                }, throwable -> {
                    getView().showFollowing(false);
                });
        compositeSubscription.add(subscription);
    }

    void follow() {
        if (!sharedPref.isLoggedIn()) {
            getView().showFollowing(false);
            getView().performLogin();
            return;
        }

        if (userId == 0) return;

        if (unfollowSubscription != null) compositeSubscription.remove(unfollowSubscription);

        followSubscription = dribbbleApi.follow(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showFollowing(true))
                .subscribe();
        compositeSubscription.add(followSubscription);

    }

    void unfollow() {
        if (!sharedPref.isLoggedIn()) {
            getView().showFollowing(false);
            getView().performLogin();
            return;
        }

        if (userId == 0) return;

        if (followSubscription != null) compositeSubscription.remove(followSubscription);

        unfollowSubscription = dribbbleApi.unfollow(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showFollowing(false))
                .subscribe();
        compositeSubscription.add(unfollowSubscription);

    }


}
