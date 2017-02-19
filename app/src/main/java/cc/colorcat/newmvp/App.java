package cc.colorcat.newmvp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cc.colorcat.newmvp.util.L;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public class App extends Application {
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        LeakCanary.install(this);
        mInstance = this;
        L.init(this);
    }

    public static App i() {
        return mInstance;
    }
}
