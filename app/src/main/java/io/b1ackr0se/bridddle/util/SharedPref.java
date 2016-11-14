package io.b1ackr0se.bridddle.util;


import android.content.Context;
import android.preference.PreferenceManager;

import io.b1ackr0se.bridddle.BuildConfig;

public class SharedPref {
    private Context context;

    public SharedPref(Context context) {
        this.context = context;
    }

    public void saveAccessToken(String code) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("access_token", code).apply();
    }

    public boolean isLoggedIn() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("access_token", null) != null;
    }

    public String getAccessToken() {
        String accessToken = PreferenceManager.getDefaultSharedPreferences(context).getString("access_token", null);
        return accessToken == null ? BuildConfig.DRIBBBLE_ACCESS_TOKEN : accessToken;
    }
}
