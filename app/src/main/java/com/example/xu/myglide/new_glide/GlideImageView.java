package com.example.xu.myglide.new_glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.xu.myglide.glide.transformation.CircleTransformation;
import com.example.xu.myglide.new_glide.progress.OnProgressListener;
import com.example.xu.myglide.new_glide.transformation.RadiusTransformation;

public class GlideImageView extends ImageView {

    private boolean enableState = false;
    private float pressedAlpha = 0.4f;
    private float unableAlpha = 0.3f;
    private GlideImageLoader imageLoader;

    public GlideImageView(Context context) {
        this(context, null);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        imageLoader = GlideImageLoader.create(this);
    }

    public GlideImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = GlideImageLoader.create(this);
        }
        return imageLoader;
    }

    public GlideImageView apply(RequestOptions options) {
        getImageLoader().setRequestOptions(options);
        return this;
    }

    public GlideImageView centerCrop() {
        RequestOptions options = getImageLoader().getRequestOptions();
        getImageLoader().setRequestOptions(options.centerCrop());
        return this;
    }

    public GlideImageView fitCenter() {
        RequestOptions options = getImageLoader().getRequestOptions();
        getImageLoader().setRequestOptions(options.fitCenter());
        return this;
    }

    public GlideImageView diskCacheStrategy(@NonNull DiskCacheStrategy strategy) {
        RequestOptions options = getImageLoader().getRequestOptions();
        getImageLoader().setRequestOptions(options.diskCacheStrategy(strategy));
        return this;
    }

    public void load(String url) {
        load(url, 0);
    }

    public void load(String url, @DrawableRes int placeholder) {
        load(url, placeholder, null);
    }

    public void load(String url, @DrawableRes int placeholder, int radius) {
        load(url, placeholder, radius, null);
    }

    public void load(String url, @DrawableRes int placeholder, OnProgressListener onProgressListener) {
        load(url, placeholder, null, onProgressListener);
    }

    public void load(String url, @DrawableRes int placeholder, int radius, OnProgressListener onProgressListener) {
        load(url, placeholder, new RadiusTransformation(getContext(), radius), onProgressListener);
    }

    public void load(Object obj, @DrawableRes int placeholder, Transformation<Bitmap> transformation, OnProgressListener onProgressListener) {
        getImageLoader().loadImage(obj, placeholder, transformation).listener(onProgressListener);
    }

    public void loadCircle(String url) {
        loadCircle(url, 0);
    }

    public void loadCircle(String url, @DrawableRes int placeholder) {
        loadCircle(url, placeholder, null);
    }

    public void loadCircle(String url, @DrawableRes int placeholder, OnProgressListener onProgressListener) {
        load(url, placeholder, new CircleTransformation(), onProgressListener);
    }

    public void loadDrawable(@DrawableRes int resId) {
        loadDrawable(resId, 0);
    }

    public void loadDrawable(@DrawableRes int resId, @DrawableRes int placeholder) {
        loadDrawable(resId, placeholder, null);
    }

    public void loadDrawable(@DrawableRes int resId, @DrawableRes int placeholder, @NonNull Transformation<Bitmap> transformation) {
        getImageLoader().load(resId, placeholder, transformation);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (enableState) {
            if (isPressed()) {
                setAlpha(pressedAlpha);
            } else if (!isEnabled()) {
                setAlpha(unableAlpha);
            } else {
                setAlpha(1.0f);
            }
        }
    }
}
