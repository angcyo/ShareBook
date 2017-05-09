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
 * 创建时间：2017/05/09 18:23
 * 修改人员：Robi
 * 修改时间：2017/05/09 18:23
 * 修改备注：
 * Version: 1.0.0
 */
public interface User {
    /**
     * 用户登录接口
     * <p>
     * 参数名	必选	类型	说明
     * action	是	string	10001
     * uid	是	string	用户名
     * pwd	是	string	密码
     */
    @POST("user/login.php")
    Observable<ResponseBody> login(@QueryMap Map<String, String> map);

}
