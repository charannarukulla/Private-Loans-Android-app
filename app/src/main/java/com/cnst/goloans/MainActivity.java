package com.cnst.goloans;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    //And also a Firebase Auth object
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
loading=findViewById(R.id.loading);
loading.setVisibility(View.INVISIBLE);

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