package io.b1ackr0se.bridddle.ui.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private boolean loading = true;
    private int visibleThreshold = 10;
    int lastVisibleItem, totalItemCount;
    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerOnScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = mLayoutManager.getItemCount();
        if (mLayoutManager instanceof GridLayoutManager)
            lastVisibleItem = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        else
            lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        Log.i("onScrolled", "------------>: " + dy);
        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && totalItemCount > visibleThreshold) {
            onLoadMore();
            loading = true;
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public abstract void onLoadMore();
}