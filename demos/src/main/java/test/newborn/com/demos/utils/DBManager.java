package test.newborn.com.demos.utils;

import test.newborn.com.demos.MyApp;
import test.newborn.com.demos.gen.DaoMaster;
import test.newborn.com.demos.gen.DaoSession;
import test.newborn.com.demos.gen.MyDevOpenHelper;

/**
 * Created by xiaochongzi on 17-7-3
 */

public class DBManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static DBManager INSTANCE;
    private String DB_NAME = "shopping_guide";

    private DBManager() {
        init();
    }

    /**
     * 对外唯一实例的接口
     *
     * @return
     */
    public static DBManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }

    /**
     * 初始化数据
     */
    private void init() {
        MyDevOpenHelper devOpenHelper = new MyDevOpenHelper(MyApp.getApp(), DB_NAME);
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
