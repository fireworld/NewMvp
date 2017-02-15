package cc.colorcat.newmvp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public class JsonUtils {
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .serializeNulls()
                .create();
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String toJson(List<String> names, List<?> values) {
        if (names == null || names.isEmpty() || values == null || values.isEmpty()) {
            throw new IllegalArgumentException("names or values is null/empty");
        }
        int size = names.size();
        if (size != values.size()) {
            throw new IllegalArgumentException("names.size() != values.size()");
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(names.get(i), values.get(i));
        }
        return gson.toJson(map);
    }

    public static String simpleJson(String name, String value) {
        Op.nonNull(name, "name == null");
        Op.nonNull(value, "value == null");
        JsonObject obj = new JsonObject();
        obj.addProperty(name, value);
        return obj.toString();
    }

    public static String simpleJson(List<String> names, List<String> values) {
        if (names == null || names.isEmpty() || values == null || values.isEmpty()) {
            throw new IllegalArgumentException("names or values is null/empty");
        }
        int size = names.size();
        if (size != values.size()) {
            throw new IllegalArgumentException("names.size() != values.size()");
        }
        JsonObject obj = new JsonObject();
        for (int i = 0; i < size; i++) {
            obj.addProperty(names.get(i), values.get(i));
        }
        return obj.toString();
    }


    public static <T> T fromJson(String json, Class<T> c) {
        return gson.fromJson(json, c);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    private JsonUtils() {
        throw new AssertionError("no instance");
    }
}
