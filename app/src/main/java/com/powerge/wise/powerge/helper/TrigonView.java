package com.powerge.wise.powerge.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/8.
 */

public class TrigonView extends View {
    public TrigonView(Context context) {
        super(context);
    }

    public TrigonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrigonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        //实例化路径
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close();
        canvas.drawPath(path, p);

    }
}
