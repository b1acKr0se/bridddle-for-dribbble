package io.b1ackr0se.bridddle.ui.user;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import io.b1ackr0se.bridddle.util.SharedPref;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class UserPresenter extends BasePresenter<UserView> {
    private static final int PER_PAGE = 10;
    private CompositeSubscription compositeSubscription;
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


}
