//package niu.edu.water;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//public class alarmReceiver extends BroadcastReceiver {
//
//    //    建立notificationManager與notification物件
//    private Notification notificationManager;
//    private Notification notification;
//
//    //    建立能辨識通知差別的ID
//    private final static int NOTIFICATION_ID=0;
//
//
//    //     建立通知方法
//    private void broadcastNotify(Context context, PendingIntent pendingIntent) {
//        notificationManager=
//                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        //        建立通知物件內容
//        notification = new Notification.Builder(context)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle("訊息")
//                .setContentText("要量血壓囉!")
//                .setContentIntent(pendingIntent)
//                .setVibrate(new long[]{0,100,200,300,400,500})
//                .build();
//
//        //        發送通知
//        notificationManager.notify(NOTIFICATION_ID, notification);
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
////        實作觸發通知訊息，開啟首頁動作
//        Intent notifyIntent=new Intent(context,MainActivity.class);
//        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,notifyIntent,0);
//
//        //        執行通知
//        broadcastNotify(context, pendingIntent);
//    }
//}
