package cc.colorcat.newmvp.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import cc.colorcat.newmvp.contract.IBase;
import cc.colorcat.newmvp.view.activity.BaseActivity;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public abstract class BaseFragment extends Fragment implements IBase.View {
    private boolean mActive = false;

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActive = true;
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        mActive = false;
        super.onDestroyView();
    }

    @Override
    public final boolean isActive() {
        return mActive;
    }

    @Override
    public final void toast(CharSequence text) {
        Activity act = getActivity();
        if (act != null) {
            Toast.makeText(act.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public final void toast(@StringRes int resId) {
        Activity act = getActivity();
        if (act != null) {
            Toast.makeText(act.getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
        }
    }

    protected final void navigateTo(@NonNull Class<? extends BaseActivity> cls) {
        Activity act = getActivity();
        if (act != null) {
            startActivity(new Intent(act, cls));
        }
    }
}
