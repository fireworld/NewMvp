package cc.colorcat.newmvp.model;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public abstract class BaseImpl<Data> implements Api.Base<Data> {
    protected Object tag;

    @Override
    public void cancel() {

    }
}
