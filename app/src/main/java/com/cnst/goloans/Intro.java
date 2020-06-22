package com.cnst.goloans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import static android.app.PendingIntent.getActivity;

public class Intro extends AppCompatActivity {
int count=0;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    Button btn1;
    Button btn2;
    int MY_REQUEST_CODE = 0;
    ConstraintLayout bgc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        bgc=findViewById(R.id.layout);
        bgc.setBackgroundColor(Color.parseColor("#FFFFFF"));
btn2=findViewById(R.id.button2);
btn2.setVisibility(View.INVISIBLE);

        imageView=findViewById(R.id.one);
        imageView1=findViewById(R.id.two);
        imageView2=findViewById(R.id.three);
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().hasExtra("pushnotification")) {
            Intent intent = new Intent(this, Notification.class);
            startActivity(intent);
            finish();}
        // Creates instance of the manager.
        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(Intro.this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE

                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(1)) {
                    int MY_REQUEST_CODE = 0;
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                1,
                                // The current activity making the update request.
                                Intro.this,
                                // Include a request code to later monitor this update request.
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                    // Request the update.
                }
            }
        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        int state= pref.getInt("stage", 0); // getting Integer

        if (state==1)
        {
            startActivity(new Intent(Intro.this,MainActivity.class));
            finish();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "YOU CANT USE APP WITHOUT UPDATING", Toast.LENGTH_SHORT).show();
                finish();
                moveTaskToBack(true);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }
    public void next(View view) {
        count++;

        if (count==1)
        {
            imageView.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.INVISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            imageView=findViewById(R.id.two);
            bgc.setBackgroundColor(Color.parseColor("#BF8A61") );
        }
        if (count==2)
        {
            imageView.setVisibility(View.INVISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn1=findViewById(R.id.button);
            btn1.setVisibility(View.INVISIBLE);
            imageView=findViewById(R.id.three);
            bgc.setBackgroundColor(Color.parseColor("#FFFFFF") );

        }

    }

    @SuppressLint("CommitPrefEdits")
    public void start(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("stage",1);
        editor.apply();
        startActivity(new Intent(Intro.this,MainActivity.class));
        finish();

    }
}
