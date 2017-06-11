package com.angcyo.sharebook.iview.mine.sub

import com.angcyo.sharebook.R
import com.angcyo.sharebook.http.Action
import com.angcyo.sharebook.http.BSub
import com.angcyo.sharebook.http.P
import com.angcyo.sharebook.http.RxBook
import com.angcyo.sharebook.http.bean.AddressBean
import com.angcyo.sharebook.http.service.Api
import com.angcyo.sharebook.iview.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.github.pickerview.CityDialog
import com.angcyo.uiview.net.RRetrofit
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.Json
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.widget.ExEditText
import com.angcyo.uiview.widget.ItemInfoLayout

/**
 * 添加收货地址
 * Created by angcyo on 2017-06-11.
 */
class AddAddressUIView : BaseItemUIView() {

    var province: String? = null
    var city: String? = null
    var district: String? = null

    /**如果是编辑, 请传之前的地址id*/
    private var addressBean: AddressBean? = null

    private var addrid: String = ""
        get() {
            if (addressBean == null) {
                return ""
            } else {
                return addressBean!!.id
            }
        }

    fun setEditAddress(bean: AddressBean): AddAddressUIView {
        addressBean = bean
        return this
    }

    override fun getTitleString(): String {
        return if (addrid.isEmpty()) "添加收货地址" else "更新收货地址"
    }

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.view_add_address_layout
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                val addressLayout: ItemInfoLayout = holder.v(R.id.address_view)
                val nameView: ExEditText = holder.v(R.id.name_view)
                val phoneView: ExEditText = holder.v(R.id.phone_view)
                val streetView: ExEditText = holder.v(R.id.street_view)
                phoneView.setIsPhone(true, 11)

                holder.click(R.id.address_view) {
                    startIView(CityDialog(CityDialog.CityListener { province, city, district ->
                        this@AddAddressUIView.province = province
                        this@AddAddressUIView.city = city
                        this@AddAddressUIView.district = district

                        addressLayout.setItemDarkText("$province $city $district")
                    }))
                }

                //保存
                holder.click(R.id.save_view) {
                    if (!nameView.checkEmpty() && !phoneView.checkEmpty(true) && !streetView.checkEmpty()) {
                        if (province.isNullOrEmpty()) {
                            T_.error("请选择省市区!")
                        } else {
                            addAddress(AddressBean("", nameView.string(), phoneView.string(),
                                    province!!, city!!, district!!,
                                    streetView.string()))
                        }
                    }
                }

                //编辑模式
                if (addressBean != null) {
                    nameView.setText(addressBean!!.name)
                    phoneView.setText(addressBean!!.phone)
                    streetView.setText(addressBean!!.street)

                    addressLayout.setItemDarkText("${addressBean!!.province} ${addressBean!!.city} ${addressBean!!.area}")
                }
            }
        })
    }

    private fun addAddress(address: AddressBean) {
        UILoading.show2(mILayout).setLoadingTipText(if (addrid.isEmpty()) "正在添加收货地址..." else "正在更新收货地址").setCanCancel(false)
        add(RRetrofit.create(Api::class.java)
                .api(P.b(Action.ADD_ADDRESS, "addrid:$addrid", "addr:${Json.to(address)}"))
                .compose(RxBook.transformer(String::class.java))
                .subscribe(object : BSub<String>() {
                    override fun onSucceed(bean: String) {
                        super.onSucceed(bean)
                        T_.ok(bean)
                        finishIView()
                    }

                    override fun onEnd(isError: Boolean, isNoNetwork: Boolean, e: Throwable?) {
                        super.onEnd(isError, isNoNetwork, e)
                        UILoading.hide()
                    }
                }))
    }
}
