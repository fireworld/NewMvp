package cc.colorcat.newmvp.view.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

import cc.colorcat.newmvp.contract.IBase;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public class BaseDialogFragment extends DialogFragment implements IBase.View {
    private boolean mActive = false;

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void toast(CharSequence text) {
        Activity act = getActivity();
        if (act != null) {
            Toast.makeText(act.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toast(@StringRes int resId) {
        Activity act = getActivity();
        if (act != null) {
            Toast.makeText(act.getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
        }
    }
}
