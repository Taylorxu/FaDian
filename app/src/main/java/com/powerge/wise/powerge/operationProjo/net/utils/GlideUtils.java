package com.powerge.wise.powerge.operationProjo.net.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.wisesignsoft.OperationManagement.R;


/**
 * Created by ycs on 2016/10/21.
 */
public class GlideUtils {
    public static RequestManager getManager(Object o) {
        if (o instanceof Activity) {
            return Glide.with((Activity) o);
        } else if (o instanceof Fragment) {
            return Glide.with((Fragment) o);
        }
        return null;
    }
    /**
     *  Glide的属性
     *  with(context)
     *  load(url,reId,file,uri)
     *  asBitmap()
     *  asGif()
     *  into(imageView)
     *  placeholder(reId)加载中
     *  error（reId)记载失败
     *  crossFade()淡入显示，如果设置了这个就必须取消asBitmap
     *  override(x,y)最终显示的图片的像素，不是宽高
     *  centerCrop()中心裁剪，缩放填充整个imageview
     *  skipMemoryCache(true)跳过内存缓存，默认是缓存的
     *  diskCacheStrategy(DiskCacheStretey)设置磁盘缓存
     *  DiskCacheStrategy.NONE不缓存
     *  DiskCacheStrategy.SOURCE仅缓存原图（全分辨率的图片）
     *  DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片
     *  DiskCacheStrategy.ALL:缓存所有版本的图片,默认模式
     *  thumbnail(0.1f).//10%的原图大小
     *  thumbnail(DrawableRequestBuilder)用来指定你想要的缩略图
     *  animate（ObjectAnimate）加载图片的属性动画
     *  animate(R.anim.left)加载图片的view动画
     */

    /**
     * 加载网络图片
     */
    public static void loadNetPicture(RequestManager manager, ImageView imageView, String url) {
        manager.load(url)
                .placeholder(R.mipmap.contact)
                .error(R.mipmap.contact)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadRoundPicture(RequestManager manager, final Context context, final ImageView imageView, String url) {
        manager.load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 用来加载圆形图片的第二种方法，.transfrom(new CircleTransform(context))
     */
    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
