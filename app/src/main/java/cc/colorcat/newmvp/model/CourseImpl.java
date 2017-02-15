package cc.colorcat.newmvp.model;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import cc.colorcat.netbird.request.Request;
import cc.colorcat.newmvp.bean.Course;
import cc.colorcat.newmvp.net.MCallback;

/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public class CourseImpl extends BaseImpl<List<Course>> implements Api.ICourse {
    private String type;
    private int number;

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public Object call(MCallback<List<Course>> callback) {
        TypeToken<Result<List<Course>>> token = new TypeToken<Result<List<Course>>>() {};
        Request<List<Course>> req = new Request.Builder<>(new ResultParser<>(token))
                .path("/teacher")
                .add("type", type)
                .add("num", number)
                .callback(callback)
                .build();
        return sendRequest(req);
    }
}
