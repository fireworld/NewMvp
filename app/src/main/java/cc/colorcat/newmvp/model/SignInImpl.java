package cc.colorcat.newmvp.model;

import android.os.AsyncTask;
import android.os.SystemClock;

import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.net.Callback;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public class SignInImpl extends BaseImpl<User> implements Api.ISignIn {
    private static final String TEST_NAME = "cxx";
    private static final String TEST_PASSWORD = "123456";

    private String username;
    private String password;

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object call(final Callback<User> callback) {
        super.tag = System.currentTimeMillis();
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                callback.onStart();
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                SystemClock.sleep(3000);
                return TEST_NAME.equals(strings[0]) && TEST_PASSWORD.equals(strings[1]);
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
        return super.tag;
    }
}
