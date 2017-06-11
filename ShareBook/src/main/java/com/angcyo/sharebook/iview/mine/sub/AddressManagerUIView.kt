package com.angcyo.sharebook.iview.mine.sub

import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.angcyo.sharebook.R
import com.angcyo.sharebook.http.Action
import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.P
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.bean.AddressBean
import com.angcyo.sharebook.http.service.Api
import com.angcyo.sharebook.iview.base.BaseRecyclerUIView
import com.angcyo.uiview.net.RRetrofit
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.widget.IShowState
import com.angcyo.uiview.rsen.RefreshLayout

/**
 * 收货地址管理
 * Created by angcyo on 2017-06-11.
 */
class AddressManagerUIView : BaseRecyclerUIView<String, AddressBean, String>() {
    override fun getTitleString(): String {
        return "收货地址管理"
    }

    override fun createRecyclerRootView(baseContentLayout: RelativeLayout, inflater: LayoutInflater) {
        inflate(R.layout.view_address_manager_layout)
        mRecyclerView = baseContentLayout.findViewById(R.id.recycler_view) as RRecyclerView
        mRefreshLayout = baseContentLayout.findViewById(R.id.refresh_view) as RefreshLayout

        initRecyclerView(mRecyclerView, null)
        initRefreshLayout(mRefreshLayout, null)
    }

    override fun createAdapter(): RExBaseAdapter<String, AddressBean, String> {
        return object : RExBaseAdapter<String, AddressBean, String>(mActivity) {
            override fun getItemLayoutId(viewType: Int): Int {
                return R.layout.item_address_layout
            }
        }
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mViewHolder.click(R.id.add_address_view) {
            startIView(AddAddressUIView())
        }
    }

    override fun onUILoadData() {
        super.onUILoadData()
        add(RRetrofit.create(Api::class.java)
                .api(P.b(Action.ALL_ADDRESS))
                .compose(RxBook.transformerList(AddressBean::class.java))
                .subscribe(object : BSub<List<AddressBean>>() {
                    override fun onSucceed(bean: List<AddressBean>) {
                        super.onSucceed(bean)
                        showContentLayout()
                        if (bean.isEmpty()) {
                            mExBaseAdapter.setShowState(IShowState.EMPTY)
                        } else {
                            mExBaseAdapter.setShowState(IShowState.NORMAL)
                            mExBaseAdapter.resetAllData(bean)
                        }
                    }

                    override fun onEnd(isError: Boolean, isNoNetwork: Boolean, e: Throwable?) {
                        super.onEnd(isError, isNoNetwork, e)
                        onUILoadReset()
                        if (isError) {
                            showNonetLayout { loadData() }
                        }
                    }
                }))
    }
}
