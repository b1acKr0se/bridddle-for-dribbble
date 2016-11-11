package io.b1ackr0se.bridddle.ui.profile;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.User;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class ProfilePresenter extends BasePresenter<ProfileView> {

    private CompositeSubscription subscription;
    User authUser;
    private DribbbleApi api;

    @Inject
    public ProfilePresenter(DribbbleApi api) {
        this.api = api;
        subscription = new CompositeSubscription();
    }

    void getAuthUser() {
        if (authUser != null) subscription.add(Observable.just(authUser).subscribe(user -> getView().showProfile(user)));

        else {
            subscription.add(
                    api.getAuthenticatedUser()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<User>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(User user) {
                                    authUser = user;
                                    getView().showProfile(user);
                                }
                            }));

        }
    }

    @Override
    public void detachView() {
        super.detachView();
        subscription.unsubscribe();
    }
}
