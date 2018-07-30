package com.example.xu.myglide.glide.interceptor;

import android.os.Looper;
import android.util.Log;

import com.example.xu.myglide.glide.manager.ProgressManager;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by xu on 2018/6/20.
 * Glide 加载图片进度拦截器
 */

public class ProgressInterceptor implements Interceptor {
    private DownloadProgressListener progressListener;

    public ProgressInterceptor(DownloadProgressListener progressListener) {
        this.progressListener = progressListener;
        Log.e("flag", "---------------ProgressInterceptor");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(originalResponse.body(), progressListener))
                .build();
    }

    private static class DownloadProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final DownloadProgressListener progressListener;
        private BufferedSource bufferedSource;

        public DownloadProgressResponseBody(ResponseBody responseBody,
                                            DownloadProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
            Log.e("flag", "---------------DownloadProgressResponseBody");
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    // 每次读取的长度
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    // 已读取的长度
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    // 读取百分比
                    int percentage = (int) ((totalBytesRead * 1f / contentLength()) * 100f);
                    Log.e("flag", "-----------bytesRead:" + totalBytesRead + "---------contentLength" + contentLength() + "----percentage" + percentage);
                    if (null != progressListener) {
                        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                progressListener.update(percentage,  bytesRead == -1);
                            }
                        });
                    }
                    return bytesRead;
                }
            };
        }
    }

    /**
     * 加载监听
     */
    public interface DownloadProgressListener {
        void update(int percentage, boolean done);
    }
}

