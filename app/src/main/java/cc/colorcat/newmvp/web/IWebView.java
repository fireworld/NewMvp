package cc.colorcat.newmvp.web;

import android.content.Context;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public interface IWebView {

    /**
     * 在此方法中创建新的 {@link android.webkit.WebView} 并添加到视图中
     *
     * @see WebViewDelegate#onCreateWebView()
     */
    void onCreateWebView();

    /**
     * 在此方法中销毁原来的 {@link android.webkit.WebView} 并重新创建，然后添加到视图中
     *
     * @see WebViewDelegate#onRecreateWebView()
     */
    void onRecreateWebView();

    /**
     * 在此方法中载入 url
     * note: 此方法必须在 {@link android.webkit.WebView} 创建后调用
     *
     * @param url http / https
     * @throws IllegalStateException 如果 {@link android.webkit.WebView} 没有创建，即为空的时候抛出此异常
     */
    void loadUrl(String url);

    /**
     * 刷新当前页面
     */
    void refresh();

    /**
     * 在此方法中销毁 {@link android.webkit.WebView}, 回收 {@link android.webkit.WebView} 的资源
     */
    void onDestroyWebView();

    /**
     * @return 上下文环境，如可能需要在新的 {@link android.app.Activity} 中打开地址，此时需要 Context
     */
    Context getContext();
}
