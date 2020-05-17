package com.livery.demo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;

import com.example.cj.videoeditor.Constants;
import com.sunsta.bear.AnApplication;
import com.sunsta.livery.app.IApp;
import com.sunsta.livery.app.PictureAppMaster;
import com.sunsta.livery.crash.PictureSelectorCrashUtils;

public class AliddApplication extends AnApplication implements IApp, CameraXConfig.Provider {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("thread", "  线程值  " + Thread.currentThread());
        Constants.init(this);

        /** PictureSelector日志管理配制开始 **/
        // PictureSelector 绑定监听用户获取全局上下文或其他...
        PictureAppMaster.getInstance().setApp(this);
        // PictureSelector Crash日志监听
        PictureSelectorCrashUtils.init((t, e) -> {
            // Crash之后的一些操作可再此处理，没有就忽略...

        });
        /** PictureSelector日志管理配制结束 **/
    }

    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }

    @Override
    public Context getAppContext() {
        return getApplication();
    }
}