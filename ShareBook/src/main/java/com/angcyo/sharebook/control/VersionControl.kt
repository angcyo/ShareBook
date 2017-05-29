package com.angcyo.sharebook.control

import com.angcyo.library.utils.L
import com.angcyo.sharebook.App
import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.bean.VersionBean
import com.angcyo.sharebook.http.service.Version
import com.angcyo.uiview.container.ILayout
import com.angcyo.uiview.dialog.UIDialog
import com.angcyo.uiview.github.utilcode.utils.AppUtils
import com.angcyo.uiview.net.RRetrofit

/**
 * Created by angcyo on 2017-05-29.
 */
object VersionControl {
    fun check(layout: ILayout<*>) {
        RRetrofit.create(Version::class.java)
                .version(Version.url)
                .compose(RxBook.transformer(VersionBean::class.java))
                .subscribe(object : BSub<VersionBean>() {
                    override fun onSucceed(bean: VersionBean?) {
                        super.onSucceed(bean)
                        bean?.haveUpdate({
                            showUpdateDialog(layout, bean)
                        }) {
                            showNoUpdateDialog(layout)
                        }
                    }
                })
    }

    fun showUpdateDialog(layout: ILayout<*>, version: VersionBean) {
        UIDialog.build()
                .setDialogTitle("发现新版:${version.versionName}")
                .setDialogContent("${version.des}")
                .setOkText("下载安装")
                .setOkClick { dialog, _ ->
                    run {
                        dialog.setAutoFinishDialog(false)
                        L.e("...")
                    }
                }
                .showDialog(layout)
    }

    fun showNoUpdateDialog(layout: ILayout<*>) {
        UIDialog.build()
                .setDialogTitle("暂无更新")
                .setDialogContent("当前${AppUtils.getAppVersionName(App.getApp())}已是最新版.")
                .showDialog(layout)
    }
}
