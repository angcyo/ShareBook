package com.angcyo.sharebook.iview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.control.LoginControl;
import com.angcyo.sharebook.http.Action;
import com.angcyo.sharebook.http.BSub;
import com.angcyo.sharebook.http.P;
import com.angcyo.sharebook.http.RxBook;
import com.angcyo.sharebook.http.bean.BookDetailBean;
import com.angcyo.sharebook.http.service.Api;
import com.angcyo.sharebook.iview.base.BaseRecyclerUIView;
import com.angcyo.sharebook.iview.login.LoginUIView;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.recycler.RBaseItemDecoration;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.RRecyclerView;
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter;
import com.angcyo.uiview.recycler.adapter.RModelAdapter;
import com.angcyo.uiview.resources.ResUtil;
import com.angcyo.uiview.rsen.RefreshLayout;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.widget.RExTextView;
import com.angcyo.uiview.widget.RImageCheckView;
import com.lzy.imagepicker.GlideImageLoader;

import java.util.List;

/**
 * Created by angcyo on 2017-03-11.
 */

public class MyOrderUIView extends BaseRecyclerUIView<String, BookDetailBean, String> {

    private TextView mPriceView;
    private CheckBox mSelectAll;
    private TextView mSettleView;

    @Override
    protected String getTitleString() {
        return "我的书包";
    }

    @Override
    protected void createRecyclerRootView(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_my_order_layout);
        mRecyclerView = (RRecyclerView) baseContentLayout.findViewById(R.id.recycler_view);
        mRefreshLayout = (RefreshLayout) baseContentLayout.findViewById(R.id.refresh_view);

        initRecyclerView(mRecyclerView, null);
        initRefreshLayout(mRefreshLayout, null);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mSettleView = mViewHolder.v(R.id.settle_view);
        mSelectAll = mViewHolder.v(R.id.select_all);
        mPriceView = mViewHolder.v(R.id.price_view);

        mExBaseAdapter.addOnModelChangeListener(new RModelAdapter.SingleChangeListener() {
            @Override
            public void onSelectorChange(List<Integer> selectorList) {
                super.onSelectorChange(selectorList);
                mSelectAll.setChecked(selectorList.size() == mExBaseAdapter.getRawItemCount());
                mSettleView.setText(selectorList.size() + "");
                mSettleView.setEnabled(!selectorList.isEmpty());

                float price = 0f;
                for (Integer it : selectorList) {
                    BookDetailBean bean = mExBaseAdapter.getAllDatas().get(it);
                    price += Float.valueOf(bean.getPrice());
                }
                mPriceView.setText(String.valueOf(price));
            }
        });

        mSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectAll.isChecked()) {
                    mExBaseAdapter.setSelectorAll(mRecyclerView, R.id.check_box);
                } else {
                    mExBaseAdapter.unSelectorAll(mRecyclerView, R.id.check_box);
                }
            }
        });

        ResUtil.setBgDrawable(mSettleView, ResUtil.selector(
                ResUtil.createDrawable(getColor(R.color.base_red), 0f),
                ResUtil.createDrawable(getColor(R.color.base_red_tran), 0f),
                ResUtil.createDrawable(getColor(R.color.base_color_disable), 0f)
        ));

        mSettleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected RExBaseAdapter createAdapter() {
        return (RExBaseAdapter) new RExBaseAdapter<String, BookDetailBean, String>(mActivity) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_order_list_layout;
            }

            @Override
            protected void onBindModelView(int model, boolean isSelector, final RBaseViewHolder holder, final int position, BookDetailBean bean) {
                super.onBindModelView(model, isSelector, holder, position, bean);
                final RImageCheckView checkView = holder.v(R.id.check_box);
                checkView.setChecked(isSelector);

                checkView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelectorPosition(position, checkView);
                    }
                });
            }

            @Override
            protected void onBindDataView(final RBaseViewHolder holder, final int posInData, final BookDetailBean bean) {
                int xhdpi = getDimensionPixelOffset(R.dimen.base_xhdpi);
                holder.v(R.id.book_root_layout).setPadding(0, xhdpi, xhdpi, xhdpi);

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
                        setSelectorPosition(posInData, holder.v(R.id.check_box));
                    }
                });


                holder.click(R.id.trash_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(posInData);
                        trash(bean.getIsbn());
                        if (getRawItemCount() == 0) {
                            showEmptyLayout();
                        }
                    }
                });
            }
        }.setModel(RModelAdapter.MODEL_MULTI);
    }

    @Override
    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new RBaseItemDecoration(getResources().getDimensionPixelSize(R.dimen.base_xhdpi), getColor(R.color.default_base_line)).setDrawLastLine(false);
    }

    @Override
    public void onViewShow(long viewShowCount) {
        super.onViewShow(viewShowCount);
        if (viewShowCount > 0) {
            loadData();
        }
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
                        onUILoadReset();
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

    /**
     * 从购物车中删除
     */
    private void trash(String isbn) {
        //UILoading.show2(mILayout);
        add(RRetrofit.create(Api.class)
                .api(P.b(Action.DEL_CART, "isbn:" + isbn))
                .compose(RxBook.transformer(String.class))
                .subscribe(new BSub<String>() {
                    @Override
                    public void onSucceed(String bean) {
                        super.onSucceed(bean);
                        T_.ok(bean);
                    }

                    @Override
                    public void onEnd(boolean isError, boolean isNoNetwork, Throwable e) {
                        super.onEnd(isError, isNoNetwork, e);
                        //UILoading.hide();
                    }
                }));
    }

    @Override
    public void loadData() {
        if (LoginControl.INSTANCE.isLogin()) {
            super.loadData();
        } else {
            showErrorLayout();
        }
    }

    @Override
    protected int getBaseErrorLayoutId() {
        return R.layout.error_login_layout;
    }

    @Override
    protected void initBaseErrorLayout(View view) {
        view.findViewById(R.id.login_view).setBackground(getTipButtonSelector());
        view.findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentILayout.startIView(new LoginUIView().setEnableClipMode(ClipMode.CLIP_BOTH, v));
            }
        });
    }
}
