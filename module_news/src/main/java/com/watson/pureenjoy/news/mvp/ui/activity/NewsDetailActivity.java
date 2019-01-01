package com.watson.pureenjoy.news.mvp.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseSupportActivity;
import me.jessyan.armscomponent.commonres.view.ObservableWebView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.watson.pureenjoy.news.app.NewsConstants.POST_ID;

@Route(path = RouterHub.NEWS_DETAILACTIVITY)
public class NewsDetailActivity extends BaseSupportActivity {
    @BindView(R2.id.webView)
    ObservableWebView mWebView;
    @BindView(R2.id.topBar)
    TopBar mTopBar;
    @BindView(R2.id.progressBar)
    ProgressBar mProgressBar;

    @Autowired(name = POST_ID)
    private String postId;
    @Autowired
    private String url;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_news_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initWebView(url);
    }

    private void initWebView(String url) {
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setBackgroundColor(0); //背景透明
        initWebSettings();
        mWebView.setOnScrollChangedCallback((dx, dy) -> mTopBar.setScrollY(dy));
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //开启 DOM 存储功能
        webSettings.setDomStorageEnabled(true);
        //开启 数据库 存储功能
        webSettings.setDatabaseEnabled(true);
        //开启 应用缓存 功能
        webSettings.setAppCacheEnabled(true);
        //混合模式,https中允许访问http链接
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(webSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
    }


}
