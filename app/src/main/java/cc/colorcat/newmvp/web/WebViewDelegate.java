package cc.colorcat.newmvp.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by cxx on 17-3-24.
 * xx.ch@outlook.com
 */
public class WebViewDelegate implements IWebView {
    private static final int RETRY_LIMIT = 3;

    private ViewGroup mWebContainer;
    private WebView mWebView;
    private IUriHandler mHandler;

    private List<PageLoadListener> mPageListeners = new LinkedList<>();
    private List<ErrorListener> mErrorListeners = new LinkedList<>();

    public WebViewDelegate(@NonNull ViewGroup webContainer, @NonNull IUriHandler handler) {
        mWebContainer = webContainer;
        mHandler = handler;
    }

    @Override
    public void onCreateWebView() {
        mWebView = new WebView(mWebContainer.getContext());
        configWebView(mWebView, mHandler);
        mWebContainer.addView(mWebView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void configWebView(WebView webView, IUriHandler handler) {
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.setWebViewClient(new LowerClient(this, handler));

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true); //支持js
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 通过 js 打开新窗口

        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        settings.setSupportMultipleWindows(true); //多窗口

        /* 缓存相关开始*/
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

//        settings.setAllowFileAccess(true); // 设置可以访问文件
//        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);

        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());
//        settings.setAppCacheMaxSize(10 * 1024 * 1024);

        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
//        settings.setDatabasePath(webView.getContext().getCacheDir().getAbsolutePath());
        /* 缓存相关结束*/

//        settings.setPluginState(WebSettings.PluginState.ON);

//        webView.requestFocusFromTouch();
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // LOLLIPOP 及之后默认禁止 https 和 http 混合，需手动开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public void onRecreateWebView() {
        onDestroyWebView();
        onCreateWebView();
    }

    @Override
    public boolean addPageLoadListener(PageLoadListener listener) {
        if (listener == null) throw new NullPointerException("listener == null");
        return !mPageListeners.contains(listener) && mPageListeners.add(listener);
    }

    @Override
    public boolean removePageLoadListener(PageLoadListener listener) {
        return listener != null && mPageListeners.remove(listener);
    }

    @Override
    public boolean addErrorListener(ErrorListener listener) {
        if (listener == null) throw new NullPointerException("listener == null");
        return !mErrorListeners.contains(listener) && mErrorListeners.add(listener);
    }

    @Override
    public boolean removeErrorListener(ErrorListener listener) {
        return listener != null && mErrorListeners.remove(listener);
    }

    @Override
    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        mWebView.loadData(data, mimeType, encoding);
    }

    @Override
    public void reload() {
        mWebView.reload();
    }

    @Override
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(script, resultCallback);
        } else {
            mWebView.loadUrl("javascript:" + script);
        }
    }

    @Override
    public int getHitTestType() {
        return mWebView.getHitTestResult().getType();
    }

    @Override
    public Context getContext() {
        return mWebContainer.getContext();
    }

    @Override
    public void onDestroyWebView() {
        mWebContainer.removeAllViews();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    public WebView getWebView() {
        return mWebView;
    }

    private void notifyPageStarted(String url, Bitmap favicon) {
        for (PageLoadListener listener : mPageListeners) {
            listener.onPageStarted(url, favicon);
        }
    }

    private void notifyPageFinished(String url) {
        for (PageLoadListener listener : mPageListeners) {
            listener.onPageFinished(url);
        }
    }

    private void notifyReceivedError(int errorCode, String description, String failingUrl) {
        for (ErrorListener listener : mErrorListeners) {
            listener.onReceivedError(errorCode, description, failingUrl);
        }
    }


    public static class BaseWebViewClient extends WebViewClient {
        WebViewDelegate mDelegate;
        IUriHandler mHandler;
        boolean mHasError = false;
        int mRetryCount = 0;

        BaseWebViewClient(WebViewDelegate delegate, IUriHandler handler) {
            mDelegate = delegate;
            mHandler = handler;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mDelegate.notifyPageStarted(url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mDelegate.notifyPageFinished(url);
            if (mHasError) {
                if (mRetryCount < RETRY_LIMIT) {
                    mDelegate.reload(); // 尝试重新加载
                    mRetryCount++; // 重新加载记数
                } else {
                    mRetryCount = 0; // 重新加载次数达到 RETRY_LIMIT 后，将 RetryCount 置 0，以记载其它 url 失败尝试次数
                }
                mHasError = false;  // 置为 false 以记载下一次加载是否存在错误
            } else {
                // 尝试重新加载成功后，将 {@link #mRetryCount} 置 0
                if (mRetryCount != 0) {
                    mRetryCount = 0;
                }
            }
        }
    }


    private static class LowerClient extends BaseWebViewClient {

        LowerClient(WebViewDelegate delegate, IUriHandler handler) {
            super(delegate, handler);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return mHandler.handle(Uri.parse(url), mDelegate) || super.shouldOverrideUrlLoading(view, url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mHasError = true; // 标示当前 url 加载失败
            mDelegate.notifyReceivedError(errorCode, description, failingUrl);
        }
    }


    /**
     * 用于系统版本大于或等于 Build.VERSION_CODES.M 的手机
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static class LaterClient extends BaseWebViewClient {

        LaterClient(WebViewDelegate delegate, IUriHandler handler) {
            super(delegate, handler);
        }

        /**
         * 在点击链接或后台轮播时不一定会进入此方法，需注意
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri uri = request.getUrl();
            return mHandler.handle(uri, mDelegate) || super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            int code = error.getErrorCode();
            if (code == WebViewClient.ERROR_UNSUPPORTED_SCHEME && mHandler.handle(request.getUrl(), mDelegate)) {
                return;
            }
            mHasError = true;
            super.onReceivedError(view, request, error);
        }
    }
}

