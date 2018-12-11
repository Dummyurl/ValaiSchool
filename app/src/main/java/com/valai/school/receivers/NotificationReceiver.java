package com.valai.school.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.valai.school.services.NLService;

/**
 * @author by Mohit Arora on 20/2/18.
 */

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String event = intent.getExtras().getString(NLService.NOT_EVENT_KEY);
        if (event.trim().contentEquals(NLService.NOT_REMOVED)) {

        }
    }
}