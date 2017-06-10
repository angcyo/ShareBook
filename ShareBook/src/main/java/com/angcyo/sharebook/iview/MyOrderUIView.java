package com.angcyo.sharebook.iview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.http.Action;
import com.angcyo.sharebook.http.BSub;
import com.angcyo.sharebook.http.P;
import com.angcyo.sharebook.http.RxBook;
import com.angcyo.sharebook.http.bean.BookDetailBean;
import com.angcyo.sharebook.http.service.Api;
import com.angcyo.sharebook.iview.base.BaseRecyclerUIView;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.recycler.RBaseItemDecoration;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter;
import com.angcyo.uiview.widget.RExTextView;
import com.lzy.imagepicker.GlideImageLoader;

import java.util.List;

/**
 * Created by angcyo on 2017-03-11.
 */

public class MyOrderUIView extends BaseRecyclerUIView<String, BookDetailBean, String> {

    @Override
    protected String getTitleString() {
        return "我的书包";
    }

    @Override
    protected RExBaseAdapter createAdapter() {
        return new RExBaseAdapter<String, BookDetailBean, String>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_order_list_layout;
            }

            @Override
            protected void onBindDataView(RBaseViewHolder holder, int posInData, BookDetailBean bean) {
                GlideImageLoader.displayImage(holder.imgV(R.id.image_view), bean.getPic(), R.drawable.default_image);
                holder.tv(R.id.title_view).setText(bean.getTitle());
                holder.tv(R.id.author_view).setText(bean.getAuthor());
                holder.tv(R.id.summary_view).setText(bean.getSummary());
                RExTextView summaryView = holder.v(R.id.summary_view);
                summaryView.setMaxShowLine(4);
                summaryView.setFoldString("");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

            }
        };
    }

    @Override
    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new RBaseItemDecoration(getResources().getDimensionPixelSize(R.dimen.base_xhdpi), getResources().getColor(R.color.default_base_line));
    }

    @Override
    protected void onUILoadData() {
        super.onUILoadData();
        add(RRetrofit.create(Api.class)
                .api(P.b(Action.ALL_CARTS))
                .compose(RxBook.transformerList(BookDetailBean.class))
                .subscribe(new BSub<List<BookDetailBean>>() {
                    @Override
                    public void onSucceed(List<BookDetailBean> bean) {
                        super.onSucceed(bean);
                        if (bean.isEmpty()) {
                            showEmptyLayout();
                        } else {
                            showContentLayout();
                            mExBaseAdapter.resetAllData(bean);
                        }
                    }

                    @Override
                    public void onEnd(boolean isError, boolean isNoNetwork, Throwable e) {
                        super.onEnd(isError, isNoNetwork, e);
                        onUILoadFinish();
                        if (isError) {
                            showNonetLayout(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadData();
                                }
                            });
                        }
                    }
                }));
    }
}
