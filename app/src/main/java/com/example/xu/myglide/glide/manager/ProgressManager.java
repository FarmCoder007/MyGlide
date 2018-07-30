package com.example.xu.myglide.glide.manager;

import android.text.TextUtils;
import android.util.Log;

import com.example.xu.myglide.glide.interceptor.ProgressInterceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xu on 2018/6/21.
 * 维护所有进度监听
 */

public class ProgressManager {
    /**
     * 通过
     */
    private static Map<String, ProgressInterceptor.DownloadProgressListener> listenersMap = Collections.synchronizedMap(new HashMap<>());
//    private static OkHttpClient okHttpClient;

    private ProgressManager() {
    }

//    public static OkHttpClient getOkHttpClient() {
//        if (okHttpClient == null) {
//            okHttpClient = new OkHttpClient.Builder()
//                    .addNetworkInterceptor(chain -> {
//                        Request request = chain.request();
//                        Response response = chain.proceed(request);
//                        return response.newBuilder()
//                                .body(new ProgressResponseBody(request.url().toString(), LISTENER, response.body()))
//                                .build();
//                    })
//                    .build();
//        }
//        return okHttpClient;
//    }
//
//    private static final ProgressResponseBody.InternalProgressListener LISTENER = (url, bytesRead, totalBytes) -> {
//        OnProgressListener onProgressListener = getProgressListener(url);
//        if (onProgressListener != null) {
//            int percentage = (int) ((bytesRead * 1f / totalBytes) * 100f);
//            boolean isComplete = percentage >= 100;
//            onProgressListener.onProgress(isComplete, percentage, bytesRead, totalBytes);
//            if (isComplete) {
//                removeListener(url);
//            }
//        }
//    };

    public static void addListener(String url, ProgressInterceptor.DownloadProgressListener listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {

            listenersMap.put(url, listener);
        }
    }

    public static void removeListener(String url) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url);
        }
    }

    public static ProgressInterceptor.DownloadProgressListener getProgressListener(String url) {
        if (TextUtils.isEmpty(url) || listenersMap == null || listenersMap.size() == 0) {
            return null;
        }

        ProgressInterceptor.DownloadProgressListener listenerWeakReference = listenersMap.get(url);
        if (listenerWeakReference != null) {
            Log.e("flag", "---------------------getProgressListener");
            return listenerWeakReference;
        }
        return null;
    }
}
