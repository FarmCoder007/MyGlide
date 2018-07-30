package com.example.xu.myglide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.xu.myglide.glide.ImageBinderUtils;

/**
 * 测试加载大量图片时预缓存
 */
public class PreLoadImageActivity extends AppCompatActivity {
    ImageView imageView;
    public static final String Url = "http://ww4.sinaimg.cn/large/610dc034gw1f96kp6faayj20u00jywg9.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = findViewById(R.id.iv_image);
        findViewById(R.id.tv_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 *  方法一
                 */
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ImageBinderUtils.downloadImageToLocal(PreLoadImageActivity.this, Url);
//                    }
//                }).start();

                /**
                 *  方法二
                 */
                ImageBinderUtils.preloadImageToLocal(PreLoadImageActivity.this,Url);
            }
        });

        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageBinderUtils.bindNetImage(imageView, Url);
            }
        });
    }
}
