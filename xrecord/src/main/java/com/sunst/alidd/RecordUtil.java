package com.sunst.alidd;

import android.app.Activity;
import android.util.DisplayMetrics;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Desc: 录屏工具类
 */
public class RecordUtil {

    private static final String TAG = "RecordUtil";
    private static int RECORD_Bitrate = 3 * 1024 * 1024; // 保证视频基本清晰的码率
    private long duration = 303 * 1000; // 默认视频最长录制150s
    private Activity activity;
    private RecordListener recordListener;
    private String savePath;
    private Timer timer; // 定时器，处理录制时长到达后自动关闭
    private TimerTask timerTask;

    public RecordUtil(Activity activity) {
        this.activity = activity;
        timer = new Timer();
    }

    /**
     * 设置录制流程监听
     *
     * @param recordListener
     * @return
     */
    public RecordUtil setRecordListener(RecordListener recordListener) {
        this.recordListener = recordListener;
        return this;
    }

    /**
     * 请求录屏服务权限
     */
    public void createRecord() {
        if (null != activity) {
            ScreenRecordUtil.init(getScreenWidth(), getScreenHeight(), RECORD_Bitrate);
            savePath = RecordFileUtil.createFilePath(activity);

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    ScreenRecordUtil.getInstance().destroy(activity);

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != recordListener) {
                                        recordListener.onEnd(savePath);
                                    }
                                }
                            });
                        }
                    }, 1500);
                }
            };
            //开始录屏
            ScreenRecordUtil.getInstance().create(activity, savePath, new OnScreenRecordListener() {
                @Override
                public void onSuccess() {
                    if (null != recordListener) {
                        recordListener.onStart();
                    }
                }

                @Override
                public void onFailed() {
                    if (null != recordListener) {
                        recordListener.onRefuse();
                    }

                    if (null != timerTask) {
                        timerTask.cancel();
                        timerTask = null;
                    }
                }
            });
        }
    }

    /**
     * 开始录制
     */
    public void startRecord() {
        ScreenRecordUtil.getInstance().start(activity);

        if (null != timerTask) {
            timer.schedule(timerTask, duration);
        }
    }

    /**
     * 结束录制
     */
    public void stopRecord() {
        if (null != timerTask) {
            timerTask.cancel();
            timerTask = null;
        }

        ScreenRecordUtil.getInstance().destroy(activity);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != recordListener) {
                            recordListener.onEnd(savePath);
                        }
                    }
                });
            }
        }, 1500);
    }

    /**
     * 清除
     */
    public void clear() {
        if (null != timerTask) {
            timerTask.cancel();
            timerTask = null;
        }

        if (null != timer) {
            timer.cancel();
            timer = null;
        }

        activity = null;
        recordListener = null;
    }

    private int getScreenWidth() {
        DisplayMetrics outMetrics = activity.getResources().getDisplayMetrics();
        int width = Math.min(outMetrics.widthPixels, 1080);

        if (width % 2 != 0) {
            width--;
        }

        return width;
    }

    private int getScreenHeight() {
        DisplayMetrics outMetrics = activity.getResources().getDisplayMetrics();
        int height = Math.min(outMetrics.heightPixels, 1920);

        if (height % 2 != 0) {
            height--;
        }

        return height;
    }

    public interface RecordListener {
        void onStart(); // 录制开始

        void onRefuse(); // 录制拒绝

        void onEnd(String videoPath); // 录制完成，返回视频路径
    }
}
