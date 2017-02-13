package cc.colorcat.newmvp.contract;

import cc.colorcat.newmvp.web.IUriHandler;
import cc.colorcat.newmvp.web.IWebView;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public interface IWeb {

    interface View extends IBase.View, IWebView {

        String getUrl();
    }

    interface Presenter extends IBase.Presenter<View>, IUriHandler {

    }
}
