package com.angcyo.sharebook;

import android.Manifest;

import com.angcyo.sharebook.iview.MainUIView;
import com.angcyo.uiview.base.UILayoutActivity;

public class MainActivity extends UILayoutActivity {
    @Override
    protected void onLoadView() {
        startIView(new MainUIView(), false);
    }

    @Override
    protected String[] needPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
        };
    }
}
