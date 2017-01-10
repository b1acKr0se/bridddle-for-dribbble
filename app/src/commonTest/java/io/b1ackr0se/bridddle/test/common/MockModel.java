package io.b1ackr0se.bridddle.test.common;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.b1ackr0se.bridddle.data.model.AccessToken;
import io.b1ackr0se.bridddle.data.model.Comment;
import io.b1ackr0se.bridddle.data.model.Images;
import io.b1ackr0se.bridddle.data.model.Like;
import io.b1ackr0se.bridddle.data.model.LikedShot;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;

public class MockModel {
    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static int randomInt() {
        return new Random().nextInt();
    }

    public static Shot newShot() {
        Shot shot = new Shot();
        shot.setId(1);
        shot.setTitle(randomString());
        shot.setDescription(randomString());
        shot.setImages(newImages());
        shot.setViewsCount(randomInt());
        shot.setLikesCount(randomInt());
        shot.setCommentsCount(randomInt());
        shot.setCreatedAt(new Date());
        shot.setAnimated(Math.random() < 0.5);
        shot.setUser(newUser());

        return shot;
    }

    public static Images newImages() {
        Images image = new Images();
        image.setNormal("https://d13yacurqjgara.cloudfront.net/users/120724/screenshots/3196285/nowhere-stairs-2_1x.jpg");
        return image;
    }

    public static User newUser() {
        User user = new User();
        user.setId(100);
        user.setName(randomString());
        user.setUsername(randomString());
        user.setAvatarUrl("https://d13yacurqjgara.cloudfront.net/users/123657/avatars/small/8703d989765b74eb335037b05176d0c8.png?1464068871");
        user.setBio(randomString());
        user.setLocation(randomString());
        user.setCommentsReceivedCount(randomInt());
        user.setFollowersCount(randomInt());
        user.setLikesCount(randomInt());
        user.setFollowingsCount(randomInt());
        user.setLikesReceivedCount(randomInt());
        user.setShotsCount(randomInt());

        return user;
    }

    public static Comment newComment() {
        Comment comment = new Comment();
        comment.setId(102);
        comment.setUser(newUser());
        comment.setLikesCount(randomInt());
        comment.setBody(randomString());
        comment.setCreatedAt(new Date());
        return comment;
    }

    public static Like newLike() {
        return new Like(100, new Date(), newUser(), newShot());
    }

    public static LikedShot newLikedShot() {
        LikedShot shot = new LikedShot();
        shot.setShot(newShot());
        return shot;
    }

    public static AccessToken newAccessToken() {
        AccessToken accessToken = new AccessToken();
        accessToken.scope = randomString();
        accessToken.accessToken = randomString();
        accessToken.tokenType = randomString();
        return accessToken;
    }

    public static List<Shot> newShotList(int size) {
        List<Shot> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(newShot());
        }
        return list;
    }

    public static List<Comment> newCommentList(int size) {
        List<Comment> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(newComment());
        }
        return list;
    }

    public static List<LikedShot> newLikedShotList(int size) {
        List<LikedShot> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(newLikedShot());
        }
        return list;
    }
}
