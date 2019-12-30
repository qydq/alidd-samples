package com.sunsty.xmediac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ali.module.lib.config.PictureConfig;
import com.ali.view.activity.PictureVideoPlayActivity;
import com.sunsty.xmediac.util.VideoPlayer;

import java.io.File;
import java.util.Arrays;

public class MediacDecodeActivity extends Activity implements View.OnClickListener {
    private VideoPlayer player;

    private Button btn_decode;//视频解码
    private Button btn_render;//视频播放
    private Button btn_audio_decode;//音频解码
    private Button btn_audio_player;//音频播放
    private Button btn_play;//音视频同步播放用C实现
    private Button btn_transcoding_compress;//转码压缩
    private Button btn_addWatermark;//视频添加水印

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediac_decode);
        btn_decode = findViewById(R.id.btn_decode);
        btn_render = findViewById(R.id.btn_render);
        btn_audio_decode = findViewById(R.id.btn_audio_decode);
        btn_audio_player = findViewById(R.id.btn_audio_player);
        btn_play = findViewById(R.id.btn_play);
        btn_transcoding_compress = findViewById(R.id.btn_transcoding_compress);
        btn_addWatermark = findViewById(R.id.btn_addWatermark);
        player = new VideoPlayer();

        btn_decode.setOnClickListener(this);
        btn_render.setOnClickListener(this);
        btn_audio_decode.setOnClickListener(this);
        btn_audio_player.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_addWatermark.setOnClickListener(this);
        btn_transcoding_compress.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_decode) {
            doDecode();
        } else if (id == R.id.btn_render) {
            Intent intent = new Intent(this, PictureVideoPlayActivity.class);
            intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, "sdcard/sunst_native.mp4");
            startActivity(intent);
        } else if (id == R.id.btn_audio_decode) {
            audioDecode();
        } else if (id == R.id.btn_audio_player) {
            audioPlayer();
        } else if (id == R.id.btn_play) {
            play();
        } else if (id == R.id.btn_transcoding_compress) {
            transcodingCompress();
        } else if (id == R.id.btn_addWatermark) {
            addWatermark();
        }
    }

    /**
     * 视频解码
     */
    public void doDecode() {
        String input = new File(Environment.getExternalStorageDirectory(), "sunst.mp4").getAbsolutePath();
        String output = new File(Environment.getExternalStorageDirectory(), "小苹果_out.yuv").getAbsolutePath();
        VideoPlayer.decode(input, output);
        Toast.makeText(this, "正在解码...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 音频解码
     */
    public void audioDecode() {
        String input = new File(Environment.getExternalStorageDirectory(), "说散就散.mp3").getAbsolutePath();
        String output = new File(Environment.getExternalStorageDirectory(), "说散就散.pcm").getAbsolutePath();
        player.audioDecode(input, output);
        Toast.makeText(this, "正在解码...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 音频播放
     */
    public void audioPlayer() {
        /**
         * 1.播放视频文件中的音频
         */
        //   String input = new File(Environment.getExternalStorageDirectory(),"告白气球.avi").getAbsolutePath();

        /**
         * 2.播放音频文件中的音频
         */
        String input = new File(Environment.getExternalStorageDirectory(), "说散就散.mp3").getAbsolutePath();
        player.audioPlayer(input);
        Log.d("Main", "正在播放");
    }

    /**
     * 播放视频==音视频同步播放用C实现
     */
    public void play() {
        Intent intent = new Intent(this, PictureVideoPlayActivity.class);
        intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, "sdcard/sunst_auto_lifangfang.mp4");
        startActivity(intent);
    }


    /**
     * 转码压缩
     */
    public void transcodingCompress() {

        final File inputFile;
        final File outputFile;
        final File dic = Environment.getExternalStorageDirectory();
        inputFile = new File(dic, "sunst.mp4");
        outputFile = new File(dic, "告白气球.mp4");

        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取视频文件的路径(sdcard路径)
                // 拼接cmd 指令 ffmpeg -i 告白气球.avi -b:v 640k 告白气球.mp4 (与windows 命令行相同)
                StringBuilder builder = new StringBuilder();
                builder.append("ffmpeg ");
                builder.append("-i ");
                builder.append(inputFile.getAbsolutePath() + " ");
                builder.append("-b:v 640k ");  //码率越大 清晰度越高 码率越小 清晰度越低
                builder.append(outputFile.getAbsolutePath());
                String[] argv = builder.toString().split(" ");

                int argc = argv.length;

                player.ffmpegCmdUtil(argc, argv);

            }
        }).start();

    }

    public void addWatermark() {

        //当前帧
        File ipFile = new File(Environment.getExternalStorageDirectory(), "sunst1.mp4");
        File opFile = new File(Environment.getExternalStorageDirectory(), "sunstoutsuper.mp4");
        File wmFile = new File(Environment.getExternalStorageDirectory(), "sunst_super.gif");

//        String str = "ffmpeg -i " + ipFile.getAbsolutePath() + " -i " + wmFile.getAbsolutePath() + " -filter_complex overlay=480:10 " + opFile.getAbsolutePath();
        String str = "ffmpeg -i " + ipFile.getAbsolutePath() + " -ignore_loop " + "0" + " -i " + wmFile.getAbsolutePath() + " -filter_complex overlay=480:10 " + opFile.getAbsolutePath();
        Log.d("命令行1:", "TAG----str" + str);

        final String[] myGifWaterCommand = getWatermarkCommand(ipFile.getAbsolutePath(), wmFile.getAbsolutePath(), opFile.getAbsolutePath());

        Log.d("命令行2:", Arrays.toString(myGifWaterCommand));

        /**
         * 网上搜索的加载视频的命令：
         * ffmpeg -y -i video.mp4 -vf \"movie=logo.png [logo]; [in][logo] overlay=5:5 [out]\" -preset ultrafast out.mp4
         * */
        final String[] argv = str.split(" ");

        Log.d("命令行3:", Arrays.toString(argv));

        final int argc = argv.length;

        final int gifArgc = myGifWaterCommand.length;
        new Thread() {
            public void run() {
                player.ffmpegCmdUtil(argc, argv);
                Log.i("MediacDecodeActivity", "------加水印完成-------");
            }
        }.start();
    }

    private String[] getWatermarkCommand(String videoUrl, String gifwaterpath, String outputUrl) {
        String[] commands = new String[11];
        commands[0] = "ffmpeg";
//输入
        commands[1] = "-i";
        commands[2] = videoUrl;
//水印
        commands[3] = "-ignore_loop";
        commands[4] = "0";
        commands[5] = "-i";
        commands[6] = gifwaterpath;
//        commands[5] = "-i";
//        commands[6] = gifwaterpath;
        commands[7] = "-filter_complex";
//        commands[8] = "[1:v]scale=100:100[img1];[2:v]scale=1280:720[img2];[0:v][img1]overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2[bkg];[bkg][img2]overlay=0:0";
        commands[8] = "overlay=480:10";
//覆盖输出
        commands[9] = "-y";//直接覆盖输出文件
//输出文件
        commands[10] = outputUrl;
        return commands;
    }
}
