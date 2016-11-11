package io.b1ackr0se.bridddle.ui.home;


import io.b1ackr0se.bridddle.data.model.Shot;

public interface OnShotClick {
    void onClick(Shot shot);
    void onLongClick(Shot shot);
}
