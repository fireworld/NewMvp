package cc.colorcat.newmvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.contract.IDemo;
import cc.colorcat.newmvp.model.Api;
import cc.colorcat.newmvp.model.SignInImpl;
import cc.colorcat.newmvp.net.WeakCallback;
import cc.colorcat.newmvp.util.Op;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public class DemoPresenter extends BasePresenter<IDemo.View> implements IDemo.Presenter {
    private static final String TAG = "Demo";

    private Api.ISignIn mModel = new SignInImpl();
    private String mUsername;
    private String mPassword;
    private IDemo.View mView;

    @Override
    public void onCreate(IDemo.View view) {
        Log.v(TAG, "DemoPresenter.onCreate() " + view.toString());
        mView = view;
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "DemoPresenter.onDestroy()");
        mView = null;
        super.onDestroy();
    }

    @Override
    public void toSignIn() {
        if (!checkToLogin()) return;
        mModel.setUsername(mUsername);
        mModel.setPassword(mPassword);
        mModel.call(new WeakCallback<IDemo.View, User>(mView) {
            @Override
            public void onStart(@NonNull IDemo.View view) {
                Log.i(TAG, "WeakCallback.onStart() -- " + view.toString());
                view.toast("login...");
                view.setSubmitEnabled(false);
            }

            @Override
            public void onSuccess(@NonNull IDemo.View view, @NonNull User user) {
                Log.i(TAG, "WeakCallback.onSuccess() -- " + view.toString() + " -- " + user.toString());
                view.onLoginSuccess(user);
            }

            @Override
            public void onFailure(@NonNull IDemo.View view, int code, @NonNull String msg) {
                Log.i(TAG, "WeakCallback.onFailure() -- " + view.toString() + " -- " + code + " : " + msg);
                view.onLoginFailure(msg);
            }

            @Override
            public void onFinish(@NonNull IDemo.View view) {
                Log.i(TAG, "WeakCallback.onFinish() -- " + view.toString());
                view.setSubmitEnabled(true);
            }
        });
    }

    private boolean checkToLogin() {
        boolean result = true;
        String username = mView.getUsername();
        if (Op.isEmpty(username)) {
            mView.setUsernameError("empty");
            result = false;
        }
        String password = mView.getPassword();
        if (Op.isEmpty(password) || password.length() < 6) {
            mView.setPasswordError("less than 6");
            result = false;
        }
        if (result) {
            mUsername = username;
            mPassword = password;
        }
        return result;
    }
}
