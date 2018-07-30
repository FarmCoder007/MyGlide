package com.example.xu.myglide.glide.target;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.example.xu.myglide.utils.DisplayUtil;

/**
 * Created by xu on 2018/6/25.
 *  传入imageView  动态改变view大小
 */

public class CustomImageViewTarget extends ImageViewTarget<Bitmap> {

    private int width, height;

    public CustomImageViewTarget(ImageView view) {
        super(view);
    }

    public CustomImageViewTarget(ImageView view, int width, int height) {
        super(view);
        this.width = DisplayUtil.dp2px(view.getContext(),width);
        this.height =  DisplayUtil.dp2px(view.getContext(),height);
    }

    @Override
    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
        super.onResourceReady(bitmap,transition);
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
        view.setImageBitmap(resource);
    }

    @Override
    public void getSize(SizeReadyCallback cb) {
        if (width > 0 && height > 0) {
            cb.onSizeReady(width, height);
            return;
        }
        super.getSize(cb);
    }
}
