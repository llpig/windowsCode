package com.kong.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadCast_2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("BroadCast_2","广播已接收");
        Log.v("BroadCast_2",intent.getAction().toString());
        Log.v("BroadCast_2", intent.getExtras().get("title").toString());

        //忽略广播，该广播接收者之后的注册的接收者都不会在收到该广播
        //abortBroadcast();
    }
}
