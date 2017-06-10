package com.angcyo.sharebook.control

import com.angcyo.sharebook.http.Action
import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.P
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.bean.BookDetailBean
import com.angcyo.sharebook.http.service.Book
import com.angcyo.uiview.net.RRetrofit

/**
 * 管理所有收藏的书
 * Created by angcyo on 2017-06-04.
 */
object FavControl {
    var allFavBook: List<BookDetailBean>? = null

    fun initFavList() {
        RRetrofit.create(Book::class.java)
                .getALlFavBook(P.b(Action.GET_ALL_FAV_BOOK))
                .compose(RxBook.transformerList(BookDetailBean::class.java))
                .subscribe(object : BSub<List<BookDetailBean>>() {
                    override fun onSucceed(bean: List<BookDetailBean>) {
                        super.onSucceed(bean)
                        allFavBook = bean
                    }
                })
    }

    fun isFav(isbn: String, listener: OnFavResultListener) {
        if (allFavBook == null) {
            initFavList()
        }
    }

    interface OnFavResultListener {
        fun onFavResult(isFav: Boolean)
    }
}
