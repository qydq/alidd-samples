package com.example.cj.videoeditor.activity;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.media.VideoInfo;
import com.example.cj.videoeditor.mediacodec.MediaMuxerRunnable;

import java.util.ArrayList;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 * 视频拼接的类
 */

public class VideoConnectActivity extends BaseActivity implements View.OnClickListener {
    private TextView mPathOne;
    private TextView mPathTwo;
    private String path1;
    private String path2;

    private ArrayList<VideoInfo> mInfoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_connect);
        findViewById(R.id.select_one).setOnClickListener(this);
        findViewById(R.id.select_two).setOnClickListener(this);
        findViewById(R.id.video_connect).setOnClickListener(this);

        mPathOne = (TextView) findViewById(R.id.path_one);
        mPathTwo = (TextView) findViewById(R.id.path_two);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.select_one) {
            VideoSelectActivity.openActivityForResult(this, 100);
        } else if (id == R.id.select_two) {
            VideoSelectActivity.openActivityForResult(this, 101);
        } else if (id == R.id.video_connect) {
            if (TextUtils.isEmpty(path1) || TextUtils.isEmpty(path2)) {
                Toast.makeText(this, "请先选择视频", Toast.LENGTH_SHORT).show();
                return;
            }
            String[] data = {path1, path2};
            setDataSource(data);
        }
    }
    private String outputPath;
    public void setDataSource(String[] dataSource) {

        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        mInfoList.clear();
        for (int i = 0; i < dataSource.length; i++) {
            VideoInfo info = new VideoInfo();
            String path = dataSource[i];
            retr.setDataSource(path);
            String rotation = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String duration = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            info.path = path;
            info.rotation = Integer.parseInt(rotation);
            info.width = Integer.parseInt(width);
            info.height = Integer.parseInt(height);
            info.duration = Integer.parseInt(duration);

            mInfoList.add(info);
        }
        outputPath = Constants.getPath("video/output/", System.currentTimeMillis() + "");
        showLoading("视频拼接中");
        MediaMuxerRunnable instance = new MediaMuxerRunnable();
        instance.setVideoInfo(mInfoList, outputPath);
        instance.addMuxerListener(new MediaMuxerRunnable.MuxerListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        endLoading();
                        Toast.makeText(VideoConnectActivity.this," 拼接完成 文件地址 "+outputPath,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        instance.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 100) {
                path1 = data.getStringExtra("path");
                mPathOne.setText(path1);
            } else if (requestCode == 101) {
                path2 = data.getStringExtra("path");
                mPathTwo.setText(path2);
            }
        }
    }
}
