package io.b1ackr0se.bridddle.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
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
        if (!loading
                && totalItemCount <= (lastVisibleItem + visibleThreshold) && totalItemCount > visibleThreshold) {
            //End of the items
            onLoadMore();
            loading = true;
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public abstract void onLoadMore();
}