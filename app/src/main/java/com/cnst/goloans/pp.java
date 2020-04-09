package com.cnst.goloans;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class pp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pp);
        WebView webView=findViewById(R.id.pp);
        webView.loadUrl("https://go-loans-3cde8.firebaseapp.com/privacy_policy.html");
    }
}
