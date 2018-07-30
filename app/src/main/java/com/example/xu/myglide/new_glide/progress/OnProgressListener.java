package com.example.xu.myglide.new_glide.progress;


public interface OnProgressListener {
    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}
