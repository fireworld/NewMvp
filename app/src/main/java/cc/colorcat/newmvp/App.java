package cc.colorcat.newmvp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        LeakCanary.install(this);
    }
}
