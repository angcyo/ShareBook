package com.angcyo.sharebook.iview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.adapter.BookAdapter;
import com.angcyo.sharebook.adapter.BookListAdapter;
import com.angcyo.sharebook.http.Action;
import com.angcyo.sharebook.http.BSub;
import com.angcyo.sharebook.http.P;
import com.angcyo.sharebook.http.RxBook;
import com.angcyo.sharebook.http.bean.HomeBean;
import com.angcyo.sharebook.http.service.Home;
import com.angcyo.sharebook.iview.base.BaseRecyclerUIView;
import com.angcyo.sharebook.iview.book.BookDetailUIView;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.net.Rx;
import com.angcyo.uiview.recycler.RBaseItemDecoration;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RExItemDecoration;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter;
import com.angcyo.uiview.recycler.widget.IShowState;

import java.util.ArrayList;
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

    RecyclerView.ItemDecoration mBookListItemDecoration = new RExItemDecoration(new RExItemDecoration.SingleItemCallback() {
        @Override
        public void getItemOffsets2(Rect outRect, int position, int edge) {
            super.getItemOffsets2(outRect, position, edge);
            outRect.bottom = getDimensionPixelOffset(R.dimen.base_xhdpi);
        }

        @Override
        public void draw(Canvas canvas, TextPaint paint, View itemView, Rect offsetRect, int itemCount, int position) {
            super.draw(canvas, paint, itemView, offsetRect, itemCount, position);
            drawBottomLine(canvas, paint, itemView, offsetRect, itemCount, position);
        }
    });

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

                recyclerView.setOnFlingListener(null);
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
                RRecyclerView recyclerView = holder.reV(R.id.recycler_view);
                if (posInFooter == 0) {
                    holder.tv(R.id.text_view).setText(footerBean.type);
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recyclerView.setAdapter(new BookAdapter<>(mActivity, footerBean.mBeanList));
                } else {
                    holder.tv(R.id.text_view).setText(footerBean.type2);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new BookListAdapter(mActivity, footerBean.mBeanList2).setBookItemClick(new BookListAdapter.OnBookItemClick() {
                        @Override
                        public void onBookItemClick(String isbn) {
                            mParentILayout.startIView(new BookDetailUIView(isbn));
                        }
                    }));
                    recyclerView.setBackgroundColor(getColor(R.color.base_chat_bg_color));
                    recyclerView.removeItemDecoration(mBookListItemDecoration);
                    recyclerView.addItemDecoration(mBookListItemDecoration);
                }
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
        super.onUILoadData();
        add(RRetrofit.create(Home.class)
                .home(P.b(Action.HOME))
                .compose(RxBook.transformer(HomeBean.class))
                .subscribe(new BSub<HomeBean>() {
                    @Override
                    public void onSucceed(HomeBean bean) {
                        if (bean != null) {
                            showContentLayout();

                            mExBaseAdapter.setHeaderData(new HBean(bean.getLatern()));
                            mExBaseAdapter.setDataData(new DBean(bean.getTopical()));

                            List<FBean> fBeanList = new ArrayList<>();
                            fBeanList.add(new FBean(bean.getSpecial()));
                            fBeanList.add(new FBean().setBeanList2(bean.getRecommand()));
                            mExBaseAdapter.setAllFooterDatas(fBeanList);

                            HomeBean.BASE_IMG_PATH = bean.getPath();
                            mExBaseAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onEnd(boolean isError, boolean isNetwork, Throwable e) {
                        super.onEnd(isError, isNetwork, e);
                        if (isNetwork) {
                            showNonetLayout(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadData();
                                }
                            });
                        } else {
                            onUILoadFinish();
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

        public String type2 = "Recommand";
        public List<HomeBean.RecommandBean> mBeanList2;

        public FBean() {
        }

        public FBean(List<HomeBean.SpecialBean> beanList) {
            mBeanList = beanList;
        }

        public FBean setBeanList2(List<HomeBean.RecommandBean> beanList2) {
            mBeanList2 = beanList2;
            return this;
        }
    }
}
