package cc.colorcat.newmvp.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import cc.colorcat.newmvp.bean.Course;
import cc.colorcat.newmvp.contract.ICourse;
import cc.colorcat.newmvp.model.Api;
import cc.colorcat.newmvp.model.CourseImpl;
import cc.colorcat.newmvp.net.WeakCallback;
import cc.colorcat.newmvp.util.L;

/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public class CoursePresenter extends BasePresenter<ICourse.View> implements ICourse.Presenter {
    private static final String TAG = CoursePresenter.class.getSimpleName();
    private Api.ICourse mModel = new CourseImpl();
    private ICourse.View mView;

    @Override
    public void onCreate(ICourse.View view) {
        mView = view;
        toReloadCourses();
    }

    @Override
    public void toReloadCourses() {
        mModel.setType("4");
        mModel.setNumber(30);
        mModel.call(new WeakCallback<ICourse.View, List<Course>>(mView) {
            @Override
            public void onSuccess(@NonNull ICourse.View view, @NonNull List<Course> courses) {
                view.refresh(courses);
            }

            @Override
            public void onFailure(@NonNull ICourse.View view, int code, @NonNull String msg) {
                L.e(TAG, "code = " + code + ", msg = " + msg);
            }

            @Override
            public void onFinish(@NonNull ICourse.View view) {
                view.hideRefresh();
            }
        });
    }

    @Override
    public void toDeleteCourse(int position) {
        mView.deleteCourse(position);
    }

    @Override
    public void onDestroy() {
        mView = null;
        super.onDestroy();
    }
}
