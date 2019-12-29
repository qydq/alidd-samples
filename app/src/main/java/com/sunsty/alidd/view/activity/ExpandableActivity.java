package com.sunsty.alidd.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ali.take.MediaHelper;
import com.ali.take.callback.OnLoadVideoImageListener;
import com.bumptech.glide.Glide;
import com.sunsty.alidd.R;

import java.io.File;

import static android.provider.MediaStore.Video.Thumbnails.MICRO_KIND;

public class ExpandableActivity extends AppCompatActivity {
    private static final String TAG = "JustHttpsActivity";
    private TextView tvHttps;
    private ImageView natureIv;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Glide.with(ExpandableActivity.this)
                        .load((Bitmap) msg.obj)
                        .into(natureIv);
            } else {
                String imagePath = (String) msg.obj;
                if (!TextUtils.isEmpty(imagePath)) {
                    Glide.with(ExpandableActivity.this)
                            .load(imagePath)
                            .into(natureIv);
                } else {
                    Glide.with(ExpandableActivity.this)
                            .load(msg.obj)
                            .into(natureIv);
                }
            }
            return false;
        }
    });


    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    private void loadNativeVideo() {
//        String filePath = "sdcard/sunst.mp4";//ok
        MediaHelper mediaHelper = new MediaHelper(ExpandableActivity.this);
        String xFilePath = "storage/emulated/0/Android/data/com.zbhd.hgb/files/Screen/1576495565357.mp4";
        File ipFile = new File(Environment.getExternalStorageDirectory(), "sunst.mp4");//ok
        String filePathGetPath = ipFile.getPath();
        String aPath = ipFile.getAbsolutePath();
        Bitmap bitmap = mediaHelper.getVideoThumbnail(xFilePath, MICRO_KIND, 80, 80);
//        File filepath = mediaHelper.bitmap2File(bitmap, "中国特种兵");//这里是将bitmap转为File，很多地方代码都能拷贝这一块
//        String absolutePaht = filepath.getAbsolutePath();
        Message message = mHandler.obtainMessage();
        message.obj = bitmap;
        message.what = 1;
        mHandler.sendMessage(message);
    }

    private void loadRemote() {
        //                                String videoUrl = "http://hgb-oss-001.oss-cn-shenzhen.aliyuncs.com//db9f9558e896420d9441dd6b55bd3c8f.mp4";
        String videoUrl = "sdcard/sunst.mp4";
        MediaHelper mediaHelper = new MediaHelper(ExpandableActivity.this);
        mediaHelper.getVideoThumbnail(videoUrl, new OnLoadVideoImageListener() {
            @Override
            public void onLoadImage(File file) {
                File typeResultFile = MediaHelper.getOutputMediaFile(MediaHelper.MEDIA_TYPE_IMAGE);
                String opPath = typeResultFile.getAbsolutePath();

                String resultPath = file.getAbsolutePath();
                Message message = mHandler.obtainMessage();
                message.obj = resultPath;
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        tvHttps = findViewById(R.id.tvHttps);
        natureIv = findViewById(R.id.natureIv);

        tvHttps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                loadNativeVideo();
//                                loadRemote();
                            }
                        }).start();
                    }
                });


            }
        });
    }
}