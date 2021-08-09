package com.example.ftpdemo.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.example.ftpdemo.R;

import java.util.HashMap;
import java.util.Map;

public class NotificationUtil {

    private static final String channelId = "channelId";
    private static final String channelName = "文件上传下载";

    private static volatile NotificationUtil INSTANCE;

    private Context mContext;

    private Map<Integer, Notification.Builder> builderMap = new HashMap<>();

    public static NotificationUtil getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NotificationUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotificationUtil(context);
                }
            }
        }
        return INSTANCE;
    }

    private NotificationUtil(Context context) {
        mContext = context;
    }

    public void initNotificationParams(int notificationId,
                                              String title, String content) {
        Notification.Builder builder = null;
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(mContext, channelId);
            NotificationChannel channel = new NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            );
            manager.createNotificationChannel(channel);
        } else {
            builder = new Notification.Builder(mContext);
        }
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setProgress(100, 0, false);
        builder.setSmallIcon(R.drawable.format_unkown);
        manager.notify(notificationId, builder.build());
        builderMap.put(notificationId, builder);
    }

    public void notifyNotificationProgress(int id, int progress, String filename) {
        if (!builderMap.containsKey(id)) {
            return;
        }
        Notification.Builder builder = builderMap.get(id);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setProgress(100, progress, false);
        builder.setContentTitle(filename);
        manager.notify(id, builder.build());
        if (progress == 100) {
            manager.cancel(id);
            builderMap.remove(id);
        }
    }
}
