package cc.colorcat.newmvp.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cc.colorcat.newmvp.contract.IBase;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBase.View {

    @Override
    public final boolean isActive() {
        return !isFinishing();
    }

    @Override
    public final void toast(CharSequence text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public final void toast(@StringRes int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    protected final void navigateTo(@NonNull Class<? extends BaseActivity> cls) {
        navigateTo(cls, false);
    }

    protected final void navigateTo(@NonNull Class<? extends BaseActivity> cls, boolean finish) {
        startActivity(new Intent(this, cls));
        if (finish) {
            finish();
        }
    }
}
