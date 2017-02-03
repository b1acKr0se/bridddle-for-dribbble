package io.b1ackr0se.bridddle.util;


import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

import java.text.NumberFormat;

import javax.inject.Inject;

import io.b1ackr0se.bridddle.injection.ActivityContext;

public class StringUtils {

    private Resources resources;

    @Inject
    public StringUtils(@ActivityContext Activity context) {
        this.resources = context.getResources();
    }

    public String formatWithoutText(Integer count) {
        if (count == null || count == 0)
            return "0";
        return NumberFormat.getIntegerInstance().format(count);
    }

    public String formatWithText(@PluralsRes int pluralResource, @StringRes int zeroCountString, Integer count) {
        if (count == null || count == 0)
            return resources.getString(zeroCountString);
        String formattedNumber = NumberFormat.getIntegerInstance().format(count);
        return resources.getQuantityString(pluralResource, count, formattedNumber);
    }
}
