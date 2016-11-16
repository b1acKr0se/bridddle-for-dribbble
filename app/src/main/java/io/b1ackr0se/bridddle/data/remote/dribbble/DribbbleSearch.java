package io.b1ackr0se.bridddle.data.remote.dribbble;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.b1ackr0se.bridddle.data.model.Shot;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DribbbleSearch {

    String ENDPOINT = "https://dribbble.com/";
    String SORT_POPULAR = "";
    String SORT_RECENT = "latest";

    @GET("search")
    Observable<ResponseBody> search(@Query("q") String query,
                                    @Query("page") Integer page,
                                    @Query("per_page") Integer pageSize,
                                    @Query("s") @SortingOrder String sort);

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            SORT_POPULAR,
            SORT_RECENT
    })
    @interface SortingOrder {

    }
}
