package cc.colorcat.newmvp.model;


import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.net.Callback;

/**
 * Created by cxx on 16/7/13.
 * xx.ch@outlook.com
 */
public interface ISignInModel {

    ISignInModel setUsername(String username);

    ISignInModel setPassword(String password);

    void signIn(Callback<User> callback);
}
