package com.angcyo.sharebook.http.bean

import com.angcyo.sharebook.App
import com.angcyo.uiview.github.utilcode.utils.AppUtils

/*{
    "version_name":"1.0.100",
    "version_code":"100",
    "des":"1:1\n2:2\n3:3\n",
    "url":"www.baidu.com",
    "force":false
}
*/
data class VersionBean(
        val versionName: String? = null,
        val des: String? = null,
        val versionCode: String? = null,
        val force: Boolean? = null,
        val url: String? = null
) {
    fun haveUpdate(update: () -> Unit, noUpdate: () -> Unit) {
        val toInt = versionCode?.toInt()
        if (toInt == null) {
            noUpdate.invoke()
        } else {
            if (toInt > AppUtils.getAppVersionCode(App.getApp())) {
                update.invoke()
            } else {
                noUpdate.invoke()
            }
        }
    }
}
