package com.example.cj.videoeditor.utils;

import android.content.res.Resources;

import com.ali.AnApplication;

import java.io.InputStream;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 * 用于OpenGl的工具类
 */

public class OpenGlUtils {
    //通过资源路径加载shader脚本文件
    public static String uRes(String path) {
        Resources resources = AnApplication.getInstance().getResources();
        StringBuilder result = new StringBuilder();
        try {
            InputStream is = resources.getAssets().open(path);
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (Exception e) {
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }
}
