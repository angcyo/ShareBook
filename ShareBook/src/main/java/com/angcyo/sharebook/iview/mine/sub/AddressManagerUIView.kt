package com.angcyo.sharebook.iview.mine.sub

import android.os.Bundle
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
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.widget.IShowState
import com.angcyo.uiview.rsen.RefreshLayout
import com.angcyo.uiview.utils.T_

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

            override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: AddressBean) {
                super.onBindDataView(holder, posInData, dataBean)
                holder.tv(R.id.name_view).text = dataBean.name
                holder.tv(R.id.phone_view).text = dataBean.phone
                holder.tv(R.id.address_view).text = "${dataBean.province} ${dataBean.city} ${dataBean.area} ${dataBean.street}"

                holder.cV(R.id.check_box).isChecked = dataBean.id == "1"
                holder.cV(R.id.check_box).isEnabled = dataBean.id != "1"

                holder.click(R.id.check_box) {
                    defaultAddrid(dataBean.id)
                }

                holder.click(R.id.edit_view) {
                    startIView(AddAddressUIView().setEditAddress(dataBean))
                }
                holder.click(R.id.delete_view) {
                    deleteAddrid(dataBean.id)
                    deleteItem(posInData)

                    if (rawItemCount == 0) {
                        showEmptyLayout()
                    }
                }
            }
        }
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        mViewHolder.click(R.id.add_address_view) {
            startIView(AddAddressUIView())
        }
    }

    override fun onViewShowNotFirst(bundle: Bundle?) {
        super.onViewShowNotFirst(bundle)
        loadData()
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

    private fun deleteAddrid(addrid: String) {
        add(RRetrofit.create(Api::class.java)
                .api(P.b(Action.DEL_ADDRESS, "addrid:$addrid"))
                .compose(RxBook.transformer(String::class.java))
                .subscribe(object : BSub<String>() {
                    override fun onSucceed(bean: String) {
                        super.onSucceed(bean)
                        T_.ok(bean)
                    }
                }))
    }

    /**设置默认地址*/
    private fun defaultAddrid(addrid: String) {
        add(RRetrofit.create(Api::class.java)
                .api(P.b(Action.DEFAULT_ADDRESS, "addrid:$addrid"))
                .compose(RxBook.transformer(String::class.java))
                .subscribe(object : BSub<String>() {
                    override fun onSucceed(bean: String) {
                        super.onSucceed(bean)
                        T_.ok(bean)
                        loadData()
                    }
                }))
    }
}
