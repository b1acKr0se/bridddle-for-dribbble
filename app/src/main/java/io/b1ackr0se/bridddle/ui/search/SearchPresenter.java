package io.b1ackr0se.bridddle.ui.search;

import java.util.List;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleSearch;
import rx.Subscriber;
import rx.Subscription;

public class SearchPresenter extends BasePresenter<SearchView> {

    private Subscription subscription;
    private DribbbleSearch dribbbleSearch;
    private int page = 1;

    @Inject
    public SearchPresenter(DribbbleSearch dribbbleSearch) {
        this.dribbbleSearch = dribbbleSearch;
    }

    public void search(String query, @DribbbleSearch.SortingOrder String sortingOrder, boolean continuousRequest) {
        if (subscription != null) subscription.unsubscribe();

        if (!continuousRequest) page = 1;

        subscription = dribbbleSearch.search(query, page, 20, sortingOrder)
                .doOnNext(shots -> {
                    if (continuousRequest && !shots.isEmpty()) page++;
                })
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Shot> list) {
                        getView().showProgress(false);
                        if (list.isEmpty()) getView().showEmpty();
                        else getView().showResult(list);
                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }
}
