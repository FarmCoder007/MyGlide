package com.example.xu.myglide.utils;

import android.os.Environment;

import java.io.File;

public class FileUtils {
    public static final String BASE_LOCAL = Environment.getExternalStorageDirectory().getPath() + "/" + "MyGlide/myCache/";
//    public static final String BASE_LOCAL = Environment.getExternalStorageDirectory().getPath() + File.separator + "DouTuiComic/glide_cache";

    /**
     * 文件重命名
     *
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public static void renameFile(String oldname, String newname) {
        if (!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(oldname);
            File newfile = new File(newname);
            if (!oldfile.exists()) {
                return;//重命名文件不存在
            }
            if (newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname + "已经存在！");
            else {
                oldfile.renameTo(newfile);
            }
        } else {
            System.out.println("新文件名和旧文件名相同...");
        }
    }
}
