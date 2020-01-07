package com.sunsty.alidd.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ali.model.entity.Download;
import com.ali.take.DataService;
import com.ali.take.NetDownloadService;
import com.sunsty.alidd.R;


public class HttpsDownloadActivity extends AppCompatActivity {
    public static final String MESSAGE_PROGRESS = "message_progress";
    private LocalBroadcastManager bManager;

    private AppCompatButton btn_download;
    private ProgressBar progress;
    private TextView progress_text;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_PROGRESS)) {
                Download download = intent.getParcelableExtra("download");
                progress.setProgress(download.getProgress());
                if (download.getProgress() == 100) {
                    progress_text.setText("File Download Complete");
                } else {

                    DataService dataService = DataService.getInstance();
                    String currentDownloadSize = dataService.getDataSize(download.getCurrentFileSize());
                    String totalDownloadFileSize = dataService.getDataSize(download.getTotalFileSize());
                    String shouldTipsProgress = currentDownloadSize + "/" + totalDownloadFileSize;
                    progress_text.setText(shouldTipsProgress);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https_download);
        btn_download = findViewById(R.id.btn_download);
        progress = findViewById(R.id.progress);
        progress_text = findViewById(R.id.progress_text);

        registerReceiver();

        btn_download.setOnClickListener(v -> shouldDownLoad());
    }

    private void shouldDownLoad() {
        Intent intent = new Intent(HttpsDownloadActivity.this, NetDownloadService.class);
        startService(intent);
    }

    private void registerReceiver() {
        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册时，使用注册时的manager解绑
        bManager.unregisterReceiver(broadcastReceiver);
    }
}