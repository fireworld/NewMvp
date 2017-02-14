package cc.colorcat.newmvp.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import cc.colorcat.newmvp.PermissionListener;
import cc.colorcat.newmvp.contract.IBase;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBase.View {
    private static final int REQUEST_PERMISSION = 0x24123;

    private PermissionListener mPermissionListener;

    protected final void requestPermissions(@NonNull String[] permissions, PermissionListener listener) {
        mPermissionListener = listener;
        List<String> denied = new LinkedList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                denied.add(permission);
            }
        }
        if (!denied.isEmpty()) {
            ActivityCompat.requestPermissions(this, denied.toArray(new String[denied.size()]), REQUEST_PERMISSION);
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
