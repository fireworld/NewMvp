package cc.colorcat.newmvp.presenter;

import android.support.annotation.CallSuper;

import cc.colorcat.newmvp.contract.IBase;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public abstract class BasePresenter<V extends IBase.View> implements IBase.Presenter<V> {

    @CallSuper
    @Override
    public void onDestroy() {
    }
}
