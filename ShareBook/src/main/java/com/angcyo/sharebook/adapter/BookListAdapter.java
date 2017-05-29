package com.angcyo.sharebook.adapter;

import android.content.Context;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.http.bean.HomeBean;
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
    protected void onBindView(RBaseViewHolder holder, int position, HomeBean.RecommandBean bean) {
        GlideImageLoader.displayImage(holder.imgV(R.id.image_view), bean.getBookPic(), R.drawable.default_image);
        holder.tv(R.id.title_view).setText(bean.getTitle());
        holder.tv(R.id.author_view).setText(bean.getAuthor());
        holder.tv(R.id.summary_view).setText(bean.getSummary());
        RExTextView summaryView = holder.v(R.id.summary_view);
        summaryView.setMaxShowLine(4);
        summaryView.setFoldString("");
    }
}
