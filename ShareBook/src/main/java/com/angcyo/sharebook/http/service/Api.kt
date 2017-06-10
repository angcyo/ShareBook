package com.angcyo.sharebook.http.service

import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by angcyo on 2017-06-10.
 */
interface Api {
    @POST("activity.php") fun api(@QueryMap map: Map<String, String>): Observable<ResponseBody>
}
