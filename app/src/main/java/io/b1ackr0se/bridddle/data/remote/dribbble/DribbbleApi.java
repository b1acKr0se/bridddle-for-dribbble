package io.b1ackr0se.bridddle.data.remote.dribbble;


import java.util.List;

import io.b1ackr0se.bridddle.data.model.Shot;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DribbbleApi {
    String ENDPOINT = "https://api.dribbble.com/";

    @GET("v1/shots")
    Observable<List<Shot>> getPopular(@Query("page") Integer page, @Query("per_page") Integer pageSize);
}
