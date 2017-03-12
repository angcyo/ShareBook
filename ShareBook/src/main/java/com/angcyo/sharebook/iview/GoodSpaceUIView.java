package com.angcyo.sharebook.iview;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.adapter.BookAdapter;
import com.angcyo.uiview.base.UIRecyclerUIView;
import com.angcyo.uiview.recycler.RBaseItemDecoration;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RExBaseAdapter;
import com.angcyo.uiview.recycler.widget.IShowState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angcyo on 2017-03-11.
 */

public class GoodSpaceUIView extends UIRecyclerUIView {

    @Override
    protected String getTitleString() {
        return "推荐";
    }

    @Override
    protected RExBaseAdapter createAdapter() {
        return new RExBaseAdapter<String, MainItemBean, String>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_home_type_layout;
            }

            @Override
            protected void onBindDataView(RBaseViewHolder holder, int posInData, MainItemBean dataBean) {
                holder.tv(R.id.text_view).setText("pos:" + posInData);
                holder.reV(R.id.recycler_view).setAdapter(new BookAdapter(mActivity, createBooks()));
            }
        };
    }

    private List<String> createBooks() {
        List<String> beanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            beanList.add("");
        }
        return beanList;
    }

    @Override
    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new RBaseItemDecoration(getResources().getDimensionPixelSize(R.dimen.base_xhdpi), getResources().getColor(R.color.default_base_line));
    }

    @Override
    protected void afterInflateView(RelativeLayout baseContentLayout) {
        mExBaseAdapter.setShowState(IShowState.LOADING);
    }

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
        //test();
    }

    @Override
    public void onViewShowFirst(Bundle bundle) {
        super.onViewShowFirst(bundle);
        mExBaseAdapter.resetAllData(createAllDatas());
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mExBaseAdapter.setShowState(IShowState.NORMAL);
            }
        }, 2000);
    }

    private List<MainItemBean> createAllDatas() {
        List<MainItemBean> beanList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            beanList.add(new MainItemBean());
        }
        return beanList;
    }

    public static class MainItemBean {
    }

}
