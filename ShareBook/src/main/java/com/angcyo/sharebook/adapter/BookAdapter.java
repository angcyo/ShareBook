package com.angcyo.sharebook.adapter;

import android.content.Context;

import com.angcyo.sharebook.R;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.recycler.adapter.RBaseAdapter;
import com.lzy.imagepicker.GlideImageLoader;

import java.util.List;

/**
 * Created by angcyo on 2017-03-12.
 */

public class BookAdapter<T extends BookAdapter.IBook> extends RBaseAdapter<T> {
    public BookAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_book_layout;
    }

    @Override
    protected void onBindView(RBaseViewHolder holder, int position, T bean) {
        holder.tv(R.id.text_view).setText(bean.getBookTitle());
        GlideImageLoader.displayImage(holder.imgV(R.id.image_view), bean.getBookPic());
    }

    public interface IBook {
        String getBookTitle();

        String getBookPic();
    }
}
