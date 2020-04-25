package com.sunsty.xmediac;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ali.PictureVideoPlayActivity;
import com.ali.config.PictureConfig;
import com.ali.faster.LaLog;
import com.ali.view.AliActivity;
import com.sunsty.xmediac.util.VideoPlayer;

import java.io.File;
import java.util.Arrays;

/**
 * ffmpeg编码音视频处理
 * author : sunst
 * 说明： 过滤条件：ffmpegandroidplayer   , 解码，  当前帧
 */
public class FFmpegRenderActivity extends AliActivity implements View.OnClickListener {
    private VideoPlayer player;

    private Button btn_decode;//视频解码
    private Button btn_render;//视频播放
    private Button btn_audio_decode;//音频解码
    private Button btn_audio_player;//音频播放
    private Button btn_play;//音视频同步播放用C实现
    private Button btn_transcoding_compress;//转码压缩
    private Button btn_addWatermark;//视频添加水印
    private TextView tvStatus;//视频添加水印

    @Override
    public void initView() {
        setContentView(R.layout.activity_mediac_decode);
        btn_decode = findViewById(R.id.btn_decode);
        btn_render = findViewById(R.id.btn_render);
        btn_audio_decode = findViewById(R.id.btn_audio_decode);
        btn_audio_player = findViewById(R.id.btn_audio_player);
        btn_play = findViewById(R.id.btn_play);
        btn_transcoding_compress = findViewById(R.id.btn_transcoding_compress);
        btn_addWatermark = findViewById(R.id.btn_addWatermark);
        tvStatus = findViewById(R.id.tvStatus);
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
            showLoadding("正在解码视频...");
            tvStatus.setText("正在解码视频...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            doDecode();
                        }
                    }).start();
                }
            }, 5000);
        } else if (id == R.id.btn_render) {
            tvStatus.setText("视频播放...");
            Intent intent = new Intent(this, PictureVideoPlayActivity.class);
            intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, "sdcard/sunseanative.mp4");
            startActivity(intent);
        } else if (id == R.id.btn_audio_decode) {
            showLoadding("正在解码音频...");
            tvStatus.setText("正在解码音频...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            audioDecode();
                        }
                    }).start();
                }
            }, 5000);
        } else if (id == R.id.btn_audio_player) {
            audioPlayer();
        } else if (id == R.id.btn_play) {
            play();
        } else if (id == R.id.btn_transcoding_compress) {
            transcodingCompress();
        } else if (id == R.id.btn_addWatermark) {
            showLoadding("正在添加水印...");
            tvStatus.setText("正在添加水印...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            addWatermark();
                        }
                    }).start();
                }
            }, 5000);
        }
    }

    /**
     * 视频解码
     */
    public void doDecode() {
        //打印信息fmpegandroidplayer: 解码5200帧
        /*
        2020-01-02 11:48:28.397 824-3342/? E/LOGSERVER_UTILS: [Erecovery]readEvent: eRecEventManager readEvent 0
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 视频的文件格式：mov,mp4,m4a,3gp,3g2,mj2
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 视频时长：150
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 视频的宽高：1080,1920
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 解码器的名称：h264
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 视频的文件格式：mov,mp4,m4a,3gp,3g2,mj2
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 视频时长：150
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 视频的宽高：1080,1920
2020-01-02 11:48:28.916 13144-13144/com.sunsty.alidd E/ffmpegandroidplayer: 解码器的名称：h264

        */
        String input = new File(Environment.getExternalStorageDirectory(), "sunst.mp4").getAbsolutePath();
        String output = new File(Environment.getExternalStorageDirectory(), "2020sun_dodecode_video_out.yuv").getAbsolutePath();
        VideoPlayer.decode(input, output);
        dismissLoadding();
        tvStatus.setText("解码视频成功");
    }

    /**
     * 音频解码
     */
    public void audioDecode() {
        String input = new File(Environment.getExternalStorageDirectory(), "追光者.mp3").getAbsolutePath();
        String output = new File(Environment.getExternalStorageDirectory(), "2020sun_dodecode_audio_out.pcm").getAbsolutePath();
        player.audioDecode(input, output);
        dismissLoadding();
        tvStatus.setText("解码音频成功");
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
        String input = new File(Environment.getExternalStorageDirectory(), "追光者.mp3").getAbsolutePath();
        player.audioPlayer(input);
    }

    /**
     * 播放视频==音视频同步播放用C实现
     */
    public void play() {
        Intent intent = new Intent(this, PictureVideoPlayActivity.class);
        intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, "sdcard/2020sunwatermark.mp4");
        startActivity(intent);
    }

    /**
     * 转码压缩
     */
    public void transcodingCompress() {
        final File inputFile;
        final File outputFile;
        final File dic = Environment.getExternalStorageDirectory();
        inputFile = new File(dic, "sun_sea_native.mp4");
//        inputFile = new File(dic, "sunst.avi");//将avi视频格式转成mp4，并且压缩
        outputFile = new File(dic, "2020sun_ranscodingCompress.avi");

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

    /**
     * 给视频加水印，加一个动态水印会踩很多坑 : 这里本神对参数进行一个小节： -y ，直接覆盖输入 ，
     * -i后面接图片地址，非常温馨的技巧（此处的图片地址换成带透明通道的视频就可以合成动态视频遮罩），所以这个就能满足很多人的加合成双视频的需求了
     * -filter_complex 参数是表示使用混合滤镜把图片叠加到视频上。
     * -ignore_loop 这个参数的值为1则忽略gif文件本身的循环设置，为0的话则使用文件本身的设置，一般设置为1
     * overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2 是将图片居中，当然这里的具体位置可以自己调，一般来说，加个水印简单就这样就可以了。
     * 其他的，使用scale参数可以调整水印大小。
     * <p>
     * 现在你可以随意添加任意大小位置的水印了。
     * 那么，能不能再给力一点？我们使用两张图片叠加双重水印？ 这里你就关注本神知乎以后会找到相关文章的介绍。
     * author ： sunst
     * https://zhihu.com/people/qydq
     * 请注意文中版权声明，欢迎关注和交流
     */
    public void addWatermark() {
        //当前帧
        File ipFile = new File(Environment.getExternalStorageDirectory(), "sunseanative.mp4");
        File opFile = new File(Environment.getExternalStorageDirectory(), "2020sunwatermark.mp4");
        File wmFile = new File(Environment.getExternalStorageDirectory(), "sunst.gif");
//        File wmFile = new File(Environment.getExternalStorageDirectory(), "sunst.png");//加水印静态图片
//        String str = "ffmpeg -i " + ipFile.getAbsolutePath() + " -i " + wmFile.getAbsolutePath() + " -filter_complex overlay=480:10 " + opFile.getAbsolutePath();

        String str = "ffmpeg -i " + ipFile.getAbsolutePath() + " -ignore_loop " + "1" + " -i " + wmFile.getAbsolutePath() + " -filter_complex overlay=480:10 " + opFile.getAbsolutePath();
        LaLog.d(TAG + "sunst----命令行1=" + str);

//        final String[] myGifWaterCommand = getWatermarkCommand(ipFile.getAbsolutePath(), wmFile.getAbsolutePath(), opFile.getAbsolutePath());
//        LaLog.d(TAG + "sunst----命令行2=" + Arrays.toString(myGifWaterCommand));
//        final int gifArgc = myGifWaterCommand.length;

        /*
         * 网上搜索的加载视频的命令：（可参考，不一定正确）
         * ffmpeg -y -i video.mp4 -vf \"movie=logo.png [logo]; [in][logo] overlay=5:5 [out]\" -preset ultrafast out.mp4
         * */
        final String[] argv = str.split(" ");
        LaLog.d(TAG + "sunst----命令行3=" + Arrays.toString(argv));
//        [ffmpeg, -i, /storage/emulated/0/sunseanative.mp4, -ignore_loop, 0, -i, /storage/emulated/0/sunst.gif, -filter_complex, overlay=480:10, -y, /storage/emulated/0/2020sunwatermark.mp4]
//        [ffmpeg, -i, /storage/emulated/0/sunseanative.mp4, -ignore_loop, 1, -i, /storage/emulated/0/sunst.gif, -filter_complex, overlay=480:10, /storage/emulated/0/2020sunwatermark.mp4]
        final int argc = argv.length;
        player.ffmpegCmdUtil(argc, argv);

        dismissLoadding();
        tvStatus.setText("水印已成功加载完成");
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
