package com.example.xu.myglide.glide.loader;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * Created by xu on 2018/6/20.
 *  注册自定义的model
 */

public class GlideModelLoaderFactory implements ModelLoaderFactory {


    @NonNull
    @Override
    public ModelLoader build(@NonNull MultiModelLoaderFactory multiFactory) {
        return new ProgressModelLoader();
    }

    @Override
    public void teardown() {

    }
}
