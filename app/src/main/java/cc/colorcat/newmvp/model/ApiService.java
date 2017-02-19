package cc.colorcat.newmvp.model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.List;

import cc.colorcat.netbird.Headers;
import cc.colorcat.netbird.NetBird;
import cc.colorcat.netbird.Processor;
import cc.colorcat.netbird.parser.BitmapParser;
import cc.colorcat.netbird.request.Method;
import cc.colorcat.netbird.request.Request;
import cc.colorcat.netbird.response.Response;
import cc.colorcat.netbird.util.Utils;
import cc.colorcat.newmvp.App;
import cc.colorcat.newmvp.BuildConfig;
import cc.colorcat.newmvp.net.SimpleCallback;
import cc.colorcat.newmvp.util.L;


/**
 * Created by cxx on 2017/2/15.
 * xx.ch@outlook.com
 */
public final class ApiService {
    private static final String TAG = ApiService.class.getSimpleName();

    private static final int TIME_OUT_CONNECT = 10000;
    private static final int TIME_OUT_READ = 10000;

    private static final String BASE_URL = "http://www.imooc.com/api";

    private static NetBird netBird;

    static {
        NetBird.Builder builder = new NetBird.Builder(BASE_URL)
                .enableCache(App.i(), 10L * 1024 * 1024)
                .connectTimeOut(TIME_OUT_CONNECT)
                .readTimeOut(TIME_OUT_READ);
        if (BuildConfig.DEBUG) {
            Processor<Request<?>> reqLog = new RequestLogProcessor();
            Processor<Response> repLog = new ResponseLogProcessor();
            builder.addRequestProcessor(reqLog)
                    .addResponseProcessor(repLog);
        }
        netBird = builder.build();
    }

    /**
     * 发送一个网络请求
     *
     * @param req {@link Request}
     * @return tag
     */
    static Object sendRequest(@NonNull Request<?> req) {
        return netBird.dispatch(req);
    }

    /**
     * 取消一个网络请求
     *
     * @param tag 发送网络请求时返回的 tag
     */
    static void cancel(Object tag) {
        if (tag != null) {
            netBird.cancelWait(tag);
        }
    }

    public static void display(final ImageView view, String url) {
        Request<Bitmap> request = new Request.Builder<>(BitmapParser.getParser())
                .url(url)
                .method(Method.GET)
                .callback(new SimpleCallback<Bitmap>() {
                    @Override
                    public void onSuccess(@NonNull Bitmap bitmap) {
                        view.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(int i, @NonNull String s) {
                        L.e("display", "code = " + i + " msg = " + s);
                    }
                }).build();
        sendRequest(request);
    }

    private static class RequestLogProcessor implements Processor<Request<?>> {
        @NonNull
        @Override
        public Request<?> process(@NonNull Request<?> req) {
            Method m = req.method();
            L.dd(TAG, "---------------------------------------- " + m.name() + " -----------------------------------------");
            String url = url(req);
            if (m == Method.GET) {
                String params = req.encodedParams();
                if (!Utils.isEmpty(params)) {
                    url = url + '?' + params;
                }
                L.dd(TAG, "req url --> " + url);
            } else {
                L.dd(TAG, "req url --> " + url);
                logPairs(req.paramNames(), req.paramValues(), "parameter");
                logPacks(req);
            }
            logPairs(req.headerNames(), req.headerValues(), "header");
            L.dd(TAG, "--------------------------------------------------------------------------------------");
            return req;
        }

        private static void logPairs(List<String> names, List<String> values, String mark) {
            for (int i = 0, size = names.size(); i < size; i++) {
                L.dd(TAG, "req " + mark + " -- > " + names.get(i) + " = " + values.get(i));
            }
        }

        private static void logPacks(Request<?> req) {
            List<Request.Pack> packs = req.packs();
            for (int i = 0, size = packs.size(); i < size; i++) {
                Request.Pack pack = packs.get(i);
                L.dd(TAG, "req pack --> " + pack.name + "--" + pack.contentType + "--" + pack.file.getAbsolutePath());
            }
        }

        private static String url(Request<?> req) {
            String url = Utils.emptyElse(req.url(), BASE_URL);
            String path = req.path();
            if (!Utils.isEmpty(path)) {
                url += path;
            }
            return url;
        }
    }

    private static class ResponseLogProcessor implements Processor<Response> {
        @NonNull
        @Override
        public Response process(@NonNull Response response) {
            L.ii(TAG, "-------------------------------------- response --------------------------------------");
            L.ii(TAG, "response --> code: " + response.code() + "  msg: " + response.msg());
            Headers headers = response.headers();
            for (int i = 0, size = headers.size(); i < size; i++) {
                String name = headers.name(i);
                String value = headers.value(i);
                L.ii(TAG, "response header --> " + name + " = " + value);
            }
            L.ii(TAG, "--------------------------------------------------------------------------------------");
            return response;
        }
    }

    private ApiService() {
        throw new AssertionError("no instance");
    }
}
