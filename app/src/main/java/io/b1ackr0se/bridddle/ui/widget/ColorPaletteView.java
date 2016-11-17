package io.b1ackr0se.bridddle.ui.widget;


import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

public class ColorPaletteView extends LinearLayout {
    private OnColorClickListener listener;

    public ColorPaletteView(Context context) {
        super(context);
    }

    public ColorPaletteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(HORIZONTAL);

    }

    public void setOnColorClickListener(OnColorClickListener listener) {
        this.listener = listener;
    }

    public void setSwatches(List<Palette.Swatch> swatches) {
        TransitionManager.beginDelayedTransition(this);

        if (swatches.size() < 2) {
            setVisibility(GONE);
            return;
        }

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);

        for (Palette.Swatch swatch : swatches) {
            ColorView colorView = new ColorView(getContext(), swatch.getRgb());
            colorView.setLayoutParams(param);
            colorView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onColorClick(view, colorView.getColor());
                }
            });
            addView(colorView);
        }
    }
}
