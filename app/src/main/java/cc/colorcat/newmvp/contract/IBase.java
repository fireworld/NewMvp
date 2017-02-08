package cc.colorcat.newmvp.contract;

import android.support.annotation.StringRes;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public interface IBase {

    interface View {

        /**
         * 如果当前 View 可以设置数据（即没有被销毁）就返回 true，否则返回 false。
         * 如 Activity.isFinishing() 返回 true 时表明即将被销毁，此时 isActive() 应返回 false.
         */
        boolean isActive();

        void toast(CharSequence text);

        void toast(@StringRes int resId);
    }

    interface Presenter<V extends View> {

        void onCreate(V v);

        void onDestroy();
    }
}
