package io.b1ackr0se.bridddle.data.remote.dribbble;


import java.util.List;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.data.model.Comment;
import io.b1ackr0se.bridddle.data.model.Like;
import io.b1ackr0se.bridddle.data.model.LikedShot;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import io.b1ackr0se.bridddle.util.AuthenticationManager;
import retrofit2.Response;
import rx.Observable;

public class DataManager {
    private final DribbbleApi dribbbleApi;
    private final AuthenticationManager authenticationManager;

    @Inject
    public DataManager(DribbbleApi dribbbleApi, AuthenticationManager authenticationManager) {
        this.dribbbleApi = dribbbleApi;
        this.authenticationManager = authenticationManager;
    }

    public boolean isLoggedIn() {
        return authenticationManager.isLoggedIn();
    }

    public Observable<List<Shot>> getPopular(int page, int pageSize) {
        return dribbbleApi.getPopular(authenticationManager.buildRequestHeader(), page, pageSize);
    }

    public Observable<User> getAuthenticatedUser() {
        return dribbbleApi.getAuthenticatedUser(authenticationManager.buildRequestHeader());
    }

    public Observable<List<LikedShot>> getUserLikes() {
        return dribbbleApi.getLikesOfAuthenticatedUser(authenticationManager.buildRequestHeader());
    }

    public Observable<User> getUser(int id) {
        return dribbbleApi.getUser(authenticationManager.buildRequestHeader(), id);
    }

    public Observable<Response<Void>> following(int id) {
        return dribbbleApi.following(authenticationManager.buildRequestHeader(), id);
    }

    public Observable<Void> follow(int id) {
        return dribbbleApi.follow(authenticationManager.buildRequestHeader(), id);
    }

    public Observable<Void> unfollow(int id) {
        return dribbbleApi.unfollow(authenticationManager.buildRequestHeader(), id);
    }

    public Observable<List<Shot>> getUserShots(int id, int page, int pageSize) {
        return dribbbleApi.getUserShots(authenticationManager.buildRequestHeader(), id, page, pageSize);
    }

    public Observable<List<Comment>> getComments(int id, int page, int pageSize) {
        return dribbbleApi.getComments(authenticationManager.buildRequestHeader(), id, page, pageSize);
    }

    public Observable<Like> liked(int id) {
        return dribbbleApi.liked(authenticationManager.buildRequestHeader(), id);
    }

    public Observable<Like> like(int id) {
        return dribbbleApi.like(authenticationManager.buildRequestHeader(), id);
    }

    public Observable<Like> unlike(int id) {
        return dribbbleApi.unlike(authenticationManager.buildRequestHeader(), id);
    }

}
