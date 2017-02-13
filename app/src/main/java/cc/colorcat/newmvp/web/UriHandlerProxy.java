package cc.colorcat.newmvp.web;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import cc.colorcat.newmvp.util.Op;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public class UriHandlerProxy implements IUriHandler {
    private List<IUriHandler> mHandlers = new LinkedList<>();

    public void addHandler(@NonNull IUriHandler handler) {
        Op.nonNull(handler, "handler == null");
        if (handler == this) {
            throw new IllegalArgumentException("handler == this");
        }
        mHandlers.add(handler);
    }

    @Override
    public boolean handle(Uri uri, IWebView view) {
        for (IUriHandler handler : mHandlers) {
            if (handler.handle(uri, view)) {
                return true;
            }
        }
        return false;
    }
}
