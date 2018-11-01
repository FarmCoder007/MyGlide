package com.example.xu.myglide.glide.transformation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 *  定义圆角 图片
 */

public class GlideRoundTransform extends BitmapTransformation {

  private static float radius = 0f;

  /**
   *  默认4db
   * @param context
   */
  public GlideRoundTransform(Context context) {
    this(context, 4);
  }

  public GlideRoundTransform(Context context, int dp) {
    this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    // keyi
  }

  @Override
  protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
    return roundCrop(pool, toTransform, outWidth, outHeight);
  }

  private static Bitmap roundCrop(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
    if (source == null) return null;

    Bitmap result = pool.get(source.getWidth(),
        source.getHeight(), Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(source.getWidth(),
          source.getHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(result);
    Paint paint = new Paint();
    paint.setShader(new BitmapShader(source,
        BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    paint.setAntiAlias(true);
    RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
    float x_alpha = 1.0f*source.getWidth()/outWidth, y_alpha = 1.0f*source.getHeight()/outHeight;
//    Log.e("zj test",String.format("x_alpha: %f, y_alpha: %f",x_alpha,y_alpha));
//    Log.e("zj test",String.format("x_radius: %f, y_radius: %f",x_alpha*radius,y_alpha*radius));
    canvas.drawRoundRect(rectF, x_alpha*radius, y_alpha*radius, paint);
    return result;
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

  }
}
