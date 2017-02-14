package cc.colorcat.newmvp.view.activity;

import android.os.Bundle;
import android.view.View;

import cc.colorcat.newmvp.R;
import cc.colorcat.newmvp.view.dialog.DemoDialogFragment;


/**
 * Created by cxx on 16-6-24.
 * xx.ch@outlook.com
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        findViewById(R.id.btn_to_demo_activity).setOnClickListener(mClick);
        findViewById(R.id.btn_to_demo_fragment).setOnClickListener(mClick);
        findViewById(R.id.btn_show_demo_dialog_fragment).setOnClickListener(mClick);
        findViewById(R.id.btn_to_web).setOnClickListener(mClick);
    }

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_to_demo_activity:
                    navigateTo(DemoActivity.class);
                    break;
                case R.id.btn_to_demo_fragment:
                    navigateTo(DemoFragmentActivity.class);
                    break;
                case R.id.btn_show_demo_dialog_fragment:
                    new DemoDialogFragment().show(getFragmentManager(), "DemoDialogFragment");
                    break;
                case R.id.btn_to_web:
                    WebActivity.start(LaunchActivity.this, "http://cn.bing.com");
                    break;
                default:
                    break;
            }
        }
    };
}
