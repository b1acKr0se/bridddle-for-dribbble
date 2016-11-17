package io.b1ackr0se.bridddle.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DateUtils {
    public static final String DIFFERENT_SINGLE_HOUR = "%d hour ago";
    public static final String DIFFERENT_MORE_HOURS = "%d hours ago";
    public static final String DIFFERENT_SINGLE_MINUTE = "%d minute ago";
    public static final String DIFFERENT_MORE_MINUTES = "%d minutes ago";
    public static final String UNDEFINED_TIME = "unknown time";

    @SuppressLint("DefaultLocale")
    public static String parse(Date date) {
        if(date == null) return UNDEFINED_TIME;
        long elapsedMillis = new Date().getTime() - date.getTime();
        int quantity;
        if ((int) (elapsedMillis / TimeUnit.DAYS.toMillis(1)) > 0) {
            return DateFormat.getDateInstance().format(date);
        } else if ((quantity = (int) (elapsedMillis / TimeUnit.HOURS.toMillis(1))) > 0) {
            return quantity <= 1 ? String.format(DIFFERENT_SINGLE_HOUR, quantity) :  String.format(DIFFERENT_MORE_HOURS, quantity);
        } else {
            quantity = (int) (elapsedMillis / TimeUnit.MINUTES.toMillis(1));
            return quantity <= 1 ? String.format(DIFFERENT_SINGLE_MINUTE, quantity) :  String.format(DIFFERENT_MORE_MINUTES, quantity);
        }
    }
}