package com.angcyo.sharebook.iview.mine

import android.text.TextUtils
import com.angcyo.rcode.zxing.camera.CameraManager
import com.angcyo.sharebook.control.LoginControl
import com.angcyo.sharebook.http.Action
import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.P
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.service.Book
import com.angcyo.sharebook.iview.base.RScanUIView
import com.angcyo.uiview.net.RRetrofit
import com.angcyo.uiview.utils.ScreenUtil
import com.angcyo.uiview.utils.T_
import rx.functions.Action1

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/26 13:57
 * 修改人员：Robi
 * 修改时间：2017/05/26 13:57
 * 修改备注：
 * Version: 1.0.0
 */

class SBScanUIView(onScanResult: Action1<String>) : RScanUIView(onScanResult) {

    var lastIsbn = ""

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        CameraManager.FRAME_WIDTH = (ScreenUtil.density * 300).toInt()
        CameraManager.FRAME_HEIGHT = (ScreenUtil.density * 200).toInt()
    }

    override fun onHandleDecode(msg: String?) {
        scanAgain()

        if (msg == null || msg.isEmpty()) {
            return
        }

        if (!TextUtils.isEmpty(lastIsbn) && TextUtils.equals(msg, lastIsbn)) {
            T_.error("重复添加 $lastIsbn")
            return
        }

        beepManager.playBeepSoundAndVibrate()

        with(LoginControl) {
            isLogin {
                add(RRetrofit.create(Book::class.java)
                        .addBook(P.b(Action.ADD_BOOK, "uid:" + uid, "token:" + token, "isbn:" + msg))
                        .compose(RxBook.transformer(String::class.java))
                        .subscribe(object : BSub<String>() {
                            override fun onSucceed(bean: String) {
                                super.onSucceed(bean)
                                lastIsbn = msg
                                T_.show("$msg $bean")
                            }
                        }))
            }
        }
    }
}