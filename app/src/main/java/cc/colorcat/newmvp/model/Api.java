package cc.colorcat.newmvp.model;

import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.net.Callback;

/**
 * 网络请求接口
 * <p>
 * Created by cxx on 2017/2/13.
 * xx.ch@outlook.com
 */
public interface Api {

    /**
     * 基础网络请求接口
     *
     * @param <Data> 返回的最终数据
     */
    interface Base<Data> {

        /**
         * 发出网络请求
         *
         * @param callback 网络请求回调
         * @return tag, 用于取消网络请求
         */
        Object call(Callback<Data> callback);

        /**
         * 取消网络请求
         */
        void cancel();
    }

    /**
     * 登录，成功后返回 {@link User} 实例
     */
    interface ISignIn extends Base<User> {

        void setUsername(String username);

        void setPassword(String password);
    }
}
