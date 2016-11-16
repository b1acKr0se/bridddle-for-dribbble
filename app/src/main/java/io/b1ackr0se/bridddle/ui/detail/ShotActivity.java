package io.b1ackr0se.bridddle.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.data.model.Shot;
import io.b1ackr0se.bridddle.ui.widget.AspectRatioImageView;
import io.b1ackr0se.bridddle.util.DateUtils;
import io.b1ackr0se.bridddle.util.LinkUtils;


public class ShotActivity extends BaseActivity {

    @BindView(R.id.imageview_shot) AspectRatioImageView shotImageView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar) AppBarLayout appBarLayout;
    @BindView(R.id.shot_title) TextView shotTitle;
    @BindView(R.id.shot_author_image) ImageView shotAuthorImage;
    @BindView(R.id.shot_author_name) TextView shotAuthorName;
    @BindView(R.id.shot_date) TextView shotDate;
    @BindView(R.id.shot_description) TextView shotDescription;
    @BindView(R.id.like_count) TextView likeCount;
    @BindView(R.id.view_count) TextView viewCount;
    @BindView(R.id.response_count) TextView responseCount;

    @OnClick(R.id.like)
    public void onLike() {

    }

    @OnClick(R.id.share)
    public void onShare() {

    }

    @OnClick(R.id.response)
    public void onResponse() {

    }

    public static void navigate(Activity context, View view, Shot shot) {
        Intent intent = new Intent(context, ShotActivity.class);
        intent.putExtra("shot", shot);
        ImageView coverStartView = (ImageView) view.findViewById(R.id.shot_image);
        if(coverStartView.getDrawable() == null) {
            context.startActivity(intent);
            return;
        }
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, coverStartView, "image");
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        Shot shot = getIntent().getParcelableExtra("shot");

        Glide.with(this)
                .load(shot.getImages().getHighestResImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(shotImageView);


        shotTitle.setText(shot.getTitle());

        LinkUtils.setTextWithLinks(shotDescription, LinkUtils.fromHtml(shot.getDescription(), false));

        shotAuthorName.setText(shot.getUser().getName());

        Glide.with(this).load(shot.getUser().getAvatarUrl())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(shotAuthorImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ShotActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        shotAuthorImage.setImageDrawable(circularBitmapDrawable);
                    }
                });


        likeCount.setText(String.valueOf(shot.getLikesCount()) + " LIKES");
        viewCount.setText(String.valueOf(shot.getViewsCount()) + " VIEWS");
        responseCount.setText(String.valueOf(shot.getCommentsCount()) + " RESPONSES");
        shotDate.setText(DateUtils.parse(shot.getCreatedAt()));


    }
}
