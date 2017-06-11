package com.angcyo.sharebook.iview.mine.sub

import com.angcyo.sharebook.R
import com.angcyo.sharebook.iview.base.BaseItemUIView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder

/**
 * 添加收货地址
 * Created by angcyo on 2017-06-11.
 */
class AddAddressUIView : BaseItemUIView() {

    override fun getTitleString(): String {
        return "添加收货地址"
    }

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.view_add_address_layout
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item) {
                holder.click(R.id.address_view) {

                }
            }
        })
    }
}
