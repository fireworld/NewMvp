package cc.colorcat.newmvp.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import cc.colorcat.netbird.parser.Parser;
import cc.colorcat.netbird.response.NetworkData;
import cc.colorcat.netbird.response.Response;
import cc.colorcat.netbird.util.Utils;
import cc.colorcat.newmvp.util.JsonUtils;
import cc.colorcat.newmvp.util.L;
import cc.colorcat.newmvp.util.Op;

/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public class ResultParser<T> implements Parser<T> {

    private TypeToken<Result<T>> token;

    public ResultParser(TypeToken<Result<T>> token) {
        this.token = Op.nonNull(token, "token == null");
    }

    @NonNull
    @Override
    public NetworkData<? extends T> parse(@NonNull Response data) {
        String msg = data.msg();
        int code = data.code();
        try {
            String content = data.body().string();
            if (!TextUtils.isEmpty(content)) {
                Result<T> r = JsonUtils.fromJson(content, token.getType());
                msg = r.getMsg();
                code = r.getStatus();
                T t = r.getData();
                if (r.getStatus() == 1 && t != null) {
                    return NetworkData.newSuccess(t);
                }
            }
        } catch (IOException | JsonParseException e) {
            L.e(e);
            msg = Utils.formatMsg(msg, e);
        }
        return NetworkData.newFailure(code, msg);
    }
}
