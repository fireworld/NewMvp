package cc.colorcat.newmvp.model;

import cc.colorcat.netbird.request.Request;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public abstract class BaseImpl<Data> implements Api.IBase<Data> {

    @Override
    public void cancel(Object tag) {
        ApiService.cancel(tag);
    }

    protected Object sendRequest(Request<Data> req) {
        return ApiService.sendRequest(req);
    }
}
