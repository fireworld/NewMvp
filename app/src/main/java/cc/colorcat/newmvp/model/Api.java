package cc.colorcat.newmvp.model;

import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.net.Callback;

/**
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public interface Api {

    interface Base<Data> {

        Object call(Callback<Data> callback);

        void cancel();
    }

    interface ISignIn extends Base<User> {

        void setUsername(String username);

        void setPassword(String password);
    }
}
