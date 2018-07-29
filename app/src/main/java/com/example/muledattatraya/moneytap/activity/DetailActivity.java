package com.example.muledattatraya.moneytap.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.muledattatraya.moneytap.R;
import com.example.muledattatraya.moneytap.utils.Globals;

import static com.example.muledattatraya.moneytap.utils.Globals.showProgressBar;

public class DetailActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressDialog progress;
    private Button mbtnNoIntenet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
        init();
        if (Globals.isConnectedToInternet(DetailActivity.this)) {
            mbtnNoIntenet.setVisibility(View.GONE);
            loadWebview();
        } else {
            mbtnNoIntenet.setVisibility(View.VISIBLE);
        }
    }

    public void init() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mbtnNoIntenet = (Button) findViewById(R.id.btn_no_intenet);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadWebview() {
        String url = getString(R.string.str_load_page_url);
        Intent intent = getIntent();
        String title = intent.getStringExtra(Globals.KEY_INTENT_TITLE);
        url = url + title;
        progress = showProgressBar(this, true, progress, "");

        mWebView.setInitialScale(1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showProgressBar(DetailActivity.this, false, progress, "");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                showProgressBar(DetailActivity.this, false, progress, "");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (handler != null) {
                    handler.proceed();
                } else {
                    super.onReceivedSslError(view, null, error);
                }
            }
        });
    }
}
