package com.cnst.goloans;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.CampaignMetadata;
import com.google.firebase.inappmessaging.model.InAppMessage;

public class MyClickListener implements FirebaseInAppMessagingClickListener {
    private static Context mContext;
    @Override
    public void messageClicked(InAppMessage inAppMessage, Action action) {
        // Determine which URL the user clicked
        String url = action.getActionUrl();
        SharedPreferences pref = mContext.getSharedPreferences("notifi", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("url",url);
        editor.apply();
        mContext.startActivity(new Intent(mContext.getApplicationContext(),Notification.class));
        // Get general information about the campaign
        CampaignMetadata metadata = inAppMessage.getCampaignMetadata();

        // ...
    }


    public static Context getContext(){

        return mContext;
    }
}
