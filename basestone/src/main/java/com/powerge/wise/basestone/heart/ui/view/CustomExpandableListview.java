package com.powerge.wise.basestone.heart.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2018/3/1.
 */

public class CustomExpandableListview extends ExpandableListView {
    public CustomExpandableListview(Context context) {
        super(context);
    }

    public CustomExpandableListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomExpandableListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
