package com.sunst.alidd;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;



/**
 * Desc: 适配android10
 * 录屏工具类MediaProjection只能在服务中创建
 */
public class ScreenRecordService extends Service {

    private int resultCode;
    private Intent resultData = null;

    private MediaProjectionManager mMediaProjectionManager;
    private MediaProjection mediaProjection = null;
    private ScreenRecorder mRecorder;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mScreenDensity;
    private int mBitrate;
    private String mPath;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ScreenRecordService", "onCreate: ");
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling startService(Intent),
     * providing the arguments it supplied and a unique integer token representing the start request.
     * Do not call this method directly.
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //todo theres is icon
        Log.e("ScreenRecordService", "onStartCommand: ");
        createNotificationChannel(R.drawable.base_image_arrowback);

        try {
            resultCode = intent.getIntExtra("resultCode", -1);
            resultData = intent.getParcelableExtra("resultData");
            mScreenWidth = intent.getIntExtra("width", 0);
            mScreenHeight = intent.getIntExtra("height", 0);
            mScreenDensity = intent.getIntExtra("density", 0);
            mBitrate = intent.getIntExtra("bitrate", 0);
            mPath = intent.getStringExtra("path");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                if (null != mMediaProjectionManager) {
                    mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, resultData);
                }
                mRecorder = new ScreenRecorder(mScreenWidth, mScreenHeight, mBitrate, mScreenDensity, mediaProjection, mPath);
                mRecorder.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * START_NOT_STICKY:
         * Constant to return from onStartCommand(Intent, int, int): if this service's process is
         * killed while it is started (after returning from onStartCommand(Intent, int, int)),
         * and there are no new start intents to deliver to it, then take the service out of the
         * started state and don't recreate until a future explicit call to Context.startService(Intent).
         * The service will not receive a onStartCommand(Intent, int, int) call with a null Intent
         * because it will not be re-started if there are no pending FasterIntents to deliver.
         */
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ScreenRecordService", "onDestroy: ");

        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
            mMediaProjectionManager=null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel(@DrawableRes int icon) {
        String channelId = "record01"; // 通知渠道
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器

        builder.setSmallIcon(icon) // 设置状态栏内的小图标
                .setContentText("录屏开始") // 设置上下文内容
                .setAutoCancel(true); // 点击清除

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // 兼容  API 26，Android 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "录屏通知",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            builder.setChannelId(channelId);
        }

        startForeground(3, builder.build());
    }
}
