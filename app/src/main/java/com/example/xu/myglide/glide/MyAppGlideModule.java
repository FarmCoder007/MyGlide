package com.example.xu.myglide.glide;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.example.xu.myglide.glide.loader.GlideModelLoaderFactory;
import com.example.xu.myglide.utils.FileUtils;

import java.io.File;
import java.io.InputStream;

/**
 * Created by xu on 2018/6/12.
 * Glide  V4 生成 流式 API 一次性调用到 RequestBuilder， RequestOptions 和集成库中所有的选项。
 * Glide 配置项 []
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    public MyAppGlideModule() {
        super();
    }

    /**
     * 设置清单解析，设置为false，避免添加相同的modules两次
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    /**
     * GlideBuilder  设置 默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache DiskCache)
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        // 内存设置一  ：设置手机默认推荐缓存大小  设置内存大小
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
//
//        // 内存设置二  设置指定大小
//        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));

        //  磁盘设置一  ：设置内置指定磁盘缓存路径   大小
        int diskCacheSizeBytes = 1024 * 1024 * 20;   // 2M
        builder.setDiskCache(
                new DiskLruCacheFactory(FileUtils.BASE_LOCAL, diskCacheSizeBytes));
        //  磁盘缓存二   ： 设置外置缓存
//        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
//        builder.setDiskCache(
//                new ExternalCacheDiskCacheFactory(context, "cacheFolderName", diskCacheSizeBytes));

    }

    /**
     * 注册新的加载模式   或 支持新的加载类型
     *
     * @param context
     * @param glide
     * @param registry
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));

        /**
         *  replace   将自己的ModelLoader   替换在Glide 默认 ModelLoader
         *  以后所有的加载都用自己的ModelLoader
         */
//        registry.replace(String.class, InputStream.class, new GlideModelLoaderFactory()); // 替换默认的加载方式
        /**
         *  append 在Glide 默认 ModelLoader  之后追加自己的 MyModelLoader
         *  这样 Glide 将在默认的 ModelLoader 之后尝试它。
         */
//        registry.append(String.class, InputStream.class, new GlideModelLoaderFactory()); // 在末尾的追加加载方式

        /**
         *  你通常想要 prepend【预置】 你的 ModelLoader，
         *  这样 Glide 将在默认的 ModelLoader 之前尝试它。【
         *  GlideApp
         .with(imageView.getContext().getApplicationContext())
         .load(imageUrl)   load  资源时  会先根据GlideModelLoaderFactory中 我们预置的ProgressModelLoader
         里handles 方法 判断 此资源是否适用于新的加载方式。。如果不适合就用Glide默认的加载方式】
         */
        registry.prepend(String.class, InputStream.class, new GlideModelLoaderFactory()); // 在现有之前加入追加新的加载方式
    }
}
