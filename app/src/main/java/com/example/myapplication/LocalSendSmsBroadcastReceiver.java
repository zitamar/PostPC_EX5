package com.example.myapplication;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class LocalSendSmsBroadcastReceiver extends BroadcastReceiver {
    public String PHONE_KEY;
    public String CONTENT_KEY;
    public String DEFAULT = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean hasSmsPermission =
                ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED;


        if (!hasSmsPermission)
        {
            Log.d("ERROR", "no prenission");
        }
        else
        {

            int phoneNumber = intent.getIntExtra(PHONE_KEY, 0);
            String smsContent = intent.getStringExtra(CONTENT_KEY);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(String.valueOf(phoneNumber), null, smsContent, null, null);
            Toast.makeText(context, "SMS sent.",
                    Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                //String channelName = "sendSmsNotif";
                NotificationChannel channel = new NotificationChannel("80", "sendSmsCnl"
                        , NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("sms channel");
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
            int notificationID = 220;
                Notification ntfc = new NotificationCompat.Builder(context, "80")
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                        .setContentTitle("title")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build();
            NotificationManagerCompat.from(context).notify(notificationID,ntfc);



            }
        }




    }