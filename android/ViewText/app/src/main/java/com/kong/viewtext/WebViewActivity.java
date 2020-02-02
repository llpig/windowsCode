package com.kong.viewtext;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private WebView m_WebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        m_WebView = findViewById(R.id.webView);
        //加载本地Html文件
        //m_WebView.loadUrl("file:///android_asset/test1.html");
        //加载网络url
        //如果网页需要js的支持，不开启会自动打开默认浏览器，
        m_WebView.setWebViewClient(new myWebViewClient());
        m_WebView.getSettings().setJavaScriptEnabled(true);
        m_WebView.loadUrl("https://m.baidu.com");

        m_WebView.setWebChromeClient(new myWebChromeClient());
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Toast.makeText(WebViewActivity.this,"页面开始加载",Toast.LENGTH_SHORT).show();
            view.loadUrl("javascript:alert('页面信息')");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Toast.makeText(WebViewActivity.this,"页面完成加载",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK&&m_WebView.canGoBack())
        {
            m_WebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class myWebChromeClient extends WebChromeClient
    {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }
}