package com.angcyo.sharebook.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;

import com.angcyo.sharebook.R;
import com.angcyo.uiview.skin.SkinHelper;
import com.angcyo.uiview.widget.RTextView;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/09 17:44
 * 修改人员：Robi
 * 修改时间：2017/05/09 17:44
 * 修改备注：
 * Version: 1.0.0
 */
public class BButton extends RTextView {
    public BButton(Context context) {
        super(context);
    }

    public BButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        setGravity(Gravity.CENTER);
        setClickable(true);
        setTextColor(ColorStateList.valueOf(Color.WHITE));
        setBackground(SkinHelper.getSkin().getThemeMaskBackgroundRoundSelector());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getMeasuredWidth(), getResources().getDimensionPixelOffset(R.dimen.default_button_height));
        }
    }
}
