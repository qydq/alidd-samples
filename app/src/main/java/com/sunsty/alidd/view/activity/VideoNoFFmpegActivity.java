package com.sunsty.alidd.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ali.module.lib.config.PictureConfig;
import com.ali.take.DataService;
import com.ali.take.FileUtils;
import com.ali.take.LADialog;
import com.ali.take.LAStorageFile;
import com.ali.take.LaLog;
import com.ali.take.LaPermissions;
import com.ali.take.MediaHelper;
import com.ali.take.callback.OnLoadVideoImageListener;
import com.ali.take.webview.NestProgressBar;
import com.ali.view.AliActivity;
import com.ali.view.activity.PictureVideoPlayActivity;
import com.bumptech.glide.Glide;
import com.sunst.alidd.OnScreenShotListener;
import com.sunst.alidd.RecordFileUtil;
import com.sunst.alidd.RecordUtil;
import com.sunst.alidd.ScreenShotUtil;
import com.sunst.alidd.VideoCompressUtil;
import com.sunsty.alidd.R;
import com.sunsty.xmediac.util.VideoPlayer;

import java.io.File;
import java.util.Arrays;

public class VideoNoFFmpegActivity extends AliActivity implements View.OnClickListener {
    private static final String TAG = "VideoNoFFmpegActivity:";
    private ImageView ivThumbnailVideo;
    private ImageView ivVideoPlay;
    private LinearLayout llVideoControl;
    private TextView tvVideoStatus;
    private TextView tvProgress;
    private NestProgressBar nestProgressBar;
    private Button btnThumbnail1, btnThumbnail2, btnPhoneScreen, btnPhoneCamera;
    private Button btnWater, btnCompressNormal, btnScreenShot;

    private LaPermissions laPermissions;
    private MediaHelper mediaHelper;
    private RecordUtil recordUtil;
    private VideoCompressUtil compressUtil;

    private static final long TOTAL_TIME = 5000;
    private static final long ONECE_TIME = 1;
    private boolean recodding = false;
    private String nativeVideoPath = "sdcard/sunst.mp4";
    private VideoPlayer player;

    private TextView tvStatus;//视频添加水印


    private void handThumbnailVideoImage(Bitmap bitmap) {
        Glide.with(VideoNoFFmpegActivity.this).load(bitmap).into(ivThumbnailVideo);
    }

    private void handThumbnailVideoImage(File bitmapFileDir) {
        handThumbnailVideoImage(bitmapFileDir.getAbsolutePath());
    }

    private void handThumbnailVideoImage(String bitmapFilePath) {
        Glide.with(VideoNoFFmpegActivity.this).load(bitmapFilePath).into(ivThumbnailVideo);
    }

    private void setVideoPreview() {
        llVideoControl.setVisibility(View.VISIBLE);
        tvVideoStatus.setText("预览视频");
        ivVideoPlay.setImageResource(R.drawable.base_drawable_music_play);
        recodding = false;
    }

    private void videoPreview() {
        Intent intent = new Intent(this, PictureVideoPlayActivity.class);
        intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, nativeVideoPath);
        startActivity(intent);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_video_noffmpeg);
        laPermissions = new LaPermissions(this);
        mediaHelper = new MediaHelper(VideoNoFFmpegActivity.this);
        recordUtil = new RecordUtil(VideoNoFFmpegActivity.this);
        nestProgressBar = findViewById(R.id.nestProgressBar);

        player = new VideoPlayer();

        btnThumbnail1 = findViewById(R.id.btnThumbnail1);
        tvStatus = findViewById(R.id.tvStatus);
        btnThumbnail2 = findViewById(R.id.btnThumbnail2);
        btnPhoneScreen = findViewById(R.id.btnPhoneScreen);
        btnPhoneCamera = findViewById(R.id.btnPhoneCamera);
        btnCompressNormal = findViewById(R.id.btnCompressNormal);
        btnWater = findViewById(R.id.btnWater);

        tvProgress = findViewById(R.id.tvProgress);
        tvVideoStatus = findViewById(R.id.tvVideoStatus);
        llVideoControl = findViewById(R.id.llVideoControl);
        ivVideoPlay = findViewById(R.id.ivVideoPlay);
        ivThumbnailVideo = findViewById(R.id.ivThumbnailVideo);
        btnScreenShot = findViewById(R.id.btnScreenShot);

        btnThumbnail1.setOnClickListener(this);
        btnThumbnail2.setOnClickListener(this);
        btnPhoneScreen.setOnClickListener(this);
        btnPhoneCamera.setOnClickListener(this);
        btnCompressNormal.setOnClickListener(this);
        btnScreenShot.setOnClickListener(this);
        btnWater.setOnClickListener(this);

        llVideoControl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnThumbnail1:
                String filePath = "sdcard/sunst.mp4";//ok
                String packagePath = "storage/emulated/0/Android/data/com.sunsty.alidd/files/Screen/1576495565357.mp4";//ok
                File ipFile = new File(LAStorageFile.INSTANCE.getskStorageDirectoryFile(), "sunst.mp4");//ok
                String filePathGetPath = ipFile.getPath();
                String aPath = ipFile.getAbsolutePath();
                getNativeVideFrameImage(filePath);
                break;
            case R.id.btnThumbnail2:
                getRemoteVideFrameImage();
                break;
            case R.id.btnPhoneScreen:
                if (recodding) {
                    recordUtil.stopRecord();
                } else {
                    requestScreenRecordPermission();
                }
                break;
            case R.id.btnPhoneCamera:
                break;
            case R.id.btnScreenShot:
                ScreenShotUtil instance = ScreenShotUtil.getInstance();
                instance.screenShot(this, new OnScreenShotListener() {
                    @Override
                    public void screenShot() {
                        Bitmap bitmap = instance.getScreenShot();
                        if (bitmap != null) {
                            ivThumbnailVideo.setImageBitmap(bitmap);
                        }
                    }
                });

                break;
            case R.id.llVideoControl:
                if (recodding) {
                    showToast("正在录屏中不能预览视频");
                } else {
                    videoPreview();
                }
                break;
            case R.id.btnCompressNormal:
                LADialog.INSTANCE.createDialog(this, "正在压缩...", false).show();
                compressUtil = new VideoCompressUtil();
                startCompressVideo();
                break;
            case R.id.btnWater:
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
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recordUtil.clear();
    }

    /**
     * 手机屏幕录制视频;
     * 注意:需要添加录屏权限和文件存储权限<uses-permission android:name="android.permission.RECORD_AUDIO" />
     * 在华为或者api q以上还需要添加android.permission.FOREGROUND_SERVICE权限
     * Author ：sunst
     */
    private void requestScreenRecordPermission() {
        int[] requestCodes = new int[]{0, 7};
        laPermissions.requestPermissions(requestCodes, false, permissionGrant);
    }

    private void startPhoneScreenRecord() {
        recordUtil.setRecordListener(new RecordUtil.RecordListener() {
            @Override
            public void onStart() {
                LaLog.d(TAG, "--onStart: 开始录屏=");
                countDownTimer.start();
            }

            @Override
            public void onRefuse() {
                LaLog.d(TAG, "--onRefuse: 您拒绝了录屏的权限=");
                showToast("您拒绝了录屏的权限");
            }

            @Override
            public void onEnd(String videoPath) {
                LaLog.d(TAG, "--onEnd: 录屏结束 = videoPath=" + videoPath);
                recodding = false;
                nativeVideoPath = videoPath;
                getNativeVideFrameImage(nativeVideoPath);
                setVideoPreview();
                btnPhoneScreen.setText("手机屏幕录制视频");
            }
        });
        recordUtil.createRecord();
    }

    /**
     * 压缩视频
     * Author ：sunst
     */
    private void startCompressVideo() {
        compressUtil.compressVideoMedium(VideoNoFFmpegActivity.this, nativeVideoPath, new VideoCompressUtil.OnCompressListener() {
            @Override
            public void onStart() {
                LaLog.d(TAG, "--onStart: 开始压缩=");
            }

            @Override
            public void onEnd(String videoPath) {
                LaLog.d(TAG, "--onEnd: 压缩结束=videoPath=" + videoPath);
                if (LAStorageFile.INSTANCE.isFileExists(videoPath)) {
                    LaLog.d(TAG, "--onEnd: 准备重命名=");
                    boolean result = FileUtils.getInstance().rename(videoPath, "alidd_sunst_compress.mp4");
                    if (result) {
                        LaLog.d(TAG, "--onEnd: 压缩结束=重命名成功=" + videoPath);
                        videoPath = RecordFileUtil.createFolder(mContext) + "/alidd_sunst_compress.mp4";
                        LaLog.d(TAG, "--onEnd: 压缩结束=重命名成功=videoPath=" + videoPath);
                        String fileMD5 = DataService.getInstance().md5service(videoPath);
                        if (!TextUtils.isEmpty(videoPath) && !TextUtils.isEmpty(fileMD5)) {
//                            uploadVideoFile(nativeVideoPath);
                        }
                    }
                }
                LADialog.INSTANCE.cancelDialog();
                showToast("压缩成功，当前视频大小:" + LAStorageFile.INSTANCE.getDirSize(videoPath));
            }

            @Override
            public void onFailed() {
                LaLog.d(TAG, "--onFailed: 压缩失败=");
                LADialog.INSTANCE.cancelDialog();
            }
        });
    }

    /**
     * CountDownTimer 实现最简单的倒计时
     * Author: sunst
     */
    private CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, ONECE_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            int value = (int) (millisUntilFinished / 1000);
            btnPhoneScreen.setText("手机屏幕停止录制(" + value + "s)");
        }

        @Override
        public void onFinish() {
            showToast("开始录屏");
            recodding = true;
            recordUtil.startRecord();
            moveTaskToBack(true);

            llVideoControl.setVisibility(View.VISIBLE);
            tvVideoStatus.setText("正在录制中...");
            ivVideoPlay.setImageResource(R.drawable.base_drawable_music_play);
            btnPhoneScreen.setText("手机屏幕停止录制");
        }
    };

    private void getNativeVideFrameImage(String filePath) {
        LaLog.d(TAG, "--getNativeVideFrameImage: =");
        Bitmap bitmap = mediaHelper.getVideoThumbnail(filePath, 80, 80);

//        File nativeSaveFile = mediaHelper.bitmap2File(bitmap, "中国特种兵");//这里是将bitmap转为File，很多地方代码都能拷贝这一块
//        String nativeSavePath = nativeSaveFile.getAbsolutePath();

        handThumbnailVideoImage(bitmap);
        setVideoPreview();
    }

    private void getRemoteVideFrameImage() {
        LaLog.d(TAG, "--getRemoteVideFrameImage: =");
        nativeVideoPath = "http://pgcvideo.cdn.xiaodutv.com/3412626991_1962328323_20191019192418.mp4?Cache-Control%3Dmax-age%3A8640000%26responseExpires%3DMon%2C_27_Jan_2020_19%3A24%3A31_GMT=&xcode=09afe3e9999ff622eec18483e75242189519057c1d0541f1&time=1577524957&_=1577439288308";
        mediaHelper.getVideoThumbnail(nativeVideoPath, new OnLoadVideoImageListener() {
            @Override
            public void onLoadImage(File file) {
                LaLog.d(TAG, "--getRemoteVideFrameImage: file=" + file);
                File typeResultFile = MediaHelper.getOutputMediaFile(MediaHelper.MEDIA_TYPE_IMAGE);
                String opPath = typeResultFile.getAbsolutePath();
                String resultPath = file.getAbsolutePath();
//                handThumbnailVideoImage(typeResultFile);
//                handThumbnailVideoImage(opPath);
//                handThumbnailVideoImage(resultPath);
                handThumbnailVideoImage(file);
            }
        });
        setVideoPreview();
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
        File wmFile = new File(Environment.getExternalStorageDirectory(), "sunst.png");//加水印静态图片
        String str = "ffmpeg -i " + ipFile.getAbsolutePath() + " -i " + wmFile.getAbsolutePath() + " -filter_complex overlay=480:10 " + opFile.getAbsolutePath();

        final String[] argv = str.split(" ");
        LaLog.d(TAG + "sunst----命令行3=" + Arrays.toString(argv));
//        [ffmpeg, -i, /storage/emulated/0/sunseanative.mp4, -ignore_loop, 0, -i, /storage/emulated/0/sunst.gif, -filter_complex, overlay=480:10, -y, /storage/emulated/0/2020sunwatermark.mp4]
//        [ffmpeg, -i, /storage/emulated/0/sunseanative.mp4, -ignore_loop, 1, -i, /storage/emulated/0/sunst.gif, -filter_complex, overlay=480:10, /storage/emulated/0/2020sunwatermark.mp4]
        final int argc = argv.length;
        player.ffmpegCmdUtil(argc, argv);

        dismissLoadding();
        tvStatus.setText("水印已成功加载完成");
    }

    /**
     * ina情景LaPermission权限申请
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        laPermissions.requestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private LaPermissions.PermissionGrant permissionGrant = new LaPermissions.PermissionGrant() {
        @Override
        public void onPermissionGranted(String... grantedPermissions) {
            Toast.makeText(VideoNoFFmpegActivity.this, " open this onPermissionGranted", Toast.LENGTH_SHORT).show();
            startPhoneScreenRecord();
        }

        @Override
        public void onPermissionDenied(String... deniedPermissions) {
            Toast.makeText(VideoNoFFmpegActivity.this, " open this onPermissionDenied", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionExist() {
            Toast.makeText(VideoNoFFmpegActivity.this, " open this onPermissionExist", Toast.LENGTH_SHORT).show();
            startPhoneScreenRecord();
        }
    };
}