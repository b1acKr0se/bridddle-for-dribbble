package io.b1ackr0se.bridddle.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import io.b1ackr0se.bridddle.R;

public abstract class TransitionBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_up, R.anim.iddle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_down);
    }
}
