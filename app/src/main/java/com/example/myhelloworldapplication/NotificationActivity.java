package com.example.myhelloworldapplication;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotificationActivity extends Activity {

    private static int id = 1200;


    private String createNotificationChannel (String channelID, String channelNAME,int level){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
//            //设置提示音
//            channel.setSound();
            // 开启指示灯
            channel.enableLights(true);
            //开启震动
            channel.enableVibration(true);
//            //设置锁屏展示
//            channel.setLockscreenVisibility();
            //设置渠道描述
            channel.setDescription("A notification channel for warning the user.");

            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab_forth);

        Intent intent = new Intent(this, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Note")
                .setContentText("Receive a new message")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Dear users, as the UV intensity at the beach is above the safe range, please arrange" +
                                " your time reasonably to avoid accidents. Thank you for your cooperation!"))
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, notification.build());

    }

}
