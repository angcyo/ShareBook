package com.angcyo.sharebook.iview

import android.os.Bundle
import android.view.View
import com.angcyo.sharebook.R
import com.angcyo.sharebook.control.MainControl
import com.angcyo.sharebook.control.VersionControl
import com.angcyo.sharebook.iview.mine.MineUIView
import com.angcyo.uiview.base.UINavigationView
import com.angcyo.uiview.container.UIParam
import com.angcyo.uiview.skin.SkinHelper

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/06/06 11:44
 * 修改人员：Robi
 * 修改时间：2017/06/06 11:44
 * 修改备注：
 * Version: 1.0.0
 */
class MainNavUIView : UINavigationView() {

    override fun onViewCreate(rootView: View, param: UIParam) {
        super.onViewCreate(rootView, param)
        MainControl.onMainLoad()
    }

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
        VersionControl.check(mILayout, true)
    }

    override fun onViewUnload() {
        super.onViewUnload()
        MainControl.onMainUnload()
    }

    override fun createPages(pages: ArrayList<PageBean>) {
        pages.add(PageBean(BookSpaceUIView(), "", "书库",
                getColor(R.color.base_text_color_dark), SkinHelper.getSkin().themeSubColor,
                R.drawable.book_stack_48, R.drawable.book_stack_48_color))

        pages.add(PageBean(GoodSpaceUIView(), "", "推荐",
                getColor(R.color.base_text_color_dark), SkinHelper.getSkin().themeSubColor,
                R.drawable.book_recommend_48, R.drawable.book_recommend_48_color))

        pages.add(PageBean(MyOrderUIView(), "", "书包",
                getColor(R.color.base_text_color_dark), SkinHelper.getSkin().themeSubColor,
                R.drawable.book_order_48, R.drawable.book_order_48_color))

        pages.add(PageBean(MineUIView(), "", "我的",
                getColor(R.color.base_text_color_dark), SkinHelper.getSkin().themeSubColor,
                R.drawable.book_mine_48, R.drawable.book_mine_48_color))
    }

}
