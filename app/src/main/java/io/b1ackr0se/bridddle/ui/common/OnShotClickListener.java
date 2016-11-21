package io.b1ackr0se.bridddle.ui.common;


import android.view.View;

import io.b1ackr0se.bridddle.data.model.Shot;

public interface OnShotClickListener {
    void onClick(View view, Shot shot);
    void onLongClick(View view, Shot shot);
}
