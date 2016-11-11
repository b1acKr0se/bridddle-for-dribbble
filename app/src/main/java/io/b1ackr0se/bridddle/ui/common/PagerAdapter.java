package io.b1ackr0se.bridddle.ui.common;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.b1ackr0se.bridddle.ui.home.HomeFragment;
import io.b1ackr0se.bridddle.ui.profile.ProfileFragment;
import io.b1ackr0se.bridddle.ui.search.SearchFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 3:
                return new ProfileFragment();
        }
        return new SearchFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
