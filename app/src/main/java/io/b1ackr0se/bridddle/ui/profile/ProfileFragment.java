package io.b1ackr0se.bridddle.ui.profile;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.User;

public class ProfileFragment extends Fragment implements ProfileView {

    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.shot_count) TextView shotCount;
    @BindView(R.id.likes_received_count) TextView likesCount;
    @BindView(R.id.follower_count) TextView followerCount;
    @BindView(R.id.bio) TextView bio;

    @Inject ProfilePresenter presenter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            presenter.getAuthUser();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        presenter.attachView(this);

        return view;
    }

    @Override
    public void showProfile(User user) {
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
}
