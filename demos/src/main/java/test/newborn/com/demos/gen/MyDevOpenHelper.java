package test.newborn.com.demos.gen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * Created by xiaochongzi on 17-7-3
 */

public class MyDevOpenHelper extends DaoMaster.OpenHelper {
    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        db.execSQL("alter table DOG add color varchar");
    }
}