package com.angcyo.sharebook.http;

import com.angcyo.uiview.net.RSubscriber;

/**
 * Created by angcyo on 2017-05-16.
 */

public abstract class BSub<T> extends RSubscriber<T> {
    @Override
    public void onNoNetwork() {
        super.onNoNetwork();
    }
}
