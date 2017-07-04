package com.future.bigblack.database;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.future.bigblack.config.ConstSysConfig;

public class DBHelperUtil {
    private static String TAG = DBHelperUtil.class.getName();
    private static SQLiteDatabase db;
    private static OpenHelper mOpenHelper;
    private static int versionCode = 1;

    public synchronized static SQLiteDatabase getDatabase(Context context) {
        if (db == null) {
            if (mOpenHelper == null) {
                try {
                    PackageManager packagemanager = context.getPackageManager();
                    String packageName = context.getPackageName();
                    versionCode = packagemanager.getPackageInfo(packageName, 0).versionCode;
                    mOpenHelper = new OpenHelper(context, ConstSysConfig.DB_NAME, null, versionCode);
                    db = mOpenHelper.getReadableDatabase();//调用此方法是会检查所有数据库是否存在s
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            } else {
                db = mOpenHelper.getWritableDatabase();
            }
            if (context != null) {

            } else {

            }
        }
        return db;
    }

    public static void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
            db = null;
        }
    }
}
