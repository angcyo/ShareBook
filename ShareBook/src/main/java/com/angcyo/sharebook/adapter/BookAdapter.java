package com.angcyo.sharebook.adapter;

import android.content.Context;

import com.angcyo.sharebook.R;
import com.angcyo.uiview.recycler.RBaseAdapter;
import com.angcyo.uiview.recycler.RBaseViewHolder;

import java.util.List;

/**
 * Created by angcyo on 2017-03-12.
 */

public class BookAdapter extends RBaseAdapter<String> {
    public BookAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_book_layout;
    }

    @Override
    protected void onBindView(RBaseViewHolder holder, int position, String bean) {
    }
}
