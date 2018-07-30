package com.example.xu.myglide.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.example.xu.myglide.MyApplication;

/**
 * Created by heiyan on 2017/7/31.
 */

public class PermissionUtils {

    public static boolean hasPermission(String permission){
        return ContextCompat.checkSelfPermission(MyApplication.getContext(), permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public static boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(MyApplication.getContext(), permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    public static boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static boolean hasWritePermission(){
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean hasReadPermission(){
        return hasPermission("android.permission.READ_EXTERNAL_STORAGE");
    }

    public static boolean hasCameraPermission(){
        return hasPermission(Manifest.permission.CAMERA);
    }

    public static boolean isAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllPermissionsGranted(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return false;
            }
        }
        return true;
    }
}
