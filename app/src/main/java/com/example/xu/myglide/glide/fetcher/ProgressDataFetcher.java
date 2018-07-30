package com.example.xu.myglide.glide.fetcher;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.example.xu.myglide.glide.interceptor.ProgressInterceptor;
import com.example.xu.myglide.glide.manager.ProgressManager;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xu on 2018/6/20.
 * 定制glide 图片拉取方式  网络请求  添加拦截器  传入进度监听
 */

public class ProgressDataFetcher implements DataFetcher<InputStream> {
    private String url; //拉取图片的url
    private ProgressInterceptor.DownloadProgressListener listener;
    private Call progressCall;
    private InputStream stream;
    private boolean isCancelled; // 是否取消请求

    public ProgressDataFetcher(String url) {
        this.url = url;
//        this.listener = listener;
        Log.e("flag","---------------ProgressDataFetcher");
    }

    /**
     * 真正获取数据的地方    网络请求 或者 读取本地
     *
     * @param priority 如果你正在使用网络库或其他队列系统，它可以用于含有优先级的请求。
     * @param callback
     */
    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor(ProgressManager.getProgressListener(url)))  // 添加
                .build();
        try {
            progressCall = client.newCall(request);
            Response response = progressCall.execute();
            if (isCancelled) {
                callback.onLoadFailed(new IOException("Unexpected code cancel"));
            }
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            stream = response.body().byteStream();
        } catch (IOException e) {
            Log.e("flag", "---------------------loadData  IOException:"+e.getMessage());
            e.printStackTrace();
            if(null != callback){
                callback.onLoadFailed(e);
            }
        }
        callback.onDataReady(stream);
    }

    /**
     * 清理I/O 等资源
     */
    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
                stream = null;
            }
        }
        if (progressCall != null) {
            progressCall.cancel();
        }
    }

    /**
     * 取消加载
     */
    @Override
    public void cancel() {
        isCancelled = true;
    }


    /**
     * 自定义加载行为中 要加载的类型  InputStream 【用于获取进度】
     *
     * @return
     */
    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    /**
     * 获取数据方式  枚举  本地还是远程
     *
     * @return
     */
    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
