package com.example.cj.videoeditor.utils;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 */
public class TimeFormatUtils {
    /**
     * 00:00:00 时分秒
     *
     * @param millisec
     * @return
     */
    public static String formatMillisec(int millisec) {
        int sec = millisec / 1000;
        int min = sec / 60;
        int hour = min / 60;
        min = min % 60;
        sec = sec % 60;
        String t = "";
        t = hour >= 10 ? t + hour : t + "0" + hour + ":";
        t = min >= 10 ? t + min : t + "0" + min + ":";
        t = sec >= 10 ? t + sec : t + "0" + sec;
        return t;
    }

    /**
     * 00:00 分秒
     *
     * @param millisec
     * @return
     */
    public static String formatMillisecWithoutHours(int millisec) {
        int sec = millisec / 1000;
        int min = sec / 60;
        sec = sec % 60;
        String t = "";
        t = min >= 10 ? t + min : t + "0" + min + ":";
        t = sec >= 10 ? t + sec : t + "0" + sec;
        return t;
    }
}
