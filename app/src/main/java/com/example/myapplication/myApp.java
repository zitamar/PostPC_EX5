package com.example.myapplication;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;



public class myApp extends Application {
    private  LocalSendSmsBroadcastReceiver receiver = new LocalSendSmsBroadcastReceiver();
    String ACTION = "POST_PC.ACTION_SEND_SMS";
    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(ACTION);
        this.registerReceiver(receiver,filter);
        Log.d("finish launch myApp ", "success");
    }
}
