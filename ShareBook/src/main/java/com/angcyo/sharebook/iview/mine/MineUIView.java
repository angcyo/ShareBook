package com.angcyo.sharebook.iview.mine;

import android.os.Bundle;
import android.view.View;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.control.LoginControl;
import com.angcyo.sharebook.iview.base.BaseItemUIView;
import com.angcyo.sharebook.iview.login.LoginUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.github.utilcode.utils.ClipboardUtils;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.widget.ItemInfoLayout;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by angcyo on 2017-03-11.
 */

public class MineUIView extends BaseItemUIView {

    @Override
    protected String getTitleString() {
        return "我";
    }

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setShowBackImageView(false);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_user_top_layout;
        }
        return R.layout.item_info_layout;
    }

    @Override
    public void onViewShow(Bundle bundle) {
        super.onViewShow(bundle);
        mExBaseAdapter.notifyItemChanged(0);
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                holder.tv(R.id.text_view).setText("点击登录");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mParentILayout.startIView(new LoginUIView().setEnableClipMode(ClipMode.CLIP_BOTH, v));
                    }
                });

                if (LoginControl.INSTANCE.isLogin()) {
                    holder.tv(R.id.text_view).setText(LoginControl.INSTANCE.getUid());
                }
            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                ItemInfoLayout itemLayout = holder.v(R.id.item_info_layout);
                itemLayout.setItemText("扫一扫");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mParentILayout.startIView(new SBScanUIView(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                ClipboardUtils.copyText(s);
                                T_.show(s + " 已复制");
                            }
                        }));
                    }
                });

            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                ItemInfoLayout itemLayout = holder.v(R.id.item_info_layout);
                itemLayout.setItemText("测试条目:" + posInData);
            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                ItemInfoLayout itemLayout = holder.v(R.id.item_info_layout);
                itemLayout.setItemText("测试条目:" + posInData);
            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP_LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                ItemInfoLayout itemLayout = holder.v(R.id.item_info_layout);
                itemLayout.setItemText("测试条目:" + posInData);
            }
        });
        items.add(new SingleItem(SingleItem.Type.TOP_LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                ItemInfoLayout itemLayout = holder.v(R.id.item_info_layout);
                itemLayout.setItemText("测试条目:" + posInData);
            }
        });
    }
}
