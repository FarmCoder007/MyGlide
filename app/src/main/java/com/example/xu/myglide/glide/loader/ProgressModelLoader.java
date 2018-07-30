package com.example.xu.myglide.glide.loader;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import com.example.xu.myglide.glide.fetcher.ProgressDataFetcher;
import com.example.xu.myglide.glide.interceptor.ProgressInterceptor;

import java.io.InputStream;

/**
 * Created by xu on 2018/6/20.
 * 官方文档： https://muyangmin.github.io/glide-docs-cn/tut/custom-modelloader.html#%E7%BC%96%E5%86%99-datafetcher
 * 修改 Glide  默认加载图片的方式   在okhttp里监听图片下载进度
 * 带有进度监听的数据加载器
 */

public class ProgressModelLoader implements ModelLoader<String, InputStream> {
    // 进度监听
    private ProgressInterceptor.DownloadProgressListener progressListener;

    public ProgressModelLoader() {
//        this.progressListener = listener;
        Log.e("flag","---------------ProgressModelLoader");
    }

    /**
     * 构建 数据
     *
     * @param model
     * @param width
     * @param height
     * @param options
     * @return
     */
    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull String model, int width, int height, @NonNull Options options) {
        return new LoadData<>(new ObjectKey(model), new ProgressDataFetcher(model));
    }

    /**
     * 根据传入的  str  来判断是否执行ProgressModelLoader加载
     * 只要以.gif  结尾的都采用这种方式加载  【V4 取消了Using方法  在GlideModule里采取全局注册的方式
     * 从而根据这个方法判断什么资源采取ProgressModelLoader这样加载方式】
     *
     * @param str
     * @return
     */
    @Override
    public boolean handles(@NonNull String str) {
//        return str.endsWith(".gif");
        return true;
    }
}
