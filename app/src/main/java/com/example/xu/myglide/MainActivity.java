package com.example.xu.myglide;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;
import com.example.xu.myglide.glide.GlideApp;
import com.example.xu.myglide.glide.ImageBinderUtils;
import com.example.xu.myglide.glide.interceptor.ProgressInterceptor;
import com.example.xu.myglide.new_glide.GlideImageView;
import com.example.xu.myglide.new_glide.progress.CircleProgressView;
import com.example.xu.myglide.utils.PermissionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private final String permissionWrite = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String permissionRead = "android.permission.READ_EXTERNAL_STORAGE";
    private final String[] permissionsStorage = {permissionWrite, permissionRead};
    private final String[] permissions = {permissionWrite, permissionRead};
    private static final int REQUEST_CODE_PERMISSION = 8;
    private GlideImageView glideImageView;
    private CircleProgressView circleProgressView;
    Target target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        checkPermission();
    }

    private void initView() {
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new MyAdapter());
        glideImageView = findViewById(R.id.glide_image);
        circleProgressView = findViewById(R.id.progressView2);
    }

    private void initListener() {
        /**
         *  测试列表预缓存image集合
         */
        findViewById(R.id.test_preload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreLoadImageActivity.class));
            }
        });
        /**
         *  清理磁盘缓存
         */
        findViewById(R.id.tv_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageBinderUtils.clearDiskCache(MyApplication.getContext());
                target.onStop();
            }
        });

        /**
         *  测试带进度监听
         */
//      String gifdd = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529579089571&di=043a32c1958e15555e41e1578ca4a560&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fbmiddle%2F4b32265d4447ba9df7089";
//      String  gifdd = "http://imgs0.soufunimg.com/news/2018_01/15/1515987658049.gif";
        String gifdd = "https://b.heiyanimg.com/book/45951.jpg@!bm?1";
        glideImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 监听下载进度
                ImageBinderUtils.progressLoadImage(glideImageView, gifdd, new ProgressInterceptor.DownloadProgressListener() {
                    @Override
                    public void update(int percentage, boolean done) {
                        Log.e("flag", "-----------------数据返回了" + percentage);
                        if (done) {
                            circleProgressView.setVisibility(View.GONE);
                        } else {
                            circleProgressView.setVisibility(View.VISIBLE);
                            circleProgressView.setProgress(percentage);
                        }
                    }
                });
            }
        });

    }

    private void checkPermission() {
        if (PermissionUtils.lacksPermissions(permissions)) {
            if (PermissionUtils.lacksPermissions(permissionsStorage)) {
                PermissionUtils.requestPermissions(this, REQUEST_CODE_PERMISSION, permissions);
            } else {
                if (PermissionUtils.lacksPermissions(permissionsStorage))
                    PermissionUtils.requestPermissions(this, REQUEST_CODE_PERMISSION, permissionsStorage);
            }
        } else {
            initImage();
        }
    }

    private void initImage() {
        if (!new File(Environment.getExternalStorageDirectory() + "/MyGlide").exists()) {
            new File(Environment.getExternalStorageDirectory() + "/MyGlide").mkdir();
        }
        if (!new File(Environment.getExternalStorageDirectory() + "/MyGlide/myCache").exists()) {
            new File(Environment.getExternalStorageDirectory() + "/MyGlide/myCache").mkdir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (PermissionUtils.lacksPermissions(permissions)) {

                } else {
                    initImage();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<String> ImageUrls = new ArrayList<>();

        public MyAdapter() {
            addImage();
        }

        private void addImage() {
            for (int i = 0; i < 1; i++) {
                ImageUrls.add("https://b.heiyanimg.com/book/45951.jpg@!bm?1");
                ImageUrls.add("http://imgs0.soufunimg.com/news/2018_01/15/1515987658049.gif");
                ImageUrls.add("http://ww4.sinaimg.cn/large/610dc034gw1f96kp6faayj20u00jywg9.jpg");
                ImageUrls.add("http://ww4.sinaimg.cn/large/610dc034gw1f96kp6faayj20u00jywg9.jpg");
                ImageUrls.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=27b13845316d55fbdac670265d234f40/96dda144ad345982b391b10900f431adcbef8415.jpg");
                ImageUrls.add("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=341229fdfe1fbe09035ec5145b620c30/00e93901213fb80e3435775c3ad12f2eb8389450.jpg");
                ImageUrls.add("https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=8918386cbafd5266b82b3a149b199799/b21c8701a18b87d62fac9f980b0828381e30fd4e.jpg");
                ImageUrls.add("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=a9f37a64f11f4134ff37037e151d95c1/c995d143ad4bd1137c1d50b556afa40f4afb0560.jpg");
                ImageUrls.add("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=8dca0d22dd43ad4bb92e40c0b2005a89/03087bf40ad162d9a62a929b1ddfa9ec8b13cd75.jpg");
                ImageUrls.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b3d532e743c2d562ed08d6edd71390f3/7e3e6709c93d70cf312a368af4dcd100bba12b60.jpg");
                ImageUrls.add("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=f85d0a4f4f166d222777139476220945/5882b2b7d0a20cf49c0b735b7a094b36adaf999f.jpg");
                ImageUrls.add("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=636246318c0a19d8d403820503fb82c9/34fae6cd7b899e51a06e25944ea7d933c9950d49.jpg");
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(View.inflate(MainActivity.this, R.layout.image_layout, null));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bindData(ImageUrls.get(position), position);
        }

        @Override
        public int getItemCount() {
            return ImageUrls.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            GlideImageView iv_img;
            CircleProgressView circleProgressView;

            public MyViewHolder(View itemView) {
                super(itemView);
                iv_img = itemView.findViewById(R.id.iv_img);
                circleProgressView = itemView.findViewById(R.id.progressView2);
            }

            public void bindData(String url, int position) {
                switch (position) {
                    case 1:
                        target = ImageBinderUtils.loadGif(iv_img, url);
                        ImageBinderUtils.bindNetImage(iv_img, url);
                        break;
                    case 2:
                        ImageBinderUtils.bindNetImage(iv_img, url);
                        break;
                    case 3:
                        ImageBinderUtils.getBitmapFromUrl(iv_img, url, 20, 20, new ImageBinderUtils.BitmapLoadListener() {
                            @Override
                            public void onBitmapLoadComplete(Bitmap bitmap) {
                                iv_img.setImageBitmap(bitmap);
                            }
                        });
                        break;
                    default:
                        ImageBinderUtils.bindNetImage(iv_img, url);
                        break;
                }
            }
        }
    }
}
