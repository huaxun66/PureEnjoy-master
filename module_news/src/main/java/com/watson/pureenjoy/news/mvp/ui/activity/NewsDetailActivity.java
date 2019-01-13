package com.watson.pureenjoy.news.mvp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.watson.pureenjoy.news.R;
import com.watson.pureenjoy.news.R2;
import com.watson.pureenjoy.news.di.component.DaggerNewsDetailComponent;
import com.watson.pureenjoy.news.http.entity.NewsDetail;
import com.watson.pureenjoy.news.mvp.contract.NewsDetailContract;
import com.watson.pureenjoy.news.mvp.presenter.NewsDetailPresenter;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonres.view.ObservableWebView;
import me.jessyan.armscomponent.commonres.view.TopBar;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.StringUtil;

import static com.watson.pureenjoy.news.app.NewsConstants.POST_ID;

@Route(path = RouterHub.NEWS_DETAIL_ACTIVITY)
public class NewsDetailActivity extends NewsBaseActivity<NewsDetailPresenter> implements NewsDetailContract.View {
    @BindView(R2.id.webView)
    ObservableWebView mWebView;
    @BindView(R2.id.topBar)
    TopBar mTopBar;
    @BindView(R2.id.loadingProgressBar)
    ProgressBar mLoadingProgressBar;

    @Autowired(name = POST_ID)
    public String postId;
    @Autowired
    public String url;

    private int currentProgress;
    private ValueAnimator animator;
    private boolean isAnimStart;
    private int index = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewsDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_news_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
        if (StringUtil.isEmpty(url)) {
            Toasty.error(this, getString(R.string.news_not_available), Toast.LENGTH_SHORT, true).show();
        }
        initWebView();
        mPresenter.requestNewsDetail(postId);
    }

    private void initListener() {
        mTopBar.setLeftImageClickListener(v -> {
            if (mWebView.canGoBack()) {
                index--;
                if (index == 1) {
                    mTopBar.setLeftSecondImageVisible(false);
                }
                mWebView.goBack();
            } else {
                finish();
            }
        });
        mTopBar.setLeftSecondImageClickListener(v -> finish());
//        mWebView.setOnScrollChangedCallback((dx, dy) -> mTopBar.setScrollY(dy));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.equals("device://")) {
                    index++;
                    mTopBar.setLeftSecondImageVisible(true);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                if (null == mLoadingProgressBar) return;
                if (newProgress >= 100) {
                    if (!isAnimStart) {
                        // 防止调用多次动画
                        isAnimStart = true;
                        // 开启属性动画让进度条平滑消失
                        startDismissAnimation(currentProgress);
                    }
                } else {
                    // 开启属性动画让进度条平滑递增
                    startProgressAnimation(newProgress);
                }
            }
        });
    }

    private void initWebView() {
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setBackgroundColor(0); //背景透明
        initWebSettings();
        mWebView.loadUrl(url);
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

    /**
     * progressBar递增动画
     */
    private void startProgressAnimation(int newProgress) {
        if (null != animator) {
            animator.cancel();
        }
        animator = ValueAnimator.ofInt(currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();

        animator.addUpdateListener(valueAnimator -> {
            currentProgress = Integer.parseInt(valueAnimator.getAnimatedValue().toString());
            if (null != mLoadingProgressBar) {
                mLoadingProgressBar.setSecondaryProgress(currentProgress);
                mLoadingProgressBar.setProgress(currentProgress - 10);
            }
        });
    }


    /**
     * progressBar消失动画
     */
    private void startDismissAnimation(final int progress) {
        if (null != animator) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setDuration(300);  // 动画时长
        animator.setInterpolator(new AccelerateInterpolator()); //加速
        //添加动画进度监听器
        animator.addUpdateListener(valueAnimator -> {
            float value = Float.parseFloat(valueAnimator.getAnimatedValue().toString()); //1.0f ~ 0.0f
            float fraction = valueAnimator.getAnimatedFraction(); //0.0f ~ 1.0f
            int newProgress = (int) (progress + (100 - progress) * fraction);
            if (null != mLoadingProgressBar) {
                mLoadingProgressBar.setAlpha(value);
                mLoadingProgressBar.setSecondaryProgress(newProgress);
                mLoadingProgressBar.setProgress(newProgress - 10);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束
                if (null != mLoadingProgressBar) {
                    mLoadingProgressBar.setProgress(0);
                    mLoadingProgressBar.setVisibility(View.GONE);
                }
                isAnimStart = false;
            }
        });
        animator.start();
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            index--;
            if (index == 1) {
                mTopBar.setLeftSecondImageVisible(false);
            }
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }


    @Override
    public void showMessage(@NonNull String message) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void setNewsDetail(NewsDetail newsDetail) {

    }
}
