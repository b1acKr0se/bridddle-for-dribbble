package io.b1ackr0se.bridddle.ui.profile;

import java.util.List;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.LikedShot;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ProfilePresenter extends BasePresenter<ProfileView> {

    private CompositeSubscription subscription;
    private User authUser;
    private DribbbleApi api;

    @Inject
    public ProfilePresenter(DribbbleApi api) {
        this.api = api;
        subscription = new CompositeSubscription();
    }

    void getAuthUser(boolean forceReload) {
        if (authUser != null && !forceReload) subscription.add(Observable.just(authUser).subscribe(user -> getView().showProfile(user)));

        else {
            subscription.add(
                    api.getAuthenticatedUser()
                            .subscribeOn(Schedulers.io())
                            .doOnNext(user -> loadLikedShots())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<User>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    getView().showProgress(false);
                                }

                                @Override
                                public void onNext(User user) {
                                    authUser = user;
                                    getView().showProfile(user);
                                }
                            }));

        }
    }

    void loadLikedShots() {
        subscription.add(
                api.getLikesOfAuthenticatedUser()
                        .subscribeOn(Schedulers.io())
                        .flatMap(Observable::from)
                        .map(LikedShot::getShot)
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<Shot>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showProgress(false);
                            }

                            @Override
                            public void onNext(List<Shot> list) {
                                getView().showProgress(false);
                                getView().showLikedShots(list);
                            }
                        })
        );
    }

    @Override
    public void detachView() {
        super.detachView();
        subscription.unsubscribe();
    }
}
