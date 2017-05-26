package com.angcyo.sharebook.http.service;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/26 14:10
 * 修改人员：Robi
 * 修改时间：2017/05/26 14:10
 * 修改备注：
 * Version: 1.0.0
 */
public interface Home {
    @POST("home.php")
    Observable<ResponseBody> home(@QueryMap Map<String, String> map);
}
