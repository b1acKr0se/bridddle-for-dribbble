package io.b1ackr0se.bridddle.data.remote.dribbble;


import java.util.List;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.data.model.Comment;
import io.b1ackr0se.bridddle.data.model.Like;
import io.b1ackr0se.bridddle.data.model.LikedShot;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import io.b1ackr0se.bridddle.util.SharedPref;
import retrofit2.Response;
import rx.Observable;

public class DataManager {
    private final DribbbleApi dribbbleApi;
    private final SharedPref sharedPref;

    @Inject
    public DataManager(DribbbleApi dribbbleApi, SharedPref sharedPref) {
        this.dribbbleApi = dribbbleApi;
        this.sharedPref = sharedPref;
    }

    public Observable<List<Shot>> getPopular(int page, int pageSize) {
        return dribbbleApi.getPopular(sharedPref.getAccessToken(), page, pageSize);
    }

    public Observable<User> getAuthenticatedUser() {
        return dribbbleApi.getAuthenticatedUser(sharedPref.getAccessToken());
    }

    public Observable<List<LikedShot>> getUserLikes() {
        return dribbbleApi.getLikesOfAuthenticatedUser(sharedPref.getAccessToken());
    }

    public Observable<User> getUser(int id) {
        return dribbbleApi.getUser(sharedPref.getAccessToken(), id);
    }

    public Observable<Response<Void>> following(int id) {
        return dribbbleApi.following(sharedPref.getAccessToken(), id);
    }

    public Observable<Void> follow(int id) {
        return dribbbleApi.follow(sharedPref.getAccessToken(), id);
    }

    public Observable<Void> unfollow(int id) {
        return dribbbleApi.unfollow(sharedPref.getAccessToken(), id);
    }

    public Observable<List<Shot>> getUserShots(int id, int page, int pageSize) {
        return dribbbleApi.getUserShots(sharedPref.getAccessToken(), id, page, pageSize);
    }

    public Observable<List<Comment>> getComments(int id, int page, int pageSize) {
        return dribbbleApi.getComments(sharedPref.getAccessToken(), id, page, pageSize);
    }

    public Observable<Like> liked(int id) {
        return dribbbleApi.liked(sharedPref.getAccessToken(), id);
    }

    public Observable<Like> like(int id) {
        return dribbbleApi.like(sharedPref.getAccessToken(), id);
    }

    public Observable<Void> unlike(int id) {
        return dribbbleApi.unlike(sharedPref.getAccessToken(), id);
    }

}
