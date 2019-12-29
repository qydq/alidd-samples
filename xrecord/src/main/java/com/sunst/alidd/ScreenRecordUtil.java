package com.sunst.alidd;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import static android.app.Activity.RESULT_OK;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScreenRecordUtil {

    private static ScreenRecorder mRecorder;
    private static int mScreenDensity;
    private static int mRecordWidth;
    private static int mRecordheight;
    private static int mScreenRecordBitrate = 30;
    private static String savePath;
    private static OnScreenRecordListener onScreenRecordListener;

    static MediaProjectionManager mMediaProjectionManager;

    private static Intent data; // 用户同意录屏后系统返回的Intent
    private Intent serviceIntent;

    private ScreenRecordUtil() {
    }

    public static void init(int width, int height, int screenRecordBitrate) {
        mRecordWidth = width;
        mRecordheight = height;
        mScreenRecordBitrate = screenRecordBitrate;
    }

    private static class ScreenRecordHolder {
        private static ScreenRecordUtil instance = new ScreenRecordUtil();
    }

    public static ScreenRecordUtil getInstance() {
        return ScreenRecordHolder.instance;
    }

    public void create(Activity activity, String savePath, OnScreenRecordListener onScreenRecordListener) {
        ScreenRecordUtil.savePath = savePath;
        ScreenRecordUtil.onScreenRecordListener = onScreenRecordListener;
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        mMediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        ScreenRecordActivity.isScrennShot = false;
        activity.startActivity(new Intent(activity, ScreenRecordActivity.class));
    }


    //返回可以开始录屏的数据
    static void permissionResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            ScreenRecordUtil.data = data;
            if (null != onScreenRecordListener) {
                onScreenRecordListener.onSuccess();
            }
        } else {
            if (null != onScreenRecordListener) {
                onScreenRecordListener.onFailed();
            }
        }
    }

    /**
     * 开始录制
     */
    public void start(Activity activity) {
        if (null != data) {
            // 兼容android 10
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //获得录屏权限，启动Service进行录制
                serviceIntent = new Intent(activity, ScreenRecordService.class);
                serviceIntent.putExtra("resultCode", RESULT_OK);
                serviceIntent.putExtra("resultData", data);
                serviceIntent.putExtra("width", mRecordWidth);
                serviceIntent.putExtra("height", mRecordheight);
                serviceIntent.putExtra("density", mScreenDensity);
                serviceIntent.putExtra("bitrate", mScreenRecordBitrate);
                serviceIntent.putExtra("path", savePath);
                activity.startForegroundService(serviceIntent);
            } else {
                MediaProjection mMediaProjection = mMediaProjectionManager.getMediaProjection(RESULT_OK, data);
                mRecorder = new ScreenRecorder(mRecordWidth, mRecordheight, mScreenRecordBitrate, mScreenDensity, mMediaProjection, savePath);
                mRecorder.start();
            }
        }

    }

    /**
     * 结束录制
     */
    public void destroy(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (null != serviceIntent) {
                activity.stopService(serviceIntent);
            }
        } else {
            if (mRecorder != null) {
                mRecorder.quit();
                mRecorder = null;
                mMediaProjectionManager = null;
            }
        }
    }

    public static int getmRecordWidth() {
        return mRecordWidth;
    }

    public static void setmRecordWidth(int mRecordWidth) {
        ScreenRecordUtil.mRecordWidth = mRecordWidth;
    }

    public static int getmRecordheight() {
        return mRecordheight;
    }

    public static void setmRecordheight(int mRecordheight) {
        ScreenRecordUtil.mRecordheight = mRecordheight;
    }

    public static int getmScreenRecordBitrate() {
        return mScreenRecordBitrate;
    }

    public static void setmScreenRecordBitrate(int mScreenRecordBitrate) {
        ScreenRecordUtil.mScreenRecordBitrate = mScreenRecordBitrate;
    }
}
