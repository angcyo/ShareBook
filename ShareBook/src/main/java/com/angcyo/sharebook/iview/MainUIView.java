package com.angcyo.sharebook.iview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.control.MainControl;
import com.angcyo.sharebook.iview.base.BaseContentUIView;
import com.angcyo.sharebook.iview.mine.MineUIView;
import com.angcyo.uiview.container.UILayoutImpl;
import com.angcyo.uiview.container.UIParam;
import com.angcyo.uiview.github.tablayout.CommonTabLayout;
import com.angcyo.uiview.github.tablayout.TabEntity;
import com.angcyo.uiview.github.tablayout.TabLayoutUtil;
import com.angcyo.uiview.github.tablayout.listener.CustomTabEntity;
import com.angcyo.uiview.github.tablayout.listener.OnTabSelectListener;
import com.angcyo.uiview.model.TitleBarPattern;

import java.util.ArrayList;

/**
 * Created by angcyo on 2017-03-12.
 */

public class MainUIView extends BaseContentUIView {

    UILayoutImpl mMainUILayout;
    CommonTabLayout mCommonTabLayout;
    int lastIndex = 0;
    private BookSpaceUIView mBookSpaceUIView;
    private GoodSpaceUIView mGoodSpaceUIView;
    private MyOrderUIView mMyOrderUIView;
    private MineUIView mMineUIView;

    @Override
    protected TitleBarPattern getTitleBar() {
        return null;
    }

    @Override
    protected void inflateContentLayout(RelativeLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_main);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mMainUILayout = mViewHolder.v(R.id.main_layout_imp);
        mCommonTabLayout = mViewHolder.v(R.id.bottom_navigation_layout);

        mMainUILayout.setEnableSwipeBack(false);
        mBookSpaceUIView = new BookSpaceUIView();
        mGoodSpaceUIView = new GoodSpaceUIView();
        mMyOrderUIView = new MyOrderUIView();
        mMineUIView = new MineUIView();

        mBookSpaceUIView.bindParentILayout(getILayout());
        mGoodSpaceUIView.bindParentILayout(getILayout());
        mMyOrderUIView.bindParentILayout(getILayout());
        mMineUIView.bindParentILayout(getILayout());

        setChildILayout(mMainUILayout);
    }

    @Override
    public void onViewCreate(View rootView, UIParam param) {
        super.onViewCreate(rootView, param);
        MainControl.INSTANCE.onMainLoad();
    }

    @Override
    public void onViewUnload() {
        super.onViewUnload();
        MainControl.INSTANCE.onMainUnload();
    }

    @Override
    public void onViewLoad() {
        super.onViewLoad();
        ArrayList<CustomTabEntity> entities = new ArrayList<>();
        entities.add(new TabEntity("书库", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher));
        entities.add(new TabEntity("推荐", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher));
        entities.add(new TabEntity("订单", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher));
        entities.add(new TabEntity("我的", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher));

        TabLayoutUtil.initCommonTab(mCommonTabLayout, entities, new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    mMainUILayout.startIView(mBookSpaceUIView.setIsRightJumpLeft(lastIndex > position), new UIParam().setLaunchMode(UIParam.SINGLE_TOP));
                } else if (position == 1) {
                    mMainUILayout.startIView(mGoodSpaceUIView.setIsRightJumpLeft(lastIndex > position), new UIParam().setLaunchMode(UIParam.SINGLE_TOP));
                } else if (position == 2) {
                    mMainUILayout.startIView(mMyOrderUIView.setIsRightJumpLeft(lastIndex > position), new UIParam().setLaunchMode(UIParam.SINGLE_TOP));
                } else if (position == 3) {
                    mMainUILayout.startIView(mMineUIView.setIsRightJumpLeft(lastIndex > position), new UIParam().setLaunchMode(UIParam.SINGLE_TOP));
                }
                lastIndex = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mMainUILayout.startIView(mBookSpaceUIView, new UIParam(false).setLaunchMode(UIParam.SINGLE_TOP));
    }
}
