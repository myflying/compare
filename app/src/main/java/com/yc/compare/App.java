package com.yc.compare;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.yc.compare.util.AppContextUtil;

/**
 * Created by admin on 2017/3/24.
 */

public class App extends Application {
    public static Context applicationContext;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AppContextUtil.init(this);
        applicationContext = this;
    }
}
