package com.example.cj.videoeditor;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.sunsta.bear.AnApplication;
import com.sunsta.bear.AnConstants;

import java.io.File;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 */
public class Constants {
    /**
     * 屏幕宽高
     */
    public static int screenWidth;
    public static int screenHeight;

    /**
     * 画幅,视频的样式 9:16 1：1 16:9
     */
    public static final int MODE_POR_9_16 = 0;
    public static final int MODE_POR_1_1 = 1;
    public static final int MODE_POR_16_9 = 2;

    /**
     * 三种画幅的具体显示尺寸
     */
    public static int mode_por_width_9_16;
    public static int mode_por_height_9_16;
    public static int mode_por_width_1_1;
    public static int mode_por_height_1_1;
    public static int mode_por_width_16_9;
    public static int mode_por_height_16_9;

    /**
     * 三种画幅的具体编码尺寸(参考VUE)
     */
    public static final int mode_por_encode_width_9_16 = 540;
    public static final int mode_por_encode_height_9_16 = 960;
    public static final int mode_por_encode_width_1_1 = 540;
    public static final int mode_por_encode_height_1_1 = 540;
    public static final int mode_por_encode_width_16_9 = 960;
    public static final int mode_por_encode_height_16_9 = 540;

    public static void init(Context context) {
        DisplayMetrics mDisplayMetrics = context.getResources()
                .getDisplayMetrics();
        screenWidth = mDisplayMetrics.widthPixels;
        screenHeight = mDisplayMetrics.heightPixels;
        mode_por_width_9_16 = screenWidth;
        mode_por_height_9_16 = screenHeight;
        mode_por_width_1_1 = screenWidth;
        mode_por_height_1_1 = screenWidth;
        mode_por_width_16_9 = screenWidth;
        mode_por_height_16_9 = screenWidth / 16 * 9;
    }

    public static String getBaseFolder() {
        String baseFolder = Environment.getExternalStorageDirectory() + File.separator + AnConstants.FOLDER_ROOT + File.separator;
        File f = new File(baseFolder);
        if (!f.exists()) {
            boolean b = f.mkdirs();
            if (!b) {
                baseFolder = AnApplication.getApplication().getExternalFilesDir(null).getAbsolutePath() + File.separator;
            }
        }
        return baseFolder;
    }

    //获取VideoPath
    public static String getPath(String path, String fileName) {
        String p = getBaseFolder() + path;
        File f = new File(p);
        if (!f.exists() && !f.mkdirs()) {
            return getBaseFolder() + fileName;
        }
        return p + fileName;
    }
}
