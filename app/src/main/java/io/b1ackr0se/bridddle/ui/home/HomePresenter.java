package io.b1ackr0se.bridddle.ui.home;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.remote.dribbble.DataManager;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomePresenter extends BasePresenter<HomeView> {
    private static final int PER_PAGE = 50;
    private int currentPage = 1;
    private Subscription subscription;
    private DataManager dataManager;

    @Inject
    public HomePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadShots() {
        loadShots(true);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void increasePage() {
        currentPage++;
    }

    public void loadShots(boolean firstPage) {
        if (firstPage) {
            currentPage = 1;
            getView().showInitialProgress(true);
        } else {
            getView().showContinuousProgress(true);
        }

        subscription = dataManager.getPopular(currentPage, PER_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shots -> {
                    if(firstPage) {
                        getView().showInitialProgress(false);
                        getView().showShots(shots);
                    } else {
                        getView().showContinuousProgress(false);
                        getView().showMoreShots(shots);
                    }
                    increasePage();
                }, throwable -> {
                    if (firstPage) {
                        getView().showInitialProgress(false);
                        getView().showError();
                    } else {
                        getView().showContinuousProgress(false);
                        getView().showLoadMoreError();
                    }
                });
    }
}
