package com.angcyo.sharebook.control

import com.angcyo.sharebook.http.Action
import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.P
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.bean.TokenBean
import com.angcyo.sharebook.http.service.User
import com.angcyo.uiview.net.RRetrofit
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.T_
import com.orhanobut.hawk.Hawk

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/26 10:22
 * 修改人员：Robi
 * 修改时间：2017/05/26 10:22
 * 修改备注：
 * Version: 1.0.0
 */
object LoginControl {
    val KEY_PHONE = "key_phone"
    val KEY_PHONES = "key_phones"
    val KEY_PWD = "key_pwd"

    /**登录成功之后的token*/
    var token = ""
    /**通常是手机号码*/
    var uid = ""

    /**内存当中保存登录信息*/
    fun setLoginInfo(uid: String, token: String) {
        this.token = token
        this.uid = uid
    }

    //是否登录
    fun isLogin() = !(token.isEmpty() || uid.isEmpty())

    //如果登录成功, 自动调用函数
    fun isLogin(yes: () -> Unit) {
        if (isLogin()) {
            yes.invoke()
        } else {
            T_.error("请先登录.")
        }
    }

    //自动登录
    fun setAutoLoginInfo(phone: String, pwd: String) {
        Hawk.put(KEY_PHONE, phone)
        Hawk.put(KEY_PWD, pwd)

        val phones = getPhonesString()
        if (!phones.contains(phone)) {
            Hawk.put(KEY_PHONES, phones + "," + phone)
        }
    }

    fun getLastLoginPhone(): String {
        return Hawk.get(KEY_PHONE, "")
    }

    fun getPhonesList(): List<String> {
        return RUtils.split(getPhonesString())
    }

    private fun getPhonesString(): String {
        return Hawk.get(KEY_PHONES, "")
    }

    //自动登录
    fun autoLogin() {
        val phone = getLastLoginPhone()
        val pwd = Hawk.get(KEY_PWD, "")

        if (phone.isNotEmpty() && pwd.isNotEmpty()) {
            RRetrofit.create(User::class.java)
                    .login(P.b(Action.LOGIN, "uid:" + phone, "pwd:" + pwd))
                    .compose(RxBook.transformer(TokenBean::class.java))
                    .subscribe(object : BSub<TokenBean>() {
                        override fun onSucceed(bean: TokenBean) {
                            super.onSucceed(bean)
                            LoginControl.setLoginInfo(phone, bean.token)
                        }
                    })
        }
    }
}
