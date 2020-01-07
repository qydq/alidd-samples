package com.sunsty.alidd.take;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.sunsty.alidd.R;


/**
 * Desc: 录屏通知
 */
public class NotificationUtil {

    public static final int START_RECORD = 1; // 开始录制
    public static final int END_RECORD = 2; // 结束录制

    /**
     * 发送录屏开始通知
     * @param context
     */
    public static void sendRecordStartNotice(Context context, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, START_RECORD, intent, PendingIntent.FLAG_UPDATE_CURRENT);//PendingIntent.FLAG_UPDATE_CURRE

        // 获取系统的通知管理器(必须设置channelId)
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // 创建一个通知(必须设置channelId)
        String channelId = "record01"; // 通知渠道

        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setContentTitle("通知")
                .setContentText("录屏中")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)//设置可点击跳转
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setFullScreenIntent(null, false);

        // 兼容  API 26，Android 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "录屏通知",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            notificationBuilder.setChannelId(channelId);
        }

        Notification  notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;

        // 发送通知(Notification与NotificationManager的channelId必须对应)
        if (notificationManager != null) {
            notificationManager.notify(START_RECORD, notification);
        }
    }

    /**
     * 发送录屏开始通知
     * @param context
     */
    public static void sendRecordEndNotice(Context context, Intent intent) {
        //参数：1：Context  2:一般不用 通常传入0  3：Intent  4:FLAG_CANCEL_CURRENT(),FLAG_NO_CREATE,FLAG_ONE_SHOT,FLAG_UPDATE_CURRENT
        //PendingIntent pendingIntent=PendingIntent.getActivity(BeforeSelectAlbumActivity.this,0,intent,0);//延迟跳转
        PendingIntent pendingIntent = PendingIntent.getActivity(context, END_RECORD, intent, PendingIntent.FLAG_UPDATE_CURRENT);//PendingIntent.FLAG_UPDATE_CURRE

        // 获取系统的通知管理器(必须设置channelId)
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // 创建一个通知(必须设置channelId)
        String channelId = "record01"; // 通知渠道

        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setContentTitle("通知")
                .setContentText("录屏完成，去上传吧")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)//设置可点击跳转
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setFullScreenIntent(pendingIntent, true);

        // 兼容  API 26，Android 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "录屏通知",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            notificationBuilder.setChannelId(channelId);
        }

        // 发送通知(Notification与NotificationManager的channelId必须对应)
        if (notificationManager != null) {
            notificationManager.notify(END_RECORD, notificationBuilder.build());
        }
    }

    /**
     * 通知栏，取消录屏完成通知
     * @param context
     */
    public static void clearRecordEndNotice(Context context, int notifyID) {
        NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(notifyID);
        }
    }
}
