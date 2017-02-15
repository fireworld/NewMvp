package cc.colorcat.newmvp.model;

import java.util.List;

import cc.colorcat.newmvp.bean.Course;
import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.net.MCallback;

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
    interface IBase<Data> {

        /**
         * 发出网络请求
         *
         * @param callback 网络请求回调
         * @return tag, 用于取消网络请求
         */
        Object call(MCallback<Data> callback);

        /**
         * 取消网络请求
         */
        void cancel(Object tag);
    }

    /**
     * 登录，成功后返回 {@link User} 实例
     */
    interface ISignIn extends IBase<User> {

        void setUsername(String username);

        void setPassword(String password);
    }

    interface ICourse extends IBase<List<Course>> {

        void setType(String type);

        void setNumber(int number);
    }
}
