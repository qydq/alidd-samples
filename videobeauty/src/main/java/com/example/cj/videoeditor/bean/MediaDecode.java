package com.example.cj.videoeditor.bean;

import android.media.MediaExtractor;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 * desc 音频解码的info类 包含了音频path 音频的MediaExtractor
 * 和本段音频的截取点cutPoint
 * 以及剪切时长 cutDuration
 */

public class MediaDecode {
    public String path;
    public MediaExtractor extractor;
    public int cutPoint;
    public int cutDuration;
    public int duration;
}
