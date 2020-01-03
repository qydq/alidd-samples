package com.example.cj.videoeditor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.mediacodec.AudioCodec;

import java.io.File;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 */
public class AudioEditorActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        findViewById(R.id.video_select).setOnClickListener(this);
        findViewById(R.id.audio_select).setOnClickListener(this);
        findViewById(R.id.pcm_to_audio).setOnClickListener(this);
        findViewById(R.id.audio_mix).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.video_select) {//去选择视频
            VideoSelectActivity.openActivity(this);
        } else if (id == R.id.audio_select) {
            startActivity(new Intent(AudioEditorActivity.this, AudioSelectActivity.class));
        } else if (id == R.id.pcm_to_audio) {//pcm文件转音频
            String path = Constants.getPath("audio/outputPCM/", "PCM_1511078423497.pcm");
            if (!new File(path).exists()) {
                Toast.makeText(this, "PCM文件不存在，请设置为本地已有PCM文件", Toast.LENGTH_SHORT).show();
                return;
            }
            final String audioPath = Constants.getPath("audio/outputAudio/", "audio_" + System.currentTimeMillis() + ".aac");
            showLoading("音频编码中...");
            AudioCodec.PCM2Audio(path, audioPath, new AudioCodec.AudioDecodeListener() {
                @Override
                public void decodeOver() {
                    Toast.makeText(AudioEditorActivity.this, "数据编码成功 文件保存位置为—>>" + audioPath, Toast.LENGTH_SHORT).show();
                    endLoading();
                }

                @Override
                public void decodeFail() {
                    Toast.makeText(AudioEditorActivity.this, "数据编码失败 maybe same Exception ，please look at logcat  " + audioPath, Toast.LENGTH_SHORT).show();
                    endLoading();
                }
            });
        } else if (id == R.id.audio_mix) {
            startActivity(new Intent(this, AudioMixActivity.class));
        }
    }
}
