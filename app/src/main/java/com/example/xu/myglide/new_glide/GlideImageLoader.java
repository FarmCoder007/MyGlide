package com.example.xu.myglide.new_glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.xu.myglide.glide.GlideApp;
import com.example.xu.myglide.glide.GlideRequest;
import com.example.xu.myglide.new_glide.progress.OnProgressListener;
import com.example.xu.myglide.new_glide.progress.ProgressManager;


import java.lang.ref.WeakReference;


public class GlideImageLoader {

    protected static final String ANDROID_RESOURCE = "android.resource://";
    protected static final String FILE = "file://";
    protected static final String SEPARATOR = "/";

    private WeakReference<ImageView> imageViewWeakReference;
    private String url;

    private RequestOptions requestOptions;

    public static GlideImageLoader create(ImageView imageView) {
        return new GlideImageLoader(imageView);
    }

    private GlideImageLoader(ImageView imageView) {
        imageViewWeakReference = new WeakReference<>(imageView);
    }

    public ImageView getImageView() {
        if (imageViewWeakReference != null) {
            return imageViewWeakReference.get();
        }
        return null;
    }

    public Context getContext() {
        if (getImageView() != null) {
            return getImageView().getContext();
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public RequestOptions getRequestOptions() {
        if (requestOptions == null) {
            requestOptions = new RequestOptions();
        }
        return requestOptions;
    }

    public void setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    protected Uri resId2Uri(@DrawableRes int resId) {
        return Uri.parse(ANDROID_RESOURCE + getContext().getPackageName() + SEPARATOR + resId);
    }

    public GlideImageLoader load(@DrawableRes int resId, @DrawableRes int placeholder, @NonNull Transformation<Bitmap> transformation) {
        return loadImage(resId2Uri(resId), placeholder, transformation);
    }

    protected GlideRequest<Drawable> loadImage(Object obj) {
        if (obj instanceof String) {
            url = (String) obj;
        }
        return GlideApp.with(getContext()).load(obj);
    }

    protected GlideImageLoader loadImage(Object obj, @DrawableRes int placeholder, Transformation<Bitmap> transformation) {
        GlideRequest<Drawable> glideRequest = loadImage(obj);
        if (placeholder != 0) {
            glideRequest = glideRequest.placeholder(placeholder).error(placeholder);
        }

        if (transformation != null) {
            glideRequest = glideRequest.transform(transformation);
        }

        glideRequest = glideRequest.apply(getRequestOptions());

        glideRequest.into(new GlideImageViewTarget(getImageView()));
        return this;
    }

    public GlideImageLoader listener(OnProgressListener onProgressListener) {
        ProgressManager.addListener(url, onProgressListener);
        return this;
    }

    private static class GlideImageViewTarget extends DrawableImageViewTarget {
        public GlideImageViewTarget(ImageView view) {
            super(view);
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            super.onLoadStarted(placeholder);
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            super.onResourceReady(resource, transition);
        }
    }
}
