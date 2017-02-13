package cc.colorcat.newmvp.web;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public interface IWebView {

    void onCreateWebView();

    void onRecreateWebView();

    void loadUrl(String url);

    void refresh();

    void onDestroyWebView();
}
