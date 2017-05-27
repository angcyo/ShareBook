package com.angcyo.sharebook.control

import com.angcyo.sharebook.BuildConfig
import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.P
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.service.Home
import com.angcyo.uiview.net.RRetrofit

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/26 14:51
 * 修改人员：Robi
 * 修改时间：2017/05/26 14:51
 * 修改备注：
 * Version: 1.0.0
 */
object MainControl {
    fun onMainLoad() {
        LoginControl.autoLogin()

        if (BuildConfig.DEBUG) {
            //onDebug()
        }
    }

    private fun onDebug() {
        RRetrofit.create(Home::class.java)
                .home(P.b())
                .compose(RxBook.transformer(String::class.java))
                .subscribe(object : BSub<String>() {
                    override fun onSucceed(bean: String) {
                        super.onSucceed(bean)
                    }
                })
    }

    fun onMainUnload() {

    }
}