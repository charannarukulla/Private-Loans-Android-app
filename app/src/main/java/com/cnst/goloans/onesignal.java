package com.cnst.goloans;

import android.content.Intent;
import android.util.Log;

import com.cnst.goloans.MainActivity;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class onesignal implements OneSignal.NotificationOpenedHandler {

    private MainActivity application;

    public onesignal(MainActivity application) {
        this.application = application;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {

        // Get custom datas from notification
        JSONObject data = result.notification.payload.additionalData;
        if (data != null) {
            String myCustomData = data.optString("key", null);
        }

        // React to button pressed
        OSNotificationAction.ActionType actionType = result.action.type;
        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

        // Launch new activity using Application object
        startApp();
    }

    private void startApp() {
        Intent intent = new Intent(application, Notification.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }
}