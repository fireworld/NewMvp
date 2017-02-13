package cc.colorcat.newmvp.web;

import android.net.Uri;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public interface IUriHandler {

    /**
     * 处理 {@link android.webkit.WebView} 请求的链接（包括自定义的协议等）
     *
     * @param uri  待处理的 Uri
     * @param view {@link IWebView}
     * @return 如果能够处理返回 true，否则返回 false
     */
    boolean handle(Uri uri, IWebView view);
}
