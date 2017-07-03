package com.angcyo.sharebook;

import com.angcyo.library.utils.L;
import com.angcyo.sharebook.http.RxBook;
import com.angcyo.sharebook.skin.MainSkin;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.Root;
import com.angcyo.uiview.net.RRetrofit;
import com.angcyo.uiview.skin.SkinHelper;
import com.angcyo.umeng.UM;
import com.liulishuo.FDown;

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

        RRetrofit.BASE_URL = "http://119.23.221.242/api/";

        RxBook.OK_CODE = 0;

        FDown.init(this, false);

        if (BuildConfig.SHOW_DEBUG) {
//            Takt.stock(this)
//                    .seat(Seat.TOP_CENTER)
//                    .play();

//            BlockCanary.install(this, new AppBlockCanaryContext()).start();
        }

        UM.init(this, BuildConfig.DEBUG);
    }
}
