package cc.colorcat.newmvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cc.colorcat.newmvp.R;
import cc.colorcat.newmvp.contract.IWeb;
import cc.colorcat.newmvp.presenter.WebPresenter;
import cc.colorcat.newmvp.web.WebViewDelegate;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public class WebFragment extends BaseFragment implements IWeb.View {
    private static final String KEY_URI = "KEY_URI";

    public static WebFragment newInstance(String url) {
        if (url == null || !url.startsWith("http")) {
            throw new IllegalArgumentException("Illegal url = " + url);
        }
        Bundle args = new Bundle();
        args.putString(KEY_URI, url);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private IWeb.Presenter mPresenter = new WebPresenter();
    private WebViewDelegate mDelegate;
    private String mUrl;
    private SwipeRefreshLayout mRootSrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && mUrl == null) {
            mUrl = savedInstanceState.getString(KEY_URI);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_web, container, false);
        mRootSrl = (SwipeRefreshLayout) root.findViewById(R.id.srl_root);
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
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onCreate(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUrl != null) {
            outState.putString(KEY_URI, mUrl);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public String getUrl() {
        if (mUrl == null) {
            Bundle args = getArguments();
            mUrl = args != null ? args.getString(KEY_URI) : null;
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
}
