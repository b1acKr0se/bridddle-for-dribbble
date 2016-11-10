package io.b1ackr0se.bridddle.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;
import io.b1ackr0se.bridddle.ui.widget.SquareImageView;


public class ShotViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.shot_image) SquareImageView shotImageView;

    public ShotViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
