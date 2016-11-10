package io.b1ackr0se.bridddle.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.data.model.Shot;

public class HomeAdapter extends RecyclerView.Adapter<ShotViewHolder> {

    List<Shot> shots;

    public HomeAdapter(List<Shot> list) {
        this.shots = list;
    }

    @Override
    public ShotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shot, parent, false));
    }

    @Override
    public void onBindViewHolder(ShotViewHolder holder, int position) {
        Shot shot = shots.get(position);
        Glide.with(holder.shotImageView.getContext())
                .load(shot.getImages().getNormal())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.shotImageView);
    }

    @Override
    public int getItemCount() {
        return shots.size();
    }
}
