package cc.colorcat.newmvp.net;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import cc.colorcat.netbird.parser.Parser;
import cc.colorcat.netbird.response.NetworkData;
import cc.colorcat.netbird.response.Response;
import cc.colorcat.netbird.util.Utils;
import cc.colorcat.newmvp.util.L;
import cc.colorcat.newmvp.util.Op;

/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public class GsonParser<T> implements Parser<T> {
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .serializeNulls()
                .create();
    }

    private TypeToken token;

    public GsonParser(TypeToken token) {
        this.token = Op.nonNull(token, "token == null");
    }

    @NonNull
    @Override
    public NetworkData<? extends T> parse(@NonNull Response response) {
        String msg = response.msg();
        int code = response.code();
        try {
            String content = response.body().string();
            T data = GSON.fromJson(content, token.getType());
            return NetworkData.newSuccess(data);
        } catch (IOException e) {
            L.e(e);
            msg = Utils.formatMsg(msg, e);
        }
        return NetworkData.newFailure(code, msg);
    }
}
