package com.angcyo.sharebook.http;

import com.angcyo.uiview.net.RSubscriber;

/**
 * Created by angcyo on 2017-05-16.
 */

public abstract class BSub<T> extends RSubscriber<T> {
    @Override
    public void onSucceed(T bean) {
        super.onSucceed(bean);
    }

    @Override
    public void onEnd(boolean isError, boolean isNoNetwork, Throwable e) {
        super.onEnd(isError, isNoNetwork, e);
    }
}
