package test.newborn.com.demos;

import android.app.Application;
import android.content.Context;

import test.newborn.com.demos.utils.DBManager;

/**
 * Created by xiaochongzi on 17-7-3
 */

public class MyApp extends Application {

    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        DBManager.getInstance();
    }

    public static MyApp getApp() {
        return app;
    }
}
