package com.cnst.goloans;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class fillform extends AppCompatActivity {
    private int mYear, mMonth, mDay;
    int valid = 0;
    EditText fn;
    EditText ln;
    TextView dob;
    EditText phone;
    EditText city;
    EditText pincode;
    Spinner gender;
    Button submit;
    Spinner typeofloan;
    Spinner emptype;
    Spinner rate;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    Spinner share;
    EditText sal;
    EditText pan;
    private AdView mAdView;
    String admin;
    EditText aadar;
    Spinner source;
    Spinner bfl;
    String url="https://cnsolotech.com/appnotifiserver.php?send_notification&body=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillform);
        dob = findViewById(R.id.dobdisplay);
        fn = findViewById(R.id.fn);
        MyClickListener listener = new MyClickListener();
        FirebaseInAppMessaging.getInstance().addClickListener(listener);
        aadar = findViewById(R.id.aadar);
        pan = findViewById(R.id.pan);
        submit = findViewById(R.id.submit);
        share = findViewById(R.id.aosdsusrcce);
        rate = findViewById(R.id.aourcce);
        ln = findViewById(R.id.ln);
        bfl = findViewById(R.id.aousrcce);
        sal = findViewById(R.id.sal);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.city);
        source = findViewById(R.id.aource);
        emptype = findViewById(R.id.emptype);
        typeofloan = findViewById(R.id.typeofloan);
        pincode = findViewById(R.id.pincode);
        gender = findViewById(R.id.gender);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Display Banner ad
        mAdView.loadAd(adRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void dobpick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void check() {
        if (!fn.getText().toString().equals("")) {
            valid += 1;
        } else { valid=0;
            fn.setError("ENTER FIRST NAME");
            Toast.makeText(this, "ENTER FIRST NAME", Toast.LENGTH_SHORT).show();
        }

        if (!ln.getText().toString().equals("")) {
            valid += 1;
        } else { valid=0;
            ln.setError("ENTER LAST NAME");
            Toast.makeText(this, "ENTER LAST NAME", Toast.LENGTH_SHORT).show();
        }
        if (!phone.getText().toString().equals("") && phone.length() == 10) {
            valid += 1;
        } else { valid=0;
            phone.setError("ENTER PHONE NUMBER OF 10 NUMBERS");
            Toast.makeText(this, "ENTER PHONE NUMBER OF 10 NUMBERS;", Toast.LENGTH_SHORT).show();
        }
        if (!city.getText().toString().equals("")) {
            valid += 1;
        } else { valid=0;
            city.setError("ENTER CITY");
            Toast.makeText(this, "ENTER CITY", Toast.LENGTH_SHORT).show();
        }
        if (!gender.getSelectedItem().toString().equals("TAP TO CHOOSE")) {
            valid += 1;

        } else { valid=0;
            Toast.makeText(this, "Choose gender", Toast.LENGTH_SHORT).show();
        }
        if (!source.getSelectedItem().toString().equals("TAP TO CHOOSE")) {
            valid += 1;

        } else { valid=0;
            Toast.makeText(this, "Choose source of income", Toast.LENGTH_SHORT).show();
        }
        if (!pincode.getText().toString().equals("") && pincode.length() == 6) {
            valid += 1;
        } else { valid=0;
            pincode.setError("ENTER PINCODE OF 6 NUMBERS");
            Toast.makeText(this, "ENTER PINCODE OF 6 NUMBERS;", Toast.LENGTH_SHORT).show();
        }
        if (!pan.getText().toString().equals("") && pan.length() == 10) {
            valid += 1;
        } else { valid=0;
            pan.setError("ENTER PAN OF 10 ALPHA-NUMERIC CHART");
            Toast.makeText(this, "ENTER PAN OF 10 ALPHA-NUMERIC CHART", Toast.LENGTH_SHORT).show();
        }
        if (!aadar.getText().toString().equals("") && aadar.length() == 12) {
            valid += 1;
        } else {
            valid=0;
            aadar.setError("ENTER AADAR OF 12 NUMERIC ");
            Toast.makeText(this, "ENTER AADAR OF 12 NUMERIC ", Toast.LENGTH_SHORT).show();
        }
        if (!dob.getText().toString().equals("")) {
            valid += 1;
        } else {
            valid=0;
            Toast.makeText(this, "Choose DOB", Toast.LENGTH_SHORT).show();
        }
        if (!typeofloan.getSelectedItem().toString().equals("TAP TO CHOOSE")) {
            valid += 1;

        } else {
            valid=0;
            Toast.makeText(this, "Choose type of loan", Toast.LENGTH_SHORT).show();
        }
        if (!emptype.getSelectedItem().toString().equals("TAP TO CHOOSE")) {
            valid += 1;

        } else {
            valid=0;
            Toast.makeText(this, "Choose Employment type", Toast.LENGTH_SHORT).show();
        }
        if (!sal.getText().toString().equals("")) {
            valid += 1;
        } else {
            valid=0;
            sal.setError("ENTER INCOME");
            Toast.makeText(this, "Enter income", Toast.LENGTH_SHORT).show();
        }
        if (!rate.getSelectedItem().toString().equals("TAP TO CHOOSE")) {
            valid += 1;

        } else {
            valid=0;
            Toast.makeText(this, "Choose Rate ", Toast.LENGTH_SHORT).show();
        }
        if (!bfl.getSelectedItem().toString().equals("TAP TO CHOOSE")) {
            valid += 1;

        } else {
            valid=0;
            Toast.makeText(this, "Choose Do you have any previous loan", Toast.LENGTH_SHORT).show();
        }
        if (!share.getSelectedItem().toString().equals("TAP TO CHOOSE")) {
            valid += 1;

        } else {
            valid=0;
            Toast.makeText(this, "Choose Share my file details", Toast.LENGTH_SHORT).show();
        }

    }

    public void submit(View view) {
        check();

        if (valid >= 16) {
            uploaddata();
        }

    }

    private void uploaddata() {
        Toast.makeText(this, "PLEASE WAIT", Toast.LENGTH_SHORT).show();
        final String fns,lns,phones = phone.getText().toString(),citys = city.getText().toString(),genders = gender.getSelectedItem().toString(),pincodes = pincode.getText().toString(),dobs = dob.getText().toString(),typeofloans = typeofloan.getSelectedItem().toString(),emptypes = emptype.getSelectedItem().toString(),sals = sal.getText().toString(),sources = source.getSelectedItem().toString(),rates = rate.getSelectedItem().toString(),bfls = bfl.getSelectedItem().toString(),pans = pan.getText().toString(),aadars = aadar.getText().toString(),shares = share.getSelectedItem().toString();
        fns=fn.getText().toString();
        lns=ln.getText().toString();

        final FirebaseUser user=firebaseAuth.getCurrentUser();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        assert user != null;
        final String uid= Objects.requireNonNull(user.getUid());
        final Map<String, Object> user1 = new HashMap<>();
        user1.put("firstname",fns);
        assert user != null;
        user1.put("email", user.getEmail());
user1.put("fcmid",refreshedToken);
        user1.put("lastname", lns);
        user1.put("phone",phones);
        user1.put("city",citys);
        user1.put("gender",genders);
        user1.put("pincode",pincodes);
        user1.put("dob",dobs);
        user1.put("typeofloan",typeofloans);
        user1.put("emptype",emptypes);
        user1.put("income",sals);
        user1.put("source",sources);
        user1.put("rate",rates);
        user1.put("previousloan",bfls);
        user1.put("pan",pans);
        user1.put("aadar",aadars);
        user1.put("share",shares);
        user1.put("uid",uid);

db.collection("pending").document(uid).set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void aVoid) {

        HashMap<String,Object>app =new HashMap<>();
        app.put("filled","yes");
        app.put("uid",uid);
        app.put("email", user.getEmail());
        app.put("name", user.getDisplayName());
        app.put("status","pending");
        final WebView webView=findViewById(R.id.notifi);
        webView.setVisibility(View.INVISIBLE);
        db.collection("admin").document("fcmid").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                admin=documentSnapshot.getString("fcmid");
                String lin="http://goloans.epizy.com/appnotifiserver.php?send_notification&token="+admin+"&body="+fns+" applied for loan";

                webView.loadUrl(lin);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
            }
        });
        db.collection("users").document(uid).set(app).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
startActivity(new Intent(fillform.this,steptwo.class));
finish();


            }
        });
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(fillform.this, "FAILED TO ADD", Toast.LENGTH_SHORT).show();
    }
});
    }
}
