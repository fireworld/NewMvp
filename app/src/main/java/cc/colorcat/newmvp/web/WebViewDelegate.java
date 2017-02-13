package cc.colorcat.newmvp.web;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cc.colorcat.newmvp.util.Op;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public class WebViewDelegate implements IWebView {
    private static final int RETRY_LIMIT = 3;

    private ViewGroup mGroup;
    private WebView mWebView;
    private IUriHandler mHandler;
    private PageLoadListener mListener;

    public static WebViewDelegate create(@NonNull ViewGroup view, @NonNull IUriHandler handler) {
        return new WebViewDelegate(view, handler);
    }

    private WebViewDelegate(@NonNull ViewGroup group, @NonNull IUriHandler handler) {
        mGroup = Op.nonNull(group, "group == null");
        mHandler = Op.nonNull(handler, "handler == null");
    }

    public void setPageLoadListener(PageLoadListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreateWebView() {
        mWebView = new WebView(mGroup.getContext());
        configWebView(mWebView, mHandler);
        mGroup.addView(mWebView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void configWebView(WebView view, IUriHandler handler) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setWebViewClient(new LowerClient(this, handler));
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportMultipleWindows(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    public void onRecreateWebView() {
        onDestroyWebView();
        onCreateWebView();
    }

    @Override
    public void loadUrl(String url) {
        if (mWebView == null) {
            throw new IllegalStateException("You must call onCreateWebView() before");
        }
        mWebView.loadUrl(url);
    }

    @Override
    public void refresh() {
        mWebView.reload();
    }

    @Override
    public void onDestroyWebView() {
        mGroup.removeAllViews();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    protected void notifyPageStarted(WebView view, String url) {
        if (mListener != null) {
            mListener.onPageStarted(view, url);
        }
    }

    protected void notifyPageFinished(WebView view, String url) {
        if (mListener != null) {
            mListener.onPageFinished(view, url);
        }
    }

    public interface PageLoadListener {

        void onPageStarted(WebView view, String url);

        void onPageFinished(WebView view, String url);
    }

    public static class SimplePageLoadListener implements PageLoadListener {
        @Override
        public void onPageStarted(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }
    }


    private static class BaseWebViewClient extends WebViewClient {
        IUriHandler mHandler;
        WebViewDelegate mDelegate;
        boolean mHasError = false;
        int mRetryCount = 0;

        BaseWebViewClient(WebViewDelegate delegate, IUriHandler handler) {
            mDelegate = delegate;
            mHandler = handler;
        }

        @Override
        public final void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mDelegate.notifyPageStarted(view, url);
        }

        @Override
        public final void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mDelegate.notifyPageFinished(view, url);
            if (mHasError) {
                if (mRetryCount < RETRY_LIMIT) {
                    mDelegate.refresh(); // 尝试重新加载
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
        }
    }


//    /**
//     * 用于系统版本大于或等于 Build.VERSION_CODES.M 的手机
//     */
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private static class LaterClient extends BaseWebViewClient {
//
//        LaterClient(WebViewDelegate delegate, IUriHandler handler) {
//            super(delegate, handler);
//        }
//
//        /**
//         * 此方法调用时机未知，在点击链接或后台轮播时并不一定会进入此方法
//         */
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            Uri uri = request.getUrl();
//            MiLog.i(TAG + "Later", "Override: " + uri.toString());
//            return mHandler.handle(uri, mDelegate) || super.shouldOverrideUrlLoading(view, request);
//        }
//
//        @Override
//        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//            int code = error.getErrorCode();
//            if (code == WebViewClient.ERROR_UNSUPPORTED_SCHEME
//                    && mHandler.handle(request.getUrl(), mDelegate)) {
//                return;
//            }
//            mHasError = true;
//            MiLog.i(TAG + "Later", "ErrorCode: " + code + "\nDescription: " + error.getDescription() + "\nFailingUrl: " + request.getUrl().toString());
//            super.onReceivedError(view, request, error);
//        }
//    }
}
