package com.watson.pureenjoy.news.mvp.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.base.BaseSupportFragment;
import me.jessyan.armscomponent.commonres.view.ObservableWebView;
import me.jessyan.armscomponent.commonres.view.TopBar;

import static com.watson.pureenjoy.news.app.NewsConstants.POST_ID;
import static com.watson.pureenjoy.news.app.NewsConstants.URL;

public class NewsDetailFragment extends BaseSupportFragment {
    @BindView(R2.id.webView)
    ObservableWebView mWebView;
    @BindView(R2.id.topBar)
    TopBar mTopBar;
    @BindView(R2.id.progressBar)
    ProgressBar mProgressBar;

    private String postId;
    private String url;

    public static NewsDetailFragment getIns(String postId, String url) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POST_ID, postId);
        bundle.putString(URL, url);
        newsDetailFragment.setArguments(bundle);
        return newsDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            postId = bundle.getString(POST_ID);
            url = bundle.getString(URL);
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.news_activity_news_detail, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initWebView(url);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initWebView(String url) {
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setBackgroundColor(0); //背景透明
        initWebSettings();
        mWebView.setOnScrollChangedCallback((dx, dy) -> {
            mTopBar.setScrollY(dy);
        });
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
