package com.angcyo.sharebook.iview;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.adapter.BookAdapter;
import com.angcyo.uiview.base.UIRecyclerUIView;
import com.angcyo.uiview.net.Rx;
import com.angcyo.uiview.recycler.RBaseItemDecoration;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RExBaseAdapter;
import com.angcyo.uiview.recycler.widget.IShowState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by angcyo on 2017-03-11.
 */

public class BookSpaceUIView extends UIRecyclerUIView {
    @Override
    protected RExBaseAdapter createAdapter() {
        return new RExBaseAdapter<String, MainItemBean, String>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_home_type_layout;
            }

            @Override
            protected void onBindDataView(RBaseViewHolder holder, int posInData, MainItemBean dataBean) {
                holder.tv(R.id.text_view).setText("类型:" + posInData);
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
    protected String getTitleString() {
        return "书场";
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
    public void onViewShowFirst(Bundle bundle) {
        super.onViewShowFirst(bundle);
        mExBaseAdapter.setShowState(IShowState.NORMAL);
        mExBaseAdapter.resetAllData(createAllDatas());
    }

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
        //test();
    }

    private List<MainItemBean> createAllDatas() {
        List<MainItemBean> beanList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            beanList.add(new MainItemBean());
        }
        return beanList;
    }

    private void test() {
        Rx.just("")
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        mExBaseAdapter.setShowState(IShowState.LOADING);
                        return null;
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        mExBaseAdapter.setShowState(IShowState.EMPTY);
                        return null;
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        mExBaseAdapter.setShowState(IShowState.NONET);
                        return null;
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        mExBaseAdapter.setShowState(IShowState.ERROR);
                        return null;
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        mExBaseAdapter.setShowState(IShowState.NORMAL);
                        return null;
                    }
                })
                .subscribe();
    }

    public static class MainItemBean {
    }
}
