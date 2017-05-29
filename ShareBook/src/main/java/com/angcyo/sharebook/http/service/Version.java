package com.angcyo.sharebook.http.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by angcyo on 2017-05-29.
 */

public interface Version {
    String url = "https://raw.githubusercontent.com/angcyo/ShareBook/master/version";

    @GET()
    Observable<ResponseBody> version(@Url String url);
}
