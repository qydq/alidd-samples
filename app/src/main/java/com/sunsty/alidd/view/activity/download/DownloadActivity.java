package com.sunsty.alidd.view.activity.download;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ali.faster.ToastUtils;
import com.sunsty.alidd.R;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStartDownload;
    private Button btnPauseDownload;
    private Button btnCancelDownload;
    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https_download);
        btnStartDownload = findViewById(R.id.btnStartDownload);
        btnPauseDownload = findViewById(R.id.btnPauseDownload);
        btnCancelDownload = findViewById(R.id.btnCancelDownload);
        btnCancelDownload.setOnClickListener(this);
        btnPauseDownload.setOnClickListener(this);
        btnStartDownload.setOnClickListener(this);

        Intent intent = new Intent(this, DownloadService.class);

        /*fix : https://blog.csdn.net/qq_33649832/article/details/87880455
        .app.RemoteServiceException: Bad notification for startForeground: java.lang.RuntimeE
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0以上通过startForegroundService启动service
            startForegroundService(intent);
        } else {
            startService(intent);
        }

        bindService(intent, connection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.btnStartDownload:
                String apkUrl = "http://download.fir.im/v2/app/install/595c5959959d6901ca0004ac?download_token=1a9dfa8f248b6e45ea46bc5ed96a0a9e&source=update";
                downloadBinder.startDownload(apkUrl);
                break;
            case R.id.btnPauseDownload:
                downloadBinder.pauseDownload();
                break;
            case R.id.btnCancelDownload:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtils.s(this, "拒绝权限无法使用程序");
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
