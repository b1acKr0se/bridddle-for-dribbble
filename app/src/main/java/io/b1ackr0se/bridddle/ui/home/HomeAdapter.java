package io.b1ackr0se.bridddle.ui.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.List;
import java.util.Random;

import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.data.model.Shot;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_PROGRESS = 0;
    public static final int TYPE_ITEM = 1;

    private List<Shot> shots;
    private TypedArray placeHolderColor;
    private OnShotClick onShotClick;

    public HomeAdapter(Context context, List<Shot> list) {
        this.shots = list;
        placeHolderColor = context.getResources().obtainTypedArray(R.array.placeholder);
    }

    @Override
    public long getItemId(int position) {
        if (shots.get(position) != null) {
            return shots.get(position).getId();
        }
        return new Random().nextLong();
    }

    void setOnShotClick(OnShotClick onShotClick) {
        this.onShotClick = onShotClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PROGRESS)
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        return new ShotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shot, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return shots.get(position) != null ? TYPE_ITEM : TYPE_PROGRESS;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShotViewHolder) {
            ShotViewHolder holder = (ShotViewHolder) viewHolder;
            Shot shot = shots.get(position);

            holder.itemView.setOnLongClickListener(l -> {
                onShotClick.onLongClick(shot);
                return true;
            });

            holder.shotImageView.setBackgroundColor(placeHolderColor.getColor((position % placeHolderColor.length()), 0));

            holder.gifIndicator.setVisibility(shot.getAnimated() ? View.VISIBLE : View.INVISIBLE);

            Glide.with(holder.shotImageView.getContext())
                    .load(shot.getImages().getNormal())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new GlideDrawableImageViewTarget(holder.shotImageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            resource.stop();
                        }

                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onStop() {
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return shots.size();
    }
}
