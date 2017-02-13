package cc.colorcat.newmvp.web;

import android.net.Uri;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public class HttpHandler implements IUriHandler {
    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    @Override
    public boolean handle(Uri uri, IWebView view) {
        String scheme = uri.getScheme();
        if (HTTP.equals(scheme) || HTTPS.equals(scheme)) {
//            view.onRecreateWebView(); // 如果需要针对每一个 http / https 链接均新建 WebView 则加上此行
            view.loadUrl(uri.toString());
            return true;
        }
        return false;
    }
}
