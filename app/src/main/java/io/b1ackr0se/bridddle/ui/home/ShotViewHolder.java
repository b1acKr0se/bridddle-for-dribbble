package io.b1ackr0se.bridddle.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.ui.widget.AspectRatioImageView;


public class ShotViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.shot_image) AspectRatioImageView shotImageView;
    @BindView(R.id.gif_indicator) TextView gifIndicator;

    public ShotViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
