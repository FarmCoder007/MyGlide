package com.example.xu.myglide.glide.extension;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by xu on 2018/6/19.
 *
 *  Glide 生成api  可直接调用  RequestBuilder  和 RequestOptions
 *   @GlideExtension 注解的静态方法用于扩展 RequestOptions
 */
@GlideExtension
public class ThumbnailExtension {
    private static final int MINI_THUMB_SIZE_WIDTH = 800;
    private static final int MINI_THUMB_SIZE_HEIGHT = 800;
    private ThumbnailExtension() {
    }

    /**
     *  指定获取指定宽高的api
     *  单位像素
     * @param options
     */
    @GlideOption
    public static void miniThumb(RequestOptions options) {
        options
                .fitCenter()
                .override(MINI_THUMB_SIZE_WIDTH,MINI_THUMB_SIZE_HEIGHT);
    }
}
