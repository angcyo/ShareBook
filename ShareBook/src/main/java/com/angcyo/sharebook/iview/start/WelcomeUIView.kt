package com.angcyo.sharebook.iview.start

import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.angcyo.sharebook.R
import com.angcyo.sharebook.iview.MainNavUIView
import com.angcyo.sharebook.iview.base.BaseContentUIView
import com.angcyo.uiview.model.TitleBarPattern

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：欢迎界面
 * 创建人员：Robi
 * 创建时间：2017/05/26 09:53
 * 修改人员：Robi
 * 修改时间：2017/05/26 09:53
 * 修改备注：
 * Version: 1.0.0
 */
class WelcomeUIView : BaseContentUIView() {

    override fun getTitleBar(): TitleBarPattern? = null

    override fun inflateContentLayout(baseContentLayout: RelativeLayout?, inflater: LayoutInflater?) {
        inflate(R.layout.view_welcome)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        postDelayed({
            //replaceIView(MainUIView().setEnableClipMode(ClipMode.CLIP_START))
            replaceIView(MainNavUIView(), false)
        }, 1_000)
    }
}