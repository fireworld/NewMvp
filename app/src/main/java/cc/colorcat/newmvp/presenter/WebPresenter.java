package cc.colorcat.newmvp.presenter;

import android.net.Uri;
import android.support.annotation.CallSuper;

import cc.colorcat.newmvp.contract.IWeb;
import cc.colorcat.newmvp.web.HttpHandler;
import cc.colorcat.newmvp.web.IUriHandler;
import cc.colorcat.newmvp.web.IWebView;
import cc.colorcat.newmvp.web.UriHandlerProxy;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public class WebPresenter extends BasePresenter<IWeb.View> implements IWeb.Presenter {
    private IWeb.View mView;
    private IUriHandler mProxy;

    @Override
    public void onCreate(IWeb.View view) {
        mView = view;
        UriHandlerProxy proxy = new UriHandlerProxy();
        proxy.addHandler(new HttpHandler());
        mProxy = proxy;
        mView.onCreateWebView();
        mView.loadUrl(mView.getUrl());
    }

    @Override
    public boolean handle(Uri uri, IWebView view) {
        return mProxy.handle(uri, view);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        mView.onDestroyWebView();
        mView = null;
        super.onDestroy();
    }
}
