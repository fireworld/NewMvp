package cc.colorcat.newmvp.contract;

import java.util.List;

import cc.colorcat.newmvp.bean.Course;

/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public interface ICourse {

    interface View extends IBase.View {

        void refresh(List<Course> courses);

        void hideRefresh();

        void deleteCourse(int position);
    }

    interface Presenter extends IBase.Presenter<View> {

        void toReloadCourses();

        void toDeleteCourse(int position);
    }
}
