package com.kong.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadCast_3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("BroadCast_3","广播已接收");
        Log.v("BroadCast_3",intent.getAction().toString());
        Log.v("BroadCast_2", intent.getExtras().get("title").toString());
    }
}
