package cc.colorcat.newmvp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cc.colorcat.newmvp.R;
import cc.colorcat.newmvp.bean.Course;
import cc.colorcat.newmvp.contract.ICourse;
import cc.colorcat.newmvp.model.ApiService;
import cc.colorcat.newmvp.presenter.CoursePresenter;
import cc.colorcat.newmvp.util.RVAdapter;
import cc.colorcat.newmvp.util.VHolder;

/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public class CourseListActivity extends BaseActivity implements ICourse.View {
    private ICourse.Presenter mPresenter = new CoursePresenter();
    private List<Course> mCourses = new ArrayList<>(30);
    private SwipeRefreshLayout mRootSrl;
    private RVAdapter<Course> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        mRootSrl = (SwipeRefreshLayout) findViewById(R.id.srl_root);
        mRootSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.toReloadCourses();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_courses);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(createAdapter());

        mPresenter.onCreate(this);
    }

    private RVAdapter<Course> createAdapter() {
        if (mAdapter == null) {
            mAdapter = new RVAdapter<Course>(mCourses, R.layout.adapter_course_list) {
                @Override
                public void bindView(VHolder holder, Course course) {
                    holder.setText(R.id.tv_name, course.getName())
                            .setText(R.id.tv_desc, course.getDescription());
                    ApiService.display((ImageView) holder.getView(R.id.iv_pic_big), course.getPicBig());
                }
            };
            mAdapter.setOnItemLongClickListener(new RVAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(@NonNull View v, int position) {
                    showTip(position);
                }
            });
        }
        return mAdapter;
    }

    private void showTip(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("tip")
                .setMessage("delete ?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.toDeleteCourse(position);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void refresh(List<Course> courses) {
        mCourses.clear();
        mCourses.addAll(courses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideRefresh() {
        mRootSrl.setRefreshing(false);
    }

    @Override
    public void deleteCourse(int position) {
        mCourses.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
