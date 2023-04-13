package com.example.nasch_model;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

public class GetSpeed extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        while (true){
                            double r = MainActivity.main.getSpeed();

                            try {
                                Thread.sleep(2000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
        final String notificationChannel = "Get Speed Service id";
        NotificationChannel channel = new NotificationChannel(
                notificationChannel,
                notificationChannel,
                NotificationManager.IMPORTANCE_LOW
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder noti = new Notification.Builder(this,notificationChannel).setContentText("Nasch Model is running").setSmallIcon(R.drawable.ic_launcher_background);
        startForeground(1000,noti.build());
        return super.onStartCommand(intent,flags,startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
