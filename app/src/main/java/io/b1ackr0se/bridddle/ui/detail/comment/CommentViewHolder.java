package io.b1ackr0se.bridddle.ui.detail.comment;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.data.model.Comment;
import io.b1ackr0se.bridddle.ui.widget.CustomFontTextView;
import io.b1ackr0se.bridddle.util.DateUtils;
import io.b1ackr0se.bridddle.util.LinkUtils;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) CustomFontTextView name;
    @BindView(R.id.content) CustomFontTextView content;
    @BindView(R.id.time) CustomFontTextView time;
//    @BindView(R.id.like) ImageView like;
//    @BindView(R.id.response) ImageView response;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Comment comment) {
        Glide.with(itemView.getContext()).load(comment.getUser().getAvatarUrl())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(itemView.getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        avatar.setImageDrawable(circularBitmapDrawable);
                    }
                });

        name.setText(comment.getUser().getName());
        content.setText(comment.getBody());
        LinkUtils.setTextWithLinks(content, LinkUtils.fromHtml(comment.getBody(), false));
        time.setText(DateUtils.parse(comment.getCreatedAt()));
    }
}
