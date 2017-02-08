package cc.colorcat.newmvp.model;

import android.os.AsyncTask;
import android.os.SystemClock;

import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.net.Callback;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public class SignInModel implements ISignInModel {
    public static final String USER_NAME = "cxx";
    public static final String USER_PASSWORD = "123456";

    private String username;
    private String password;

    @Override
    public SignInModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public SignInModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public void signIn(final Callback<User> callback) {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                callback.onStart();
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                SystemClock.sleep(3000);
                return USER_NAME.equals(strings[0]) && USER_PASSWORD.equals(strings[1]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    callback.onSuccess(new User(username, password));
                } else {
                    callback.onFailure(-1, "username or password error");
                }
                callback.onFinish();
            }
        }.execute(username, password);
    }
}
