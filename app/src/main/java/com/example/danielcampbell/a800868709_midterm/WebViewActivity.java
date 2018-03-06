package com.example.danielcampbell.a800868709_midterm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by danielcampbell on 10/23/17.
 * Daniel Campbell
 * Group21InClass06.zip
 */


public class WebViewActivity extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view2);
        Intent i = getIntent();
        String url = (String)i.getExtras().get("url");
        System.out.println("test");
        System.out.println(url);
        wv =(WebView)findViewById(R.id.webView2);

        wv.loadUrl(url);




    }
}
