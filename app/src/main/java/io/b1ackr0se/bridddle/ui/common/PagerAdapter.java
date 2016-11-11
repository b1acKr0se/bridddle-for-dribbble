package io.b1ackr0se.bridddle.ui.common;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.b1ackr0se.bridddle.ui.home.HomeFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
