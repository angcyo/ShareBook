package com.angcyo.sharebook.iview.login;

import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.angcyo.sharebook.R;
import com.angcyo.sharebook.control.LoginControl;
import com.angcyo.sharebook.http.Action;
import com.angcyo.sharebook.http.BSub;
import com.angcyo.sharebook.http.P;
import com.angcyo.sharebook.http.RxBook;
import com.angcyo.sharebook.http.bean.TokenBean;
import com.angcyo.sharebook.http.service.User;
import com.angcyo.sharebook.iview.base.BaseItemUIView;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.dialog.UILoading;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.utils.T_;
import com.angcyo.uiview.widget.ExEditText;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/09 16:47
 * 修改人员：Robi
 * 修改时间：2017/05/09 16:47
 * 修改备注：
 * Version: 1.0.0
 */
public class LoginUIView extends BaseItemUIView {
    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setTitleString("登录").setShowBackImageView(true);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.view_login;
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem() {
            @Override
            public void onBindView(RBaseViewHolder holder, final int posInData, Item dataBean) {
                TextInputLayout userNameLayout = holder.v(R.id.user_name_layout);
                TextInputLayout userPasswordLayout = holder.v(R.id.user_password_layout);

                userNameLayout.setHint("请输入手机号码");
                userPasswordLayout.setHint("请输入密码(最少6位)");

                final ExEditText userName = (ExEditText) userNameLayout.findViewById(R.id.edit_text);
                final ExEditText userPassword = (ExEditText) userPasswordLayout.findViewById(R.id.edit_text);

                userName.setInputTipTextList(LoginControl.INSTANCE.getPhonesList());

                userName.setIsPhone(true, 11);
                userPassword.setIsPassword(true);

                //登录
                holder.v(R.id.login_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!userName.checkEmpty(true) && !userPassword.checkMinLength(6)) {
                            login(userName.string(), userPassword.string());
                        }
                    }
                });
                holder.v(R.id.register_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceIView(new RegisterUIView().setEnableClipMode(ClipMode.CLIP_BOTH, v));
                    }
                });

                userName.setInputText(LoginControl.INSTANCE.getLastLoginPhone());
            }
        });
    }

    private void login(final String uid, final String pwd) {
        UILoading.show2(mParentILayout);
        add(RRetrofit.create(User.class)
                .login(P.b(Action.LOGIN, "uid:" + uid, "pwd:" + pwd))
                .compose(RxBook.transformer(TokenBean.class))
                .subscribe(new BSub<TokenBean>() {
                    @Override
                    public void onSucceed(TokenBean bean) {
                        super.onSucceed(bean);
                        T_.show("登录成功");
                        finishIView();
                        LoginControl.INSTANCE.setAutoLoginInfo(uid, pwd);
                        LoginControl.INSTANCE.setLoginInfo(uid, bean.getToken());
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        UILoading.hide();
                    }
                }));
    }
}
