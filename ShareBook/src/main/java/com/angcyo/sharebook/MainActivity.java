package com.angcyo.sharebook;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;

import com.angcyo.sharebook.iview.start.WelcomeUIView;
import com.angcyo.uiview.RCrashHandler;
import com.angcyo.uiview.base.UILayoutActivity;

public class MainActivity extends UILayoutActivity {
    @Override
    protected void onLoadView(Intent intent) {
        //startIView(new MainUIView(), false);
        mLayout.getLayout().setBackgroundColor(Color.WHITE);

        startIView(new WelcomeUIView(), false);

        if (BuildConfig.SHOW_DEBUG) {
            RCrashHandler.checkCrash(mLayout);
        }

        checkPermissions();
    }

    @Override
    protected String[] needPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
        };
    }

    @Override
    protected void onUIBackPressed() {
        super.onUIBackPressed();
        //moveTaskToBack();
    }
}
