package io.b1ackr0se.bridddle.ui.user;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.remote.dribbble.DataManager;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class UserPresenter extends BasePresenter<UserView> {
    private static final int PER_PAGE = 10;
    private CompositeSubscription compositeSubscription;
    private Subscription followSubscription;
    private Subscription unfollowSubscription;
    private DataManager dataManager;
    private int userId;
    private int page = 1;

    @Inject
    public UserPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        this.compositeSubscription = new CompositeSubscription();
    }

    void setUserId(int userId) {
        this.userId = userId;
    }

    void loadUser() {
        compositeSubscription.add(dataManager.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> getView().bindUser(user)));
    }

    void loadShots(boolean firstPage) {
        if (firstPage) {
            page = 1;
        }
        compositeSubscription.add(
                dataManager.getUserShots(userId, page, PER_PAGE)
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
        if (!dataManager.isLoggedIn()) {
            getView().showFollowing(false);
            return;
        }

        if (userId == 0) return;

        Subscription subscription = dataManager.following(userId)
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
        if (!dataManager.isLoggedIn()) {
            getView().showFollowing(false);
            getView().performLogin();
            return;
        }

        if (userId == 0) return;

        if (unfollowSubscription != null) compositeSubscription.remove(unfollowSubscription);

        followSubscription = dataManager.follow(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showFollowing(true))
                .subscribe(aVoid -> {}, Throwable::printStackTrace);
        compositeSubscription.add(followSubscription);

    }

    void unfollow() {
        if (!dataManager.isLoggedIn()) {
            getView().showFollowing(false);
            getView().performLogin();
            return;
        }

        if (userId == 0) return;

        if (followSubscription != null) compositeSubscription.remove(followSubscription);

        unfollowSubscription = dataManager.unfollow(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showFollowing(false))
                .subscribe(aVoid -> {}, Throwable::printStackTrace);
        compositeSubscription.add(unfollowSubscription);

    }


    @Override
    public void detachView() {
        super.detachView();
        compositeSubscription.unsubscribe();
    }
}
