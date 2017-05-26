package com.angcyo.sharebook.iview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.adapter.BookAdapter;
import com.angcyo.sharebook.http.BSub;
import com.angcyo.sharebook.http.P;
import com.angcyo.sharebook.http.RxBook;
import com.angcyo.sharebook.http.bean.HomeBean;
import com.angcyo.sharebook.http.service.Home;
import com.angcyo.sharebook.iview.base.BaseRecyclerUIView;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.net.Rx;
import com.angcyo.uiview.recycler.RBaseItemDecoration;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter;
import com.angcyo.uiview.recycler.widget.IShowState;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by angcyo on 2017-03-11.
 */

public class BookSpaceUIView extends BaseRecyclerUIView<BookSpaceUIView.HBean,
        BookSpaceUIView.DBean,
        BookSpaceUIView.FBean> {
    @Override
    protected RExBaseAdapter createAdapter() {
        return new RExBaseAdapter<BookSpaceUIView.HBean,
                BookSpaceUIView.DBean,
                BookSpaceUIView.FBean>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_home_type_layout;
            }

            @Override
            protected void onBindHeaderView(RBaseViewHolder holder, int posInHeader, BookSpaceUIView.HBean headerBean) {
                holder.tv(R.id.text_view).setText(headerBean.type);
                RRecyclerView recyclerView = holder.reV(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(new BookAdapter<>(mActivity, headerBean.mBeanList));

                new PagerSnapHelper().attachToRecyclerView(recyclerView);

                recyclerView.setEnableAutoScroll(true);
            }

            @Override
            protected void onBindDataView(RBaseViewHolder holder, int posInData, BookSpaceUIView.DBean dataBean) {
                holder.tv(R.id.text_view).setText(dataBean.type);
                RRecyclerView recyclerView = holder.reV(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                recyclerView.setAdapter(new BookAdapter<>(mActivity, dataBean.mBeanList));

                recyclerView.setEnableAutoScroll(false);
            }

            @Override
            protected void onBindFooterView(RBaseViewHolder holder, int posInFooter, BookSpaceUIView.FBean footerBean) {
                holder.tv(R.id.text_view).setText(footerBean.type);
                RRecyclerView recyclerView = holder.reV(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                recyclerView.setAdapter(new BookAdapter<>(mActivity, footerBean.mBeanList));

                recyclerView.setEnableAutoScroll(false);
            }
        };
    }

    @Override
    protected String getTitleString() {
        return "书库";
    }

    @Override
    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new RBaseItemDecoration(getResources().getDimensionPixelSize(R.dimen.base_xhdpi), getResources().getColor(R.color.default_base_line));
    }


    @Override
    protected void onUILoadData() {
        add(RRetrofit.create(Home.class)
                .home(P.b())
                .compose(RxBook.transformer(HomeBean.class))
                .subscribe(new BSub<HomeBean>() {
                    @Override
                    public void onSucceed(HomeBean bean) {
                        if (bean != null) {
                            onUILoadFinish();

                            mExBaseAdapter.setHeaderData(new HBean(bean.getLatern()));
                            mExBaseAdapter.setData(new DBean(bean.getTopical()));
                            mExBaseAdapter.setFooterData(new FBean(bean.getSpecial()));

                            HomeBean.BASE_IMG_PATH = bean.getPath();
                            mExBaseAdapter.notifyDataSetChanged();
                        }
                    }
                }));
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

    public static class HBean {
        public String type = "Latern";
        public List<HomeBean.LaternBean> mBeanList;

        public HBean(List<HomeBean.LaternBean> beanList) {
            mBeanList = beanList;
        }
    }

    public static class DBean {
        public String type = "Topical";
        public List<HomeBean.TopicalBean> mBeanList;

        public DBean(List<HomeBean.TopicalBean> beanList) {
            mBeanList = beanList;
        }
    }

    public static class FBean {
        public String type = "Special";
        public List<HomeBean.SpecialBean> mBeanList;

        public FBean(List<HomeBean.SpecialBean> beanList) {
            mBeanList = beanList;
        }
    }
}
