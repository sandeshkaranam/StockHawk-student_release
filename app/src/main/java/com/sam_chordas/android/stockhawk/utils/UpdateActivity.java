package com.sam_chordas.android.stockhawk.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sam_chordas.android.stockhawk.rest.Utils;
import com.sam_chordas.android.stockhawk.service.StockIntentService;

/**
 * Created by kssand on 22-Mar-16.
 */
public class UpdateActivity extends BroadcastReceiver {

    private Intent mServiceIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(this.getClass().getSimpleName(), "on receive");
        boolean isConnected= Utils.isNetworkConnected(context);
            if (isConnected){
                context.sendBroadcast(new Intent("INTERNET_AVAILABLE"));
            } else{
                context.sendBroadcast(new Intent("INTERNET_LOST"));
            }
    }
}
