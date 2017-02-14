package cc.colorcat.newmvp.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import cc.colorcat.newmvp.PermissionListener;
import cc.colorcat.newmvp.contract.IBase;
import cc.colorcat.newmvp.view.activity.BaseActivity;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public abstract class BaseFragment extends Fragment implements IBase.View {
    private static final int REQUEST_PERMISSION = 0x24123;

    private PermissionListener mPermissionListener;
    private boolean mActive = false;

    protected final void requestPermissions(@NonNull String[] permissions, PermissionListener listener) {
        Activity act = getActivity();
        if (act == null) return;
        mPermissionListener = listener;
        List<String> denied = new LinkedList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(act, permission) != PackageManager.PERMISSION_GRANTED) {
                denied.add(permission);
            }
        }
        if (!denied.isEmpty()) {
            ActivityCompat.requestPermissions(act, denied.toArray(new String[denied.size()]), REQUEST_PERMISSION);
        } else if (mPermissionListener != null) {
            mPermissionListener.onAllGranted();
            mPermissionListener = null;
        }
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionListener == null) return;
        int size = grantResults.length;
        if (size > 0 && requestCode == REQUEST_PERMISSION) {
            List<String> denied = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    denied.add(permissions[i]);
                }
            }
            if (denied.isEmpty()) {
                mPermissionListener.onAllGranted();
            } else {
                mPermissionListener.onDenied(denied.toArray(new String[denied.size()]));
            }
        }
        mPermissionListener = null;
    }

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
