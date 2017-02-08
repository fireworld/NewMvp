package cc.colorcat.newmvp.view.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import cc.colorcat.newmvp.R;
import cc.colorcat.newmvp.bean.User;
import cc.colorcat.newmvp.contract.IDemo;
import cc.colorcat.newmvp.presenter.DemoPresenter;

/**
 * Created by cxx on 2017/2/8.
 * xx.ch@outlook.com
 */
public class DemoDialogFragment extends BaseDialogFragment implements IDemo.View {
    public static final String TAG = "Demo";

    private IDemo.Presenter mPresenter = new DemoPresenter();

    private TableLayout mRootTl;
    private TextView mLoginResultTv;
    private EditText mUsernameEt;
    private EditText mPasswordEt;
    private Button mSubmitBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "DemoDialogFragment.onCreateView()");
        View view = inflater.inflate(R.layout.view_demo, container);
        mRootTl = (TableLayout) view.findViewById(R.id.tl_root);
        mLoginResultTv = (TextView) view.findViewById(R.id.tv_login_result);
        mUsernameEt = (EditText) view.findViewById(R.id.et_username);
        mPasswordEt = (EditText) view.findViewById(R.id.et_password);
        mSubmitBtn = (Button) view.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.toSignIn();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "DemoDialogFragment.onViewCreated()");
        mPresenter.onCreate(this);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "DemoDialogFragment.onDestroy()");
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public String getUsername() {
        return mUsernameEt.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEt.getText().toString();
    }

    @Override
    public void setSubmitEnabled(boolean enabled) {
        mSubmitBtn.setEnabled(enabled);
    }

    @Override
    public void setUsernameError(@NonNull String tip) {
        mUsernameEt.setError(tip);
    }

    @Override
    public void setPasswordError(@NonNull String tip) {
        mPasswordEt.setError(tip);
    }

    @Override
    public void onLoginSuccess(@NonNull User user) {
        setResult(Color.parseColor("#ffff00"), user.getUsername() + " login success!");
    }

    @Override
    public void onLoginFailure(String msg) {
        setResult(Color.parseColor("#0000ff"), msg);
    }

    private void setResult(int color, String tip) {
        mRootTl.setBackgroundColor(color);
        mLoginResultTv.setText(tip);
    }
}
