package io.b1ackr0se.bridddle.ui.search;

import android.support.annotation.IntDef;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.base.BasePresenter;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.remote.dribbble.DribbbleSearch;
import io.b1ackr0se.bridddle.ui.search.converter.DribbbleSearchConverter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenter extends BasePresenter<SearchView> {
    public static final int SEARCH_QUERY = 0;
    public static final int SEARCH_COLOR = 1;

    private Subscription subscription;
    private DribbbleSearch dribbbleSearch;
    private int page = 1;
    private String query;


    @Inject
    public SearchPresenter(DribbbleSearch dribbbleSearch) {
        this.dribbbleSearch = dribbbleSearch;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void search(int searchType, @DribbbleSearch.SortingOrder String sortingOrder, boolean continuousRequest) {
        if (subscription != null) subscription.unsubscribe();

        if (!continuousRequest) {
            getView().showProgress(true);
            page = 1;
        }

        subscription = (searchType == SEARCH_QUERY ? dribbbleSearch.search(query, page, 20, sortingOrder) : dribbbleSearch.color(query, page, 20, sortingOrder))
                .subscribeOn(Schedulers.io())
                .flatMap(responseBody -> {
                    try {
                        return Observable.just(DribbbleSearchConverter.parseShots(responseBody));
                    } catch (IOException e) {
                        return Observable.error(e);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onNext(List<Shot> list) {
                        page++;
                        getView().showProgress(false);
                        if (list.isEmpty()) getView().showEmpty();
                        else getView().showResult(list, !continuousRequest);
                    }
                });
    }


    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

}
