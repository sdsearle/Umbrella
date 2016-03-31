package com.nerdery.umbrella.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * GridLayoutManager that supports dynamically changing data and wrapping contents when in a nested RecyclerView.
 *
 * This LayoutManager has only been tested with a RecyclerView with a width of match_parent and a height
 * of wrap_content, and cells that wrap their contents' width and height.
 *
 * @author bherbst
 */
public class WeatherDayGridLayoutManager extends GridLayoutManager {

    private static int WEATHER_CELL_HEIGHT = 100;

    public WeatherDayGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public WeatherDayGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    private int[] mMeasuredDimension = new int[2];

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                          int widthSpec, int heightSpec) {
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        int rows = (int)Math.ceil(getItemCount() / getSpanCount()); // Width of the RecyclerView

        setMeasuredDimension(widthSize, rows * WEATHER_CELL_HEIGHT);
    }
}
