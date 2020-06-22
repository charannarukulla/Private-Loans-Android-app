package com.cnst.goloans;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OneSignal;


import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;
TextView textView;
Button signin;
ImageView loading;
    //Tag for the logs optional
onesignal onesignal;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741";
    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    private FrameLayout adContainerView;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
loading=findViewById(R.id.loading);
loading.setVisibility(View.INVISIBLE);


        FirebaseMessaging.getInstance().subscribeToTopic("users")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (!task.isSuccessful()) {

                        }

                    }
                });
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new onesignal(this))
                .init();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9734806088592261/8411359095");
        adContainerView.addView(adView);
        loadBanner();


    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Now we will attach a click listener to the sign_in_button
        //and inside onClick() method we are calling the signIn() method that will open
        //google sign in intent
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
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
    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already signed in
        //we will close this activity
        //and take the user to profile activity
        if (mAuth.getCurrentUser() != null) {
            hideall();
            final FirebaseUser user = mAuth.getCurrentUser();

            assert user != null;
            final String uid = user.getUid();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            Task<DocumentSnapshot> documentReference = firebaseFirestore.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    if (task.getResult().getString("type")==null) {
                        if (task.getResult().getString("filled") == null) {
                            Toast.makeText(MainActivity.this, "FILL THIS FORM", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, fillform.class));
finish();
                        } else {
                            Toast.makeText(MainActivity.this, "WELCOME BACK  " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, steptwo.class));
                            finish();
                        }
                    }
                    if (task.getResult().getString("type") != null){
                    if (task.getResult().getString("type").equals("admin")){
                        startActivity(new Intent(MainActivity.this,choose.class));
finish();
                    }
                }}
            });
        }}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
hideall();
        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final FirebaseUser user = mAuth.getCurrentUser();

                            assert user != null;
                            final String uid=user.getUid();
                            FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
                            Task<DocumentSnapshot> documentReference=firebaseFirestore.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                    if (task.getResult().getString("type")=="admin"){
                                        startActivity(new Intent(MainActivity.this,choose.class));
finish();
                                    }
                                    if (task.getResult().getString("filled")==null&&task.getResult().getString("type")==null)
                                    {
                                        Map<String, Object> user1 = new HashMap<>();
                                        user1.put("uid",uid);
                                        user1.put("email", user.getEmail());
                                        user1.put("name", user.getDisplayName());
                                        user1.put("fcmid",refreshedToken);

// Add a new document with a generated ID
                                        db.collection("users").document(uid).set(user1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        startActivity(new Intent(MainActivity.this,fillform.class));
finish();
                                                        Toast.makeText(MainActivity.this, "YOU ARE NEW USER PLEASE FILL THIS APPLICATION", Toast.LENGTH_SHORT).show();
                                                    }
                                                }) .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                showall();
                                                Toast.makeText(MainActivity.this, "failed to add", Toast.LENGTH_SHORT).show();
                                            }
                                        })  ;
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "WELCOME BACK  "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,steptwo.class));
finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(MainActivity.this, "NEW USER", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
showall();
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

private  void hideall(){
        textView=findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);
       findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);
    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
    loading.startAnimation(animation);
        loading.setVisibility(View.VISIBLE);
}
    private  void showall(){
        textView=findViewById(R.id.textView);
        textView.setVisibility(View.VISIBLE);
        signin=findViewById(R.id.sign_in_button);
        signin.setVisibility(View.VISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }
    //this method is called on click
    private void signIn() {
        hideall();

        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void pp(View view) {
        startActivity(new Intent(MainActivity.this,pp.class));
    }
}