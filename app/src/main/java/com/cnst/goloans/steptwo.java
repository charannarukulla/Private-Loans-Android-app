package com.cnst.goloans;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;

import static android.view.View.VISIBLE;

public class steptwo extends AppCompatActivity {
    WebView webView;
    ImageView loading;
    String urlmain = "https://goloans.co.in/blog/";
    Animation animation;
    private InterstitialAd mInterstitialAd;
    int loaded=0,adload=0;
    FirebaseFirestore firebaseFirestore;
    ImageView status;
    FirebaseAuth firebaseAuth;
    TextView textView;
    private RewardedVideoAd mRewardedVideoAd;
    private RewardedAd rewardedAd;
    private FrameLayout adContainerView;
    private AdView adView;
    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steptwo);
        webView = findViewById(R.id.web);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MyClickListener listener = new MyClickListener();
        FirebaseInAppMessaging.getInstance().addClickListener(listener);
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9734806088592261/8411359095");
        adContainerView.addView(adView);
        loadBanner();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9734806088592261/5884887234");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        loadRewardedAd();
        webView.loadUrl(urlmain);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new myWebClient());
        loading = findViewById(R.id.loading);
        status = findViewById(R.id.imageView);
        firebaseFirestore = FirebaseFirestore.getInstance();
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String statuss = documentSnapshot.getString("status");
                if (statuss.equals("pending")) {
                    status.setBackgroundResource(R.drawable.wait);
                } else if (statuss.equals("approved")) {
                    status.setBackgroundResource(R.drawable.approved);
                } else {
                    status.setVisibility(View.INVISIBLE);
                    Toast.makeText(steptwo.this, "YOUR APPLICATION HAS BEEN REJECTED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        loading.startAnimation(animation);
        loading.setVisibility(VISIBLE);


        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(webView.getProgress());
        int i;

        progressBar.setProgress(webView.getProgress());
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 4000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 8000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 12000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 16000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 20000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 24000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 28000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 32000);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 40000);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(webView.getProgress());
            }
        }, 44000);


    }

    @Override
    public void onResume() {
        super.onResume();
        webView.setVisibility(VISIBLE);
        loading.setVisibility(View.INVISIBLE);

    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder()
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }
    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationBannerAdSizeWithWidth(this, adWidth);
    }


    private void loadRewardedAd() {

        if (rewardedAd == null || !rewardedAd.isLoaded()) {
            rewardedAd = new RewardedAd(this, "ca-app-pub-9734806088592261/4081012677");

            rewardedAd.loadAd(
                    new AdRequest.Builder().build(),
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onRewardedAdLoaded() {
                            // Ad successfully loaded.
loaded=0;

                        }

                        @Override
                        public void onRewardedAdFailedToLoad(int errorCode) {
                            // Ad failed to load.
                            adload=1;

                        }
                    });
        }
    }


    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            loaded=1;
            loading = findViewById(R.id.loading);
           webView=findViewById(R.id.web);
            webView.setVisibility(VISIBLE);

            loading.clearAnimation();
            loading.setVisibility(View.INVISIBLE);
            ProgressBar progressBar=findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

            super.onPageFinished(view, url);

        }






        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {

            if (rewardedAd.isLoaded()){
                AlertDialog.Builder builder = new AlertDialog.Builder(steptwo.this);
                builder.setMessage("You need to watch an ad inorder to view this premium content")
                        .setTitle("PREMIUM CONTENT");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Activity activityContext = steptwo.this;
                        RewardedAdCallback adCallback = new RewardedAdCallback() {
                            @Override
                            public void onRewardedAdOpened() {
                                // Ad opened.
                            }

                            @Override
                            public void onRewardedAdClosed() {
                              dialog.dismiss();  // Ad closed.
                            }

                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem reward) {
                                loading = findViewById(R.id.loading);
                                webView.setVisibility(View.INVISIBLE);
                                loading = findViewById(R.id.loading);
                                loading.startAnimation(animation);
                                loading.setVisibility(VISIBLE);
                                ProgressBar progressBar = findViewById(R.id.progressBar);
                                progressBar.setVisibility(VISIBLE);
                                view.loadUrl(url);
                                // User earned reward.
                            }

                            @Override
                            public void onRewardedAdFailedToShow(int errorCode) {
                                // Ad failed to display.
                            }
                        };
                        rewardedAd.show(activityContext, adCallback);
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                loadRewardedAd();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            loading = findViewById(R.id.loading);
                            webView.setVisibility(View.INVISIBLE);
                            loading = findViewById(R.id.loading);
                            loading.startAnimation(animation);
                            loading.setVisibility(VISIBLE);
                            ProgressBar progressBar = findViewById(R.id.progressBar);
                            progressBar.setVisibility(VISIBLE);
                            view.loadUrl(url);
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());

                        }});


                } else {loadRewardedAd();
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    loading = findViewById(R.id.loading);
                    webView.setVisibility(View.INVISIBLE);
                    loading = findViewById(R.id.loading);
                    loading.startAnimation(animation);
                    loading.setVisibility(VISIBLE);
                    ProgressBar progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(VISIBLE);
                    view.loadUrl(url);
                }
            }





            // TODO Auto-generated method stub


            return true;

        }
    }
    


    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (!webView.getUrl().equals(urlmain))
        {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {


            webView.goBack();
        }
            return true;
        }
else
        {
            finish();
            moveTaskToBack(true);
            
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();

        webView.setVisibility(View.INVISIBLE);
        loading.setVisibility(VISIBLE);
        loading.setAnimation(animation);

    }
}
