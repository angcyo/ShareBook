package com.angcyo.sharebook.iview.base;

import android.os.Bundle;

import com.angcyo.uiview.base.UIRecyclerUIView;
import com.angcyo.uiview.widget.viewpager.UIViewPager;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/26 15:14
 * 修改人员：Robi
 * 修改时间：2017/05/26 15:14
 * 修改备注：
 * Version: 1.0.0
 */
public abstract class BaseRecyclerUIView<H, T, F> extends UIRecyclerUIView<H, T, F> {

    protected int mPage = 1;

    protected boolean isShowInViewPager() {
        return false;
    }

    @Override
    public void onViewShowFirst(Bundle bundle) {
        super.onViewShowFirst(bundle);
        if (!isShowInViewPager()) {
            loadData();
        }
    }

    public void loadData() {
        onUILoadData();
    }

    @Override
    public void onBaseLoadData() {
        super.onBaseLoadData();
        loadData();
    }

    @Override
    public void onBaseLoadMore() {
        super.onBaseLoadMore();
    }

    @Override
    public void onShowInPager(UIViewPager viewPager) {
        super.onShowInPager(viewPager);
        if (isShowInViewPager()) {
            loadData();
        }
    }

    @Override
    public void onHideInPager(UIViewPager viewPager) {
        super.onHideInPager(viewPager);
        if (isShowInViewPager()) {
            onCancel();
        }
    }

    /**
     * 是否有下一页, 分页加载需要
     */
    protected boolean haveNext() {
        return true;
    }

    protected void onUILoadData() {

    }

    /**
     * 恢复界面
     */
    protected void onUILoadFinish() {
        onUILoadFinish(false);
    }

    protected void onUILoadFinish(boolean isEmpty) {
        if (isEmpty) {
            showEmptyLayout();
        } else {
            showContentLayout();

            if (mRefreshLayout != null) {
                mRefreshLayout.setRefreshEnd();
            }
            if (mExBaseAdapter != null) {
                if (mExBaseAdapter.isEnableLoadMore()) {
                    mExBaseAdapter.setLoadMoreEnd();
                }
            }
        }
    }
}
