package com.angcyo.sharebook.adapter;

import android.content.Context;
import android.view.View;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.http.Action;
import com.angcyo.sharebook.http.BSub;
import com.angcyo.sharebook.http.P;
import com.angcyo.sharebook.http.RxBook;
import com.angcyo.sharebook.http.bean.HomeBean;
import com.angcyo.sharebook.http.service.Book;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.adapter.RBaseAdapter;
import com.angcyo.uiview.widget.RExTextView;
import com.lzy.imagepicker.GlideImageLoader;

import java.util.List;

/**
 * Created by angcyo on 2017-03-12.
 */

public class BookListAdapter extends RBaseAdapter<HomeBean.RecommandBean> {
    public BookListAdapter(Context context, List<HomeBean.RecommandBean> datas) {
        super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_book_list_layout;
    }

    @Override
    protected void onBindView(RBaseViewHolder holder, int position, final HomeBean.RecommandBean bean) {
        GlideImageLoader.displayImage(holder.imgV(R.id.image_view), bean.getBookPic(), R.drawable.default_image);
        holder.tv(R.id.title_view).setText(bean.getTitle());
        holder.tv(R.id.author_view).setText(bean.getAuthor());
        holder.tv(R.id.summary_view).setText(bean.getSummary());
        RExTextView summaryView = holder.v(R.id.summary_view);
        summaryView.setMaxShowLine(4);
        summaryView.setFoldString("");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RRetrofit.create(Book.class)
                        .searchBook(P.b(Action.SEARCH_BOOK, "isbn:" + bean.getIsbn()))
                        .compose(RxBook.transformer(String.class))
                        .subscribe(new BSub<String>() {
                            @Override
                            public void onSucceed(String bean) {
                                super.onSucceed(bean);
                            }
                        });
            }
        });
    }
}
