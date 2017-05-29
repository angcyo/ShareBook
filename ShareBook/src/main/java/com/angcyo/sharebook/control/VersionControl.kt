package com.angcyo.sharebook.control

import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.bean.VersionBean
import com.angcyo.sharebook.http.service.Version
import com.angcyo.uiview.container.ILayout
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
                    }
                })
    }
}
