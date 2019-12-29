package com.sunst.alidd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ScreenRecordActivity extends AppCompatActivity {
    public static final int REQUEST_MEDIA_PROJECTION = 18;

    public static boolean isScrennShot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCapturePermission();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (isScrennShot) {
                ScreenShotUtil.permissionResult(resultCode, data);
            } else {
                ScreenRecordUtil.permissionResult(resultCode, data);
            }


            finish();
        }
    }

    //请求权限
    private void requestCapturePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0 之后才允许使用屏幕截图
            return;
        }
        if (isScrennShot) {
            startActivityForResult(ScreenShotUtil.mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        } else {
            startActivityForResult(ScreenRecordUtil.mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenShotUtil.isInit = true;
        Log.e("ScreenRecordActivity：", "onDestroy");
    }
}
