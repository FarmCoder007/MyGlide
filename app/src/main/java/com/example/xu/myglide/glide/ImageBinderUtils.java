package com.example.xu.myglide.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.xu.myglide.R;
import com.example.xu.myglide.glide.interceptor.ProgressInterceptor;
import com.example.xu.myglide.glide.manager.ProgressManager;
import com.example.xu.myglide.glide.target.CustomImageViewTarget;
import com.example.xu.myglide.glide.transformation.CircleTransformation;
import com.example.xu.myglide.glide.transformation.GlideRoundTransform;
import com.example.xu.myglide.utils.DisplayUtil;
import com.example.xu.myglide.utils.FileUtils;

import java.io.File;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/**
 * Created by xu on 2018/6/13.
 * 图片 家咋子处理工具
 * 基于Glide
 */

public class ImageBinderUtils {

    /**
     * 生成api  加载线上数据
     */
    public static void bindNetImage(ImageView imageView, String imageUrl) {
        GlideApp
                .with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)  // 获取资源前占位符
                .into(imageView);
        Log.e("flag", "-------------------------bindNetImage--" + imageUrl);
    }

    /**
     * 绑定本地图片
     *
     * @param imageView
     * @param resid
     */
    public static void bindLoacImage(ImageView imageView, @DrawableRes int resid) {
        GlideApp
                .with(imageView.getContext().getApplicationContext())
                .load(resid)
                .placeholder(R.drawable.ic_launcher_background)  // 获取资源前占位符
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

    /**
     * 按照自定义大小缩略指定图片
     */
    public static void bindThumbnailImage(ImageView imageView, String imageUrl) {
        GlideApp
                .with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(100)
                .into(imageView);
    }

    /**
     * 预先加载缩略   三种方式
     * imageView  目标view
     * imageUrl   原始图
     * thumbUrl  指定预加载缩略图url
     */
    public static void preLoadThumb(ImageView imageView, String imageUrl, String thumbUrl) {
        GlideApp
                .with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .thumbnail(GlideApp.with(imageView).load(thumbUrl)) // 1 加载分辨率较低的url
//                .thumbnail(/*sizeMultiplier=*/ 0.15f) //  2 预加载原始图像的15%
//                .thumbnail(GlideApp.with(imageView).load(imageUrl).override(100,100)) // 3 只有一个url时加载这个url指定分辨率的图片
//                .thumbnail(GlideApp.with(imageView).load(R.mipmap.ic_launcher).override(100,100)) // 4 加载本地指定分辨率的缩略图
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

    /**
     * 添加过渡效果   : 交叉淡入withCrossFade(2000)  [可以指定交叉淡入的时间]  ...
     * imageView  目标view
     * imageUrl   原始图
     */
    public static void transitionLoadImage(ImageView imageView, String imageUrl) {
        GlideApp
                .with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(/*sizeMultiplier=*/ 0.15f)  //  预加载原始图像的15%
                .transition(withCrossFade())
                .into(imageView);
    }

    /**
     * 适用于详情页大图 动画加载
     */
    public static void animLoadImage(ImageView imageView, String imageUrl) {
        GlideApp
                .with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(GlideApp.with(imageView).load(R.mipmap.ic_launcher))
                .transition(new DrawableTransitionOptions().transition(R.anim.glide_anim))
                .into(imageView);
    }

    /**
     * 失败时开始新的请求
     */
    public static void fallBackLoadImage(ImageView imageView, String imageUrl, String fallbackUrl) {
        GlideApp
                .with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(Glide.with(imageView).load(fallbackUrl))  // 主请求加载失败时  加载失败后的指定fallbackUrl
                .into(imageView);
    }

    /**
     * 带下载进度的请求图片
     * 添加了监听  和url绑定 ，，待使用监听时取出来
     * 一般用于加载gif  【由MyAppGlideModule 过滤哪些请求可以支持】
     */
    public static void progressLoadImage(ImageView imageView, String imageUrl, ProgressInterceptor.DownloadProgressListener downloadProgressListener) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
        ProgressManager.addListener(imageUrl, downloadProgressListener);
    }

    /**
     * 加载圆形图片 方法一  使用CircleTransformation
     *
     * @param imageView
     * @param imageUrl
     */
    public static void loadCircleImage(ImageView imageView, String imageUrl) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .transform(new CircleTransformation())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    /**
     * 加载圆形图片   方法二   利用Glide  处理圆形bitmap  circleCrop
     *
     * @param imageView
     * @param imageUrl
     */
    public static void loadCircleCropImage(ImageView imageView, String imageUrl) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    /**
     * 加载指定圆角图片   应用Transform
     *
     * @param imageView
     * @param imageUrl
     * @param radius    圆角半径   单位 dp
     */
    public static void loadRoundImage(ImageView imageView, String imageUrl, int radius) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .load(imageUrl)
                .transform(new GlideRoundTransform(imageView.getContext().getApplicationContext(), 8))
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }


    /**
     * 跳过缓存. 每次都从服务端获取最新.
     * diskCacheStrategy: 磁盘缓存
     * skipMemoryCache:内存缓存
     */
    public static void skipCacheLoadImage(ImageView imageView, String imageUrl) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    /**
     * 自定义view大小   由于 Glide  对WRAP_CONTENT 支持不是很好  顾可以代码改变View大小
     * width  单位dp
     * height 单位dp
     */
    public static void customImageSize(ImageView imageView, String url, int width, int height) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .asBitmap()
                .load(url)
                .dontAnimate()
                .placeholder(R.mipmap.ic_launcher)
                .into(new CustomImageViewTarget(imageView, width, height));
    }

    /**
     * 获取的bitmap
     *
     * @param imageView
     * @param url
     */
    public static void getBitmapFromUrl(ImageView imageView, String url, BitmapLoadListener bitmapLoadListener) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        bitmapLoadListener.onBitmapLoadComplete(resource);
                    }
                });
    }

    /**
     * 获取指定宽高的bitmap
     *
     * @param imageView
     * @param url
     * @param width     指定的宽高  单位  dp
     * @param height
     */
    public static void getBitmapFromUrl(ImageView imageView, String url, int width, int height, BitmapLoadListener bitmapLoadListener) {
        GlideApp.with(imageView.getContext().getApplicationContext())
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>(DisplayUtil.dp2px(imageView.getContext().getApplicationContext(), width),
                        DisplayUtil.dp2px(imageView.getContext().getApplicationContext(), height)) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bitmapLoadListener.onBitmapLoadComplete(resource);
                    }
                });
    }

    /**
     * 仅播放Gif
     * 获取加载的target  控制播放暂停
     */
    public static Target loadGif(ImageView imageView, String url) {
        return GlideApp.with(imageView.getContext().getApplicationContext())
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

    /**
     * 清除内存缓存.   特殊情况下调用
     * 主线程调用
     */
    public static void clearMemoryCache(Context context) {
        // This method must be called on the main thread.
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存.
     */
    public static void clearDiskCache(Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // This method must be called on a background thread.
                Glide.get(context).clearDiskCache();
                return null;
            }
        }.execute();
    }

    /**
     * recyclerView  加载列表    预缓存方法一：  preload   【必须主线程】
     */
    public static void preloadImageToLocal(Context context, String url) {
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .preload();
//                 .preload(width,height);  // 预缓存指定宽高
        Log.e("flag", "-------------------------cacheImageToLocal----url：" + url);
    }

    /**
     * recyclerView  加载列表    预缓存方法二：  download  【必须子线程】  安照DiskCacheStrategy.RESOURCE缓存
     * 加载的时候不写diskCacheStrategy  或者 .diskCacheStrategy(DiskCacheStrategy.RESOURCE) 可使用预先缓存好的
     * download  在子线程中执行
     *
     * @param context
     * @param url
     */
    public static void downloadImageToLocal(Context context, String url) {
        try {
            FutureTarget<File> target = GlideApp
                    .with(context)
                    .download(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).getDownloadOnlyRequest().submit();
            final File imageFile = target.get();
            FileUtils.renameFile(imageFile.getPath(), FileUtils.BASE_LOCAL + imageFile.getName());
            Log.e("flag", "-------------------" + imageFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("flag", "-------------------Exception" + e.getMessage());
        }
    }

    /**
     * 获取bitmap加载进度
     */
    public interface BitmapLoadListener {
        void onBitmapLoadComplete(Bitmap bitmap);
    }
}
