package io.b1ackr0se.bridddle.data.remote.dribbble;

import io.b1ackr0se.bridddle.data.model.AccessToken;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface DribbbleAuthenticator {
    String ENDPOINT = "https://dribbble.com/";

    @POST("/oauth/token")
    Observable<AccessToken> getAccessToken(@Query("client_id") String client_id,
                                           @Query("client_secret") String client_secret,
                                           @Query("code") String code);
}
