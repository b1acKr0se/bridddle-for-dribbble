package io.b1ackr0se.bridddle.ui.profile;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.b1ackr0se.bridddle.MainActivity;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.data.model.User;
import io.b1ackr0se.bridddle.ui.home.HomeAdapter;
import io.b1ackr0se.bridddle.ui.login.DribbbleLoginActivity;
import io.b1ackr0se.bridddle.util.SoftKey;

public class ProfileFragment extends Fragment implements ProfileView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.nested_scroll_view) NestedScrollView nestedScrollView;
    @BindView(R.id.profile_view) View profileView;
    @BindView(R.id.login) Button login;
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.shot_count) TextView shotCount;
    @BindView(R.id.likes_received_count) TextView likesCount;
    @BindView(R.id.follower_count) TextView followerCount;
    @BindView(R.id.bio) TextView bio;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    @OnClick(R.id.login)
    public void login() {
        Intent intent = new Intent(getActivity(), DribbbleLoginActivity.class);
        intent.putExtra("command_login", true);
        startActivityForResult(intent, MainActivity.REQUEST_CODE_LOGIN);
    }

    @Inject ProfilePresenter presenter;

    private HomeAdapter homeAdapter;
    private List<Shot> shots = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            nestedScrollView.post(() -> nestedScrollView.scrollTo(0, 0));
            presenter.checkLoginStatus();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        presenter.attachView(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setClipToPadding(false);

        if (SoftKey.isAvailable(getActivity())) {
            recyclerView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.profile_recycler_view_padding_top), 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));
        } else {
            recyclerView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.profile_recycler_view_padding_top), 0, 0);
        }

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        homeAdapter = new HomeAdapter(getContext(), shots);
        recyclerView.setAdapter(homeAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                onLoginStatus(true);
                presenter.getAuthUser(true);
            } else {
                onLoginStatus(false);
                Toast.makeText(getActivity(), "Failed to login!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void showProfile(User user) {
        onLoginStatus(true);
        Glide.with(getContext()).load(user.getAvatarUrl())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        avatar.setImageDrawable(circularBitmapDrawable);
                    }
                });

        name.setText(user.getName());
        username.setText(user.getUsername());
        shotCount.setText(String.valueOf(user.getShotsCount()));
        followerCount.setText(String.valueOf(user.getFollowersCount()));
        likesCount.setText(String.valueOf(user.getLikesReceivedCount()));
        bio.setText(Html.fromHtml("Co-founder &amp; designer of <a href=\\\"https://dribbble.com/dribbble\\\">@Dribbble</a>. Principal of SimpleBits. Aspiring clawhammer banjoist."));
    }

    @Override
    public void showLikedShots(List<Shot> list) {
        shots.clear();
        shots.addAll(list);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean show) {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(show));
    }

    @Override
    public void onLoginStatus(boolean isLoggedIn) {
        profileView.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
        login.setVisibility(isLoggedIn ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        presenter.checkLoginStatus();
    }
}
