package io.b1ackr0se.bridddle.data.remote.dribbble;


import java.util.List;

import io.b1ackr0se.bridddle.data.model.AccessToken;
import io.b1ackr0se.bridddle.data.model.Comment;
import io.b1ackr0se.bridddle.data.model.LikedShot;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface DribbbleApi {
    String ENDPOINT = "https://api.dribbble.com/";

    @GET("v1/shots")
    Observable<List<Shot>> getPopular(@Query("page") Integer page, @Query("per_page") Integer pageSize);

    @GET("v1/user")
    Observable<User> getAuthenticatedUser();

    @GET("v1/user/likes")
    Observable<List<LikedShot>> getLikesOfAuthenticatedUser();

    @GET("v1/shots/{id}/comments")
    Observable<List<Comment>> getComments(@Path("id") int id);
}
