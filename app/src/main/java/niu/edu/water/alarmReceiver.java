package niu.edu.water;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class alarmReceiver extends BroadcastReceiver {

    //    建立notificationManager與notification物件
    private NotificationManager notificationManager;
    private Notification notification;

    //    建立能辨識通知差別的ID
    private final static int NOTIFICATION_ID = 0;


    //     建立通知方法
    private void broadcastNotify(Context context, PendingIntent pendingIntent) {

        String id = "12345";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(id, "drink2", importance);
//                channel.enableLights(true);
//                channel.enableVibration(true);


        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.createNotificationChannel(channel);
        //        建立通知物件內容
        Notification notification = new NotificationCompat.Builder(context, "123")
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("提醒")
                .setContentText("該喝水囉!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        //        發送通知
        notificationManager.notify(1, notification);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        實作觸發通知訊息，開啟首頁動作
        Intent notifyIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, 0);

        //        執行通知
        broadcastNotify(context, pendingIntent);
        Log.d("接收通知", "通知通知通知通知通知通知通知");
    }
}
