package io.b1ackr0se.bridddle.ui.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.R;


public class ColorView extends FrameLayout {

    @BindView(R.id.color) View colorView;
    @ColorInt private int color;

    public ColorView(Context context, @ColorInt int color) {
        super(context);
        this.color = color;
        if (!isInEditMode()) setup();
    }

    public int getColor() {
        return color;
    }

    private void setup() {
        inflate(getContext(), R.layout.item_color, this);

        ButterKnife.bind(this);

        colorView.setBackgroundColor(color);

    }
}
