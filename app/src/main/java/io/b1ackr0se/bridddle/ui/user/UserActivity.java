package io.b1ackr0se.bridddle.ui.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import io.b1ackr0se.bridddle.ui.common.OnShotClickListener;
import io.b1ackr0se.bridddle.ui.common.ShotAdapter;
import io.b1ackr0se.bridddle.ui.detail.ShotActivity;
import io.b1ackr0se.bridddle.util.LinkUtils;
import io.b1ackr0se.bridddle.util.SoftKey;

public class UserActivity extends BaseActivity implements UserView, OnShotClickListener {
    @BindView(R.id.nested_scroll_view) NestedScrollView nestedScrollView;
    @BindView(R.id.profile_view) View profileView;
    @BindView(R.id.empty_shot) TextView empty;
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.shot_count) TextView shotCount;
    @BindView(R.id.likes_received_count) TextView likesCount;
    @BindView(R.id.follower_count) TextView followerCount;
    @BindView(R.id.bio) TextView bio;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.recycler_view_container) View container;
    @BindView(R.id.button_container) FrameLayout buttonContainer;
    @BindView(R.id.follow) Button follow;
    @BindView(R.id.unfollow) Button unfollow;

    @Inject UserPresenter presenter;

    @OnClick(R.id.more_latest_shot)
    public void showMoreShots() {

    }

    @OnClick(R.id.follow)
    public void follow() {
        presenter.follow();
    }

    @OnClick(R.id.unfollow)
    public void unfollow() {
        presenter.unfollow();
    }

    private User user;
    private List<Shot> shots = new ArrayList<>();
    private ShotAdapter shotAdapter;

    public static void navigate(Context context, User user) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getActivityComponent().inject(this);

        ButterKnife.bind(this);

        presenter.attachView(this);

        if (SoftKey.isAvailable(this)) {
            recyclerView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        shotAdapter = new ShotAdapter(this, shots, true);
        shotAdapter.setOnShotClickListener(this);

        recyclerView.setClipToPadding(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(shotAdapter);

        user = getIntent().getParcelableExtra("user");

        bindUser();

        presenter.setUserId(user.getId());
        presenter.loadShots(true);
        presenter.checkFollow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void bindUser() {
        Glide.with(this).load(user.getAvatarUrl())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        avatar.setImageDrawable(circularBitmapDrawable);
                    }
                });

        name.setText(user.getName());

        if (user.getLocation() == null) {
            location.setVisibility(View.GONE);
        } else {
            location.setText(user.getLocation());
            location.setVisibility(View.VISIBLE);
        }

        shotCount.setText(String.valueOf(user.getShotsCount()));
        followerCount.setText(String.valueOf(user.getFollowersCount()));
        likesCount.setText(String.valueOf(user.getLikesReceivedCount()));

        if (user.getBio() == null) {
            bio.setVisibility(View.GONE);
        } else {
            LinkUtils.setTextWithLinks(bio, user.getBio());
            bio.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showShots(List<Shot> list) {
        empty.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        shots.addAll(list);
        shotAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyShot() {
        if(shots.isEmpty()) {
            container.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void showFollowing(boolean following) {
        TransitionManager.beginDelayedTransition(buttonContainer);
        follow.setVisibility(following ? View.INVISIBLE : View.VISIBLE);
        unfollow.setVisibility(following ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void performLogin() {

    }

    @Override
    public void onClick(View view, Shot shot) {
        shot.setUser(user);
        ShotActivity.navigate(this, view, shot);
    }

    @Override
    public void onLongClick(View view, Shot shot) {

    }
}
