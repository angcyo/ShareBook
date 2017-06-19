package com.angcyo.sharebook.listener;

import android.view.View;

import com.angcyo.sharebook.control.LoginControl;
import com.angcyo.sharebook.iview.login.LoginUIView;
import com.angcyo.uiview.base.UIBaseView;
import com.angcyo.uiview.container.ILayout;
import com.angcyo.uiview.dialog.UIDialog;

/**
 * 需要请求登录的单击事件
 * Created by angcyo on 2017-06-17.
 */

public class LoginClickListener implements View.OnClickListener {
    ILayout mLayout;
    View.OnClickListener mListener;

    public LoginClickListener(ILayout layout, View.OnClickListener listener) {
        mLayout = layout;
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (LoginControl.INSTANCE.isLogin()) {
            mListener.onClick(v);
        } else {
            UIDialog.build()
                    .setDialogTitle("提示")
                    .setDialogContent("此功能, 需要登录.")
                    .setOkText("登录")
                    .setOkClick(new UIDialog.OnDialogClick() {
                        @Override
                        public void onDialogClick(UIDialog dialog, View clickView) {
                            mLayout.startIView(new LoginUIView().setEnableClipMode(UIBaseView.ClipMode.CLIP_BOTH, clickView));
                        }
                    })
                    .showDialog(mLayout)
            ;
        }
    }
}
