package com.example.administrator.kcapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class AdapterScrollListView extends ListView {
    public AdapterScrollListView(Context context) {
        super(context);
    }

    public AdapterScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdapterScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
