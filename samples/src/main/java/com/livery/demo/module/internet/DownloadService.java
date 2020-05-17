package com.livery.demo.module.internet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sunsta.bear.faster.ToastUtils;
import com.livery.demo.R;

import java.io.File;

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功以后将前台service关闭，并且创建一个下载 成功的通知
            stopForeground(true);
            getNotificationManger().notify(1, getNotification("DownloadSuccess", -1));
            ToastUtils.s(getApplicationContext(), "下载成功");
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            //下载失败以后将前台service关闭，并且创建一个下载 成功的通知
            stopForeground(true);
            getNotificationManger().notify(1, getNotification("DownloadSuccess", -1));
            ToastUtils.s(getApplicationContext(), "下载失败");
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            ToastUtils.s(DownloadService.this, "下载暂停");
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            ToastUtils.s(DownloadService.this, "取消下载");
        }

        @Override
        public void onProgress(int progress) {
            getNotificationManger().notify(1, getNotification("Downloadding...", progress));
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManger().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private NotificationManager getNotificationManger() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Notification notification;
//        Intent intent = new Intent(this, DownloadActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        builder.setContentIntent(pendingIntent);
//        builder.setContentTitle(title);
//        if (progress > 0) {
//            //当progress大于或等于0时，才不需要显示下载进度
//            builder.setContentText(progress + "%");
//            builder.setProgress(100, progress, false);
//        }
//        notification = builder.build(); // 获取构建好的Notification


        Intent nfIntent = new Intent(this, DownloadActivity.class);
        Notification.Builder builderPatch = new Notification.Builder(this.getApplicationContext())
                .setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setSmallIcon(R.mipmap.base_refresh_loading01) // 设置状态栏内的小图标
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("正在上传...") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        if (progress > 0) {
            //当progress大于或等于0时，才不需要显示下载进度
            builderPatch.setContentText(progress + "%");
            builderPatch.setProgress(100, progress, false);
        }
        /**/
//        Notification.Builder builderPatch = new Notification.Builder(this.getApplicationContext());
//        builderPatch.setContentIntent(pendingIntent);// 设置PendingIntent
//        builderPatch.setSmallIcon(R.mipmap.ic_launcher);// 设置状态栏内的小图标
//        builderPatch.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        builderPatch.setContentTitle(title);
//        builderPatch.setContentText("正在上传...");// 设置上下文内容
//        builderPatch.setWhen(System.currentTimeMillis());// 设置该通知发生的时间
        /*//修改安卓8.1以上系统报错:android.app.RemoteServiceException: Bad notification for startForeground: java.lang.RuntimeException*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ONE_ID", "CHANNEL_ONE_NAME", NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);

            builderPatch.setChannelId("CHANNEL_ONE_ID");

        }
        notification = builderPatch.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        return notification;
    }
}
