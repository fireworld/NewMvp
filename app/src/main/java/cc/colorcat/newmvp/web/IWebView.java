package cc.colorcat.newmvp.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * Created by cxx on 2017/2/9.
 * xx.ch@outlook.com
 */
public interface IWebView {

    /**
     * 在此方法中创建新的 {@link WebView} 并添加到视图中
     */
    void onCreateWebView();

    /**
     * 在此方法中销毁原来的 {@link WebView} 并重新创建，然后添加到视图中
     */
    void onRecreateWebView();

    /**
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addPageLoadListener(PageLoadListener listener);

    /**
     * @return 移除成功返回 true，否则返回 false
     */
    boolean removePageLoadListener(PageLoadListener listener);

    /**
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addErrorListener(ErrorListener listener);

    /**
     * @return 移除成功返回 true，否则返回 false
     */
    boolean removeErrorListener(ErrorListener listener);

    /**
     * 在此方法中载入 url
     * note: 此方法必须在 {@link WebView} 创建后调用
     *
     * @param url http / https
     * @throws IllegalStateException 如果 {@link WebView} 没有创建，即为空的时候抛出此异常
     */
    void loadUrl(String url);

    /**
     * @param data     a String of data in the given encoding
     * @param mimeType the MIME type of the data, e.g. 'text/html'
     * @param encoding the encoding of the data
     */
    void loadData(String data, String mimeType, String encoding);

    /**
     * 刷新当前页面
     */
    void reload();

    /**
     * @param script         the JavaScript to execute.
     * @param resultCallback A callback to be invoked when the script execution
     *                       completes with the result of the execution (if any).
     *                       May be null if no notificaion of the result is required.
     */
    void evaluateJavascript(String script, ValueCallback<String> resultCallback);

    /**
     * @return {@link WebView.HitTestResult#getType()}
     */
    int getHitTestType();

    /**
     * 获取上下文环境变量
     */
    Context getContext();

    /**
     * 在此方法中销毁 {@link WebView}, 回收 {@link WebView} 的资源
     */
    void onDestroyWebView();


    interface PageLoadListener {

        void onPageStarted(String url, Bitmap favicon);

        void onPageFinished(String url);
    }


    interface ErrorListener {

        void onReceivedError(int errorCode, String description, String failingUrl);
    }


    class SimplePageLoadListener implements PageLoadListener {
        @Override
        public void onPageStarted(String url, Bitmap favicon) {

        }

        @Override
        public void onPageFinished(String url) {

        }
    }
}
