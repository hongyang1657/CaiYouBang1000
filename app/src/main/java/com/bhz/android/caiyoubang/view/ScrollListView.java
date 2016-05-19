package com.bhz.android.caiyoubang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义listview
 * Created by Administrator on 2016/4/20.
 */
//com.bhz.android.caiyoubang.view.ScrollListView
public class ScrollListView extends ListView {
    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
