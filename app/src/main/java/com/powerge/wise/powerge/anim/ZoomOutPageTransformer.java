package com.powerge.wise.powerge.anim;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.powerge.wise.basestone.heart.util.LogUtils;
import com.wisesignsoft.OperationManagement.utils.LogUtil;

/**
 * Created by Administrator on 2018/2/22.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 0.85f;
    private static float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1] 在此区间是能够看到page的
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            view.setAlpha(0);
        }
    }
}
