package io.b1ackr0se.bridddle.ui.home;

import java.util.List;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleApi;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomePresenter extends BasePresenter<HomeView> {
    private Subscription subscription;
    private DribbbleApi dribbbleApi;

    @Inject
    public HomePresenter(DribbbleApi api) {
        dribbbleApi = api;
    }

    @Override
    public void detachView() {
        super.detachView();
        subscription.unsubscribe();
    }

    void loadShots() {
        subscription = dribbbleApi.getPopular(0, 50).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showError();
                    }

                    @Override
                    public void onNext(List<Shot> shots) {
                        getView().showShots(shots);
                    }
                });
    }
}
