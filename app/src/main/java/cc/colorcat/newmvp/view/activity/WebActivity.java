package cc.colorcat.newmvp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;

import cc.colorcat.newmvp.R;
import cc.colorcat.newmvp.contract.IWeb;
import cc.colorcat.newmvp.presenter.WebPresenter;
import cc.colorcat.newmvp.util.Op;
import cc.colorcat.newmvp.web.WebViewDelegate;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public class WebActivity extends BaseActivity implements IWeb.View {
    private static final String KEY_URL = "HttpUrl";

    public static void start(@NonNull Activity act, String url) {
        Op.nonNull(act, "act == null");
        if (url == null || !url.startsWith("http")) {
            throw new IllegalArgumentException("Illegal url = " + url);
        }
        Intent i = new Intent(act, WebActivity.class);
        i.putExtra(KEY_URL, url);
        act.startActivity(i);
    }

    private IWeb.Presenter mPresenter = new WebPresenter();
    private WebViewDelegate mDelegate;
    private String mUrl;
    private SwipeRefreshLayout mRootSrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_web);

        if (savedInstanceState != null && mUrl == null) {
            mUrl = savedInstanceState.getString(KEY_URL);
        }

        mRootSrl = (SwipeRefreshLayout) findViewById(R.id.srl_root);
        mDelegate = WebViewDelegate.create(mRootSrl, mPresenter);
        mRootSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mDelegate.setPageLoadListener(new WebViewDelegate.SimplePageLoadListener() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mRootSrl.setRefreshing(false);
            }
        });
        mPresenter.onCreate(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUrl != null) {
            outState.putString(KEY_URL, mUrl);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public String getUrl() {
        if (mUrl == null) {
            mUrl = getIntent().getStringExtra(KEY_URL);
        }
        return mUrl;
    }

    @Override
    public void onCreateWebView() {
        mDelegate.onCreateWebView();
    }

    @Override
    public void onRecreateWebView() {
        mDelegate.onRecreateWebView();
    }

    @Override
    public void loadUrl(String url) {
        mDelegate.loadUrl(url);
    }

    @Override
    public void refresh() {
        mDelegate.refresh();
    }

    @Override
    public void onDestroyWebView() {
        mDelegate.onDestroyWebView();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
