package com.angcyo.sharebook;

import com.angcyo.library.utils.L;
import com.angcyo.sharebook.skin.MainSkin;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.Root;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.skin.SkinHelper;

/**
 * Created by angcyo on 2017-03-11.
 */

public class App extends RApplication {
    @Override
    protected void onInit() {
        super.onInit();

        Root.APP_FOLDER = "ShareBook";

        L.init(BuildConfig.DEBUG, Root.APP_FOLDER);

        SkinHelper.init(new MainSkin(this));

        RRetrofit.BASE_URL = "http://xiaoya.ngrok.cc/";
    }
}
