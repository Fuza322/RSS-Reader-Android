package com.example.lab4_rss.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.lab4_rss.R;
import com.example.lab4_rss.network.NetworkState;
import com.example.lab4_rss.network.NetworkUtil;

import java.io.File;


public class PostActivity extends AppCompatActivity
{
    private WebView webView;
    private String postLink;
    private ProgressBar progressBar;
    private int postIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postLink = getIntent().getStringExtra("link");
        postIndex = getIntent().getIntExtra("position", 0);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);
        showPost();
    }

    private void showPost()
    {
        WebSettings webSett = webView.getSettings();
        webSett.setDefaultTextEncodingName("UTF-8");
        webSett.setBuiltInZoomControls(true);
        webSett.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                setTitle("Загрузка...");
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                setTitle(view.getTitle());
            }
        });

        if (NetworkUtil.getConnectivityStatus(this) == NetworkState.NOT_CONNECTED)
        {
            webView.loadUrl("file://" + getFilesDir().getAbsolutePath() + File.separator + postIndex + ".mht");
        }
        else {
            webView.loadUrl(postLink);
        }

    }

}
