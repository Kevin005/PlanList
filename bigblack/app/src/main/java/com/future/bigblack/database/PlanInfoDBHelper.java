package com.future.bigblack.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.future.bigblack.bean.PlanInfo;


public class PlanInfoDBHelper {

    private static String TableName = "PlanInfoTable";

    public static void create(SQLiteDatabase paramSQLiteDatabase) {
        if (paramSQLiteDatabase != null)
            paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TableName
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + " content VARCHAR, "
                    + "is_doing INTEGER, " + "level INTEGER, " + "date LONG)");
    }

    public static synchronized void delAll(Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        if (sqlitedatabase != null) {
            sqlitedatabase.delete(TableName, "1=1", null);
        }
        DBHelperUtil.closeDatabase();
    }

    public static synchronized PlanInfo getInfo(int post_id, Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        PlanInfo info = null;
        if (sqlitedatabase != null) {
            Cursor c = sqlitedatabase.rawQuery("SELECT * FROM " + TableName + " where PostId = ? ORDER BY id DESC",
                    new String[]{String.valueOf(post_id)});
            while (c.moveToNext()) {
                info = new PlanInfo();
                info.setId(c.getInt(c.getColumnIndex("id")));
                info.setContent(c.getString(c.getColumnIndex("content")));
                info.setContent(c.getString(c.getColumnIndex("is_doing")));
                info.setContent(c.getString(c.getColumnIndex("level")));
                info.setContent(c.getString(c.getColumnIndex("date")));
                break;
            }
            if (c != null) {
                c.close();
            }
        }
        DBHelperUtil.closeDatabase();
        return info;
    }

    public static synchronized void insertInfo(PlanInfo info, Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        if (sqlitedatabase != null) {
            sqlitedatabase.execSQL("INSERT INTO " + TableName + " VALUES(null,?,?,?,?)", new Object[]{
                    info.getContent(), info.getIs_doing(), info.getLevel(), info.getDate()});
        }
        DBHelperUtil.closeDatabase();
    }

    public static synchronized void deleteInfoById(int psot_id, Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        if (sqlitedatabase != null) {
            sqlitedatabase.execSQL("DELETE FROM " + TableName + " WHERE PostId = ? ", new Object[]{
                    psot_id});
        }
        DBHelperUtil.closeDatabase();
    }

    public static synchronized void updateInfo(PlanInfo info, Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        if (sqlitedatabase != null) {
            ContentValues cv = new ContentValues();
            cv.put("content", info.getContent());
            cv.put("is_doing", info.getIs_doing());
            cv.put("level", info.getLevel());
            cv.put("date", info.getDate());
            sqlitedatabase.update(TableName, cv, "id=? ", new String[]{String.valueOf(info.getId())});
        }
        DBHelperUtil.closeDatabase();
    }

    public static void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("drop table if exists " + TableName);
            create(db);
        }
    }
}
