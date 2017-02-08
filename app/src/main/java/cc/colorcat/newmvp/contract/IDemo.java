package cc.colorcat.newmvp.contract;

import android.support.annotation.NonNull;

import cc.colorcat.newmvp.bean.User;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public interface IDemo {

    interface View extends IBase.View {

        String getUsername();

        String getPassword();

        void setSubmitEnabled(boolean enabled);

        void setUsernameError(@NonNull String tip);

        void setPasswordError(@NonNull String tip);

        void onLoginSuccess(@NonNull User user);

        void onLoginFailure(String msg);
    }

    interface Presenter extends IBase.Presenter<View> {

        void toSignIn();
    }
}
