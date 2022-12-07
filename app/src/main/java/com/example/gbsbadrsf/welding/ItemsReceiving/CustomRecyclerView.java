package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {

    public CustomRecyclerView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView (Context context) {
        super(context);
    }

    public CustomRecyclerView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
