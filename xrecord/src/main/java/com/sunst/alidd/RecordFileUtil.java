package com.sunst.alidd;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Objects;

/**
 * Desc: 文件处理
 */
public class RecordFileUtil {

    private static final String TAG = "RecordFileUtil";

    /**
     * 获取存储路径
     * @param context
     * @return
     */
    public static String getFilesPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            return Objects.requireNonNull(context.getExternalFilesDir(null)).getPath();
        } else {
            //外部存储不可用
            return context.getFilesDir().getPath();
        }
    }

    /**
     * 创建存储录屏文件、压缩后的视频的目录
     * @param context
     * @return
     */
    public static String createFolder(Context context) {
        String path1 = getFilesPath(context) + "/Screen";
        Log.d(TAG, "录屏文件目录 = " + path1);
        File myFile = new File(path1);
        if (!myFile.exists()) {
            boolean result = myFile.mkdir();
            Log.d(TAG, "Screen目录创建" + (result ? "成功" : "失败"));
        }
        return myFile.getPath();
    }

    /**
     * 创建录屏文件完整路径
     * @return
     */
    public static String createFilePath(Context context) {
        File myFile = new File(createFolder(context));
        File file = new File(myFile, System.currentTimeMillis() + ".mp4");
        Log.d(TAG, "录屏文件路径 = " + file.getPath());
        return file.getPath();
    }


    /**
     * 判断文件大小是否超过标准（20M）
     *
     * @param path 文件路径
     * @param size 文件大小（MB）
     * @return
     */
    public static boolean isFileSizeExceed(String path, int size) {
        File file = new File(path);
        float length = file.length();
        String value;
        if (length >= 1024 * 1024f) // Size in KB
            value = length / 1024 / 1024 + " MB";
        else
            value = length / 1024 + " KB";
        Log.e(TAG, "Path: " + "Name: " + file.getName() + "\nSize: " + value);

        return length > size * 1024 * 1024f;
    }
}
