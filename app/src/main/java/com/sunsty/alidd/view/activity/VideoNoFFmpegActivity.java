package com.sunsty.alidd.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ali.module.lib.config.PictureConfig;
import com.ali.take.LADialog;
import com.ali.take.LAStorageFile;
import com.ali.take.LaLog;
import com.ali.take.LaPermissions;
import com.ali.take.MediaHelper;
import com.ali.take.callback.OnLoadVideoImageListener;
import com.ali.take.webview.NestProgressBar;
import com.ali.view.AliActivity;
import com.ali.view.activity.PictureVideoPlayActivity;
import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;
import com.sunst.alidd.RecordFileUtil;
import com.sunst.alidd.RecordUtil;
import com.sunst.alidd.VideoCompressUtil;
import com.sunsty.alidd.R;

import java.io.File;

public class VideoNoFFmpegActivity extends AliActivity implements View.OnClickListener {
    private static final String TAG = "VideoNoFFmpegActivity:";
    private ImageView ivThumbnailVideo;
    private ImageView ivVideoPlay;
    private LinearLayout llVideoControl;
    private TextView tvVideoStatus;
    private TextView tvProgress;
    private NestProgressBar nestProgressBar;
    private Button btnThumbnail1, btnThumbnail2, btnPhoneScreen, btnPhoneCamera;
    private Button btnWater, btnCompressNormal;

    private LaPermissions laPermissions;
    private MediaHelper mediaHelper;
    private RecordUtil recordUtil;
    private VideoCompressUtil compressUtil;

    private static final long TOTAL_TIME = 5000;
    private static final long ONECE_TIME = 1;
    private boolean recodding = false;
    private String nativeVideoPath = "sdcard/sunst.mp4";


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
        setContentView(R.layout.activity_video);
        laPermissions = new LaPermissions(this);
        mediaHelper = new MediaHelper(VideoNoFFmpegActivity.this);
        recordUtil = new RecordUtil(VideoNoFFmpegActivity.this);
        nestProgressBar = findViewById(R.id.nestProgressBar);

        btnThumbnail1 = findViewById(R.id.btnThumbnail1);
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

        btnThumbnail1.setOnClickListener(this);
        btnThumbnail2.setOnClickListener(this);
        btnPhoneScreen.setOnClickListener(this);
        btnPhoneCamera.setOnClickListener(this);
        btnCompressNormal.setOnClickListener(this);
        btnWater.setOnClickListener(this);

        llVideoControl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnThumbnail1:
                String filePath = "sdcard/sunst.mp4";//ok
                String packagePath = "storage/emulated/0/Android/data/com.zbhd.hgb/files/Screen/1576495565357.mp4";//ok
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
                if (FileUtils.isFileExists(videoPath)) {
                    LaLog.d(TAG, "--onEnd: 准备重命名=");
                    boolean result = FileUtils.rename(videoPath, "alidd_sunst_compress.mp4");
                    if (result) {
                        LaLog.d(TAG, "--onEnd: 压缩结束=重命名成功=" + videoPath);
                        videoPath = RecordFileUtil.createFolder(mContext) + "/alidd_sunst_compress.mp4";
                        LaLog.d(TAG, "--onEnd: 压缩结束=重命名成功=videoPath=" + videoPath);
                        String fileMD5 = FileUtils.getFileMD5ToString(videoPath);
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