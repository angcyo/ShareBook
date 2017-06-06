package com.angcyo.sharebook.http;

import android.text.TextUtils;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.github.type.TypeBuilder;
import com.angcyo.uiview.net.RException;
import com.angcyo.uiview.utils.Json;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.angcyo.uiview.net.Rx.RETRY_COUNT;

/**
 * Created by angcyo on 2017-05-16.
 */

public class RxBook {

    public static int OK_CODE = 200;

    public static <T> Observable.Transformer<ResponseBody, T> transformer(final Class<T> type) {
        return new Observable.Transformer<ResponseBody, T>() {

            @Override
            public Observable<T> call(Observable<ResponseBody> responseObservable) {
                return responseObservable.unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<ResponseBody, T>() {
                            @Override
                            public T call(ResponseBody stringResponse) {
                                T bean;
                                String body;
                                try {
                                    body = stringResponse.string();

                                    //"接口返回数据-->\n" +
                                    L.json(body);

                                    JSONObject jsonObject = new JSONObject(body);
                                    int code = jsonObject.getInt("code");
                                    String msg = jsonObject.getString("msg");

                                    if (code == OK_CODE) {
                                        //请求成功
                                        String data = jsonObject.getString("data");
                                        if (TextUtils.isEmpty(data) || "null".equalsIgnoreCase(data)) {
                                            data = msg;
                                        }
                                        if (!TextUtils.isEmpty(data)) {
                                            if (type == String.class) {
                                                bean = (T) data;
                                                return bean;
                                            }
                                            bean = Json.from(data, type);
                                            return bean;
                                        }
                                    } else {
                                        //请求成功, 但是有错误

                                        throw new RException(code, msg, "no more");
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                    throw new RException(-1000, "服务器数据异常.", e.getMessage());
                                }
                                //throw new NullPointerException("无数据.");
                                return null;
                            }
                        })
                        .retry(RETRY_COUNT)
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<ResponseBody, List<T>> transformerList(final Class<T> type) {
        return new Observable.Transformer<ResponseBody, List<T>>() {

            @Override
            public Observable<List<T>> call(Observable<ResponseBody> responseObservable) {
                return responseObservable.unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<ResponseBody, List<T>>() {
                            @Override
                            public List<T> call(ResponseBody stringResponse) {
                                List<T> bean = new ArrayList<>();
                                String body;
                                try {
                                    body = stringResponse.string();

                                    //"接口返回数据-->\n" +
                                    L.json(body);

                                    JSONObject jsonObject = new JSONObject(body);
                                    int code = jsonObject.getInt("code");
                                    String msg = jsonObject.getString("msg");

                                    if (code == OK_CODE) {
                                        //请求成功
                                        String data = jsonObject.getString("data");
                                        if (!TextUtils.isEmpty(data)) {
                                            bean = Json.from(data, TypeBuilder.newInstance(List.class).addTypeParam(type).build());
                                            return bean;
                                        }
                                    } else {
                                        //请求成功, 但是有错误
                                        throw new RException(code,
                                                msg,
                                                "no more");
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                                return bean;
                            }
                        })
                        .retry(RETRY_COUNT)
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
