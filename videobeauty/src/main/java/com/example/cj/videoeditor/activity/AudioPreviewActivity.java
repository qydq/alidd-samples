package com.example.cj.videoeditor.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.gpufilter.SlideGpuFilterGroup;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.media.MediaPlayerWrapper;
import com.example.cj.videoeditor.media.VideoInfo;
import com.example.cj.videoeditor.mediacodec.AudioCodec;
import com.example.cj.videoeditor.widget.VideoPreviewView;

import java.util.ArrayList;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 * 视频预览界面
 * 点击确定开始分离音频
 */
public class AudioPreviewActivity extends BaseActivity implements View.OnClickListener, SlideGpuFilterGroup.OnFilterChangeListener, View.OnTouchListener, MediaPlayerWrapper.IMediaCallback {

    private VideoPreviewView previewView;
    private String mVideoPath;
    private boolean resumed;
    int startPoint;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_preview);
        previewView = (VideoPreviewView) findViewById(R.id.videoView);
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.iv_confirm).setOnClickListener(this);

        previewView.setOnFilterChangeListener(this);
        previewView.setOnTouchListener(this);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        //选择的视频的本地播放地址
        mVideoPath = intent.getStringExtra("path");
        ArrayList<String> srcList = new ArrayList<>();
        srcList.add(mVideoPath);
        previewView.setVideoPath(srcList);
        previewView.setIMediaCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, R.string.confirm_to_editor_video, Toast.LENGTH_SHORT).show();
        if (resumed) {
            previewView.start();
        }
        resumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        previewView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isDestroy = true;
        previewView.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!isLoading()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close) {
            if (isLoading()) {
                endLoading();
            }
            finish();
        } else if (id == R.id.iv_confirm) {
            if (isLoading()) {
                return;
            }
            previewView.pause();
            showLoading("视频处理中", false);

            final String path = Constants.getPath("video/outputAudio/", "audio_" + System.currentTimeMillis() + ".aac");//视频音乐
            AudioCodec.getAudioFromVideo(mVideoPath, path, new AudioCodec.AudioDecodeListener() {
                @Override
                public void decodeOver() {
                    Toast.makeText(AudioPreviewActivity.this, "分离完毕 音频保存路径为----  " + path, Toast.LENGTH_SHORT).show();
                    endLoading();
                }

                @Override
                public void decodeFail() {
                    Toast.makeText(AudioPreviewActivity.this, "分离失败 maybe same Exception ，please look at logcat ", Toast.LENGTH_SHORT).show();
                    endLoading();
                }
            });
              /*  AudioCodec.getPCMFromAudio(mVideoPath, path, new AudioCodec.AudioDecodeListener() {
                    @Override
                    public void decodeOver() {
                        Toast.makeText(AudioPreviewActivity.this,"分离完毕 PCM保存路径为----  "+path,Toast.LENGTH_SHORT).show();
                        endLoading();
                    }

                    @Override
                    public void decodeFail() {
                        Toast.makeText(AudioPreviewActivity.this,"分离失败 maybe same Exception ，please look at logcat ",Toast.LENGTH_SHORT).show();
                        endLoading();
                    }
                });*/
        }
    }

    @Override
    public void onFilterChange(MagicFilterType type) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onVideoPrepare() {
    }

    @Override
    public void onVideoStart() {
    }

    @Override
    public void onVideoPause() {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        previewView.seekTo(startPoint);
        previewView.start();
    }

    @Override
    public void onVideoChanged(VideoInfo info) {

    }

}
