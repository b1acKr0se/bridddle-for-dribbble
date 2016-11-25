package io.b1ackr0se.bridddle;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.b1ackr0se.bridddle.base.BaseActivity;
import io.b1ackr0se.bridddle.ui.common.PagerAdapter;
import io.b1ackr0se.bridddle.ui.login.DribbbleLoginActivity;
import io.b1ackr0se.bridddle.ui.search.SearchActivity;
import io.b1ackr0se.bridddle.ui.search.SearchPresenter;
import io.b1ackr0se.bridddle.ui.widget.AppBarStateListener;
import io.b1ackr0se.bridddle.ui.widget.SlideDisabledViewPager;

public class MainActivity extends BaseActivity implements OnTabSelectListener {
    public static final int REQUEST_CODE_LOGIN = 1;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.app_bar_layout) AppBarLayout appBarLayout;
    @BindView(R.id.view_pager) SlideDisabledViewPager viewPager;
    @BindView(R.id.bottom_bar) BottomBar bottomBar;

    private boolean isToolbarShowing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                isToolbarShowing = state == State.EXPANDED;
            }
        });

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        bottomBar.getTabAtPosition(1).setOnClickListener(view -> search());
        bottomBar.setOnTabSelectListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.login) {
            startActivityForResult(new Intent(this, DribbbleLoginActivity.class), REQUEST_CODE_LOGIN);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onLoggedIn() {

    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.dashboad:
                changeTab(0);
                break;
            case R.id.favorite:
                changeTab(1);
                break;
            case R.id.profile:
                changeTab(2);
                break;
        }
    }

    private void search() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("search_type", SearchPresenter.SEARCH_QUERY);
        startActivity(intent);
    }

    private void changeTab(int position) {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(viewPager, View.ALPHA, 0, 1);
        alphaAnimation.setDuration(200);
        alphaAnimation.start();
        viewPager.setCurrentItem(position, false);
        if (isToolbarShowing)
            return;
        appBarLayout.setExpanded(true, true);
    }
}
