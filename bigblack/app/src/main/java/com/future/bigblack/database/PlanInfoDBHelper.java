package com.future.bigblack.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.future.bigblack.bean.PlanInfo;

import java.util.ArrayList;
import java.util.List;


public class PlanInfoDBHelper {

    private static String TableName = "PlanInfoTable";

    public static void create(SQLiteDatabase paramSQLiteDatabase) {
        if (paramSQLiteDatabase != null)
            paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TableName
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + " content VARCHAR, "
                    + "is_doing INTEGER, " + "level INTEGER, " + "dateStamp LONG, " + "dateDay VARCHAR)");
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
            Cursor c = sqlitedatabase.rawQuery("SELECT * FROM " + TableName + " where id = ?",
                    new String[]{String.valueOf(post_id)});
            while (c.moveToNext()) {
                info = new PlanInfo();
                info.setId(c.getInt(c.getColumnIndex("id")));
                info.setContent(c.getString(c.getColumnIndex("content")));
                info.setIs_doing(c.getInt(c.getColumnIndex("is_doing")));
                info.setLevel(c.getInt(c.getColumnIndex("level")));
                info.setDateStamp(c.getLong(c.getColumnIndex("dateStamp")));
                info.setDateDay(c.getString(c.getColumnIndex("dateDay")));
                break;
            }
            if (c != null) {
                c.close();
            }
        }
        DBHelperUtil.closeDatabase();
        return info;
    }

    public static synchronized List<PlanInfo> getOneDayInfos(int id, Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        List<PlanInfo> infos = new ArrayList<PlanInfo>();
        if (sqlitedatabase != null) {
            Cursor c = sqlitedatabase.rawQuery("SELECT * FROM " + TableName + " where dateDay = ?",
                    new String[]{String.valueOf(id)});
            while (c.moveToNext()) {
                PlanInfo info = new PlanInfo();
                info.setId(c.getInt(c.getColumnIndex("id")));
                info.setContent(c.getString(c.getColumnIndex("content")));
                info.setIs_doing(c.getInt(c.getColumnIndex("is_doing")));
                info.setLevel(c.getInt(c.getColumnIndex("level")));
                info.setDateStamp(c.getLong(c.getColumnIndex("dateStamp")));
                info.setDateDay(c.getString(c.getColumnIndex("dateDay")));
                infos.add(info);
            }
            if (c != null) {
                c.close();
            }
        }
        DBHelperUtil.closeDatabase();
        return infos;
    }

    public static synchronized void insertInfo(PlanInfo info, Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        if (sqlitedatabase != null) {
            sqlitedatabase.execSQL("INSERT INTO " + TableName + " VALUES(null,?,?,?,?,?)", new Object[]{
                    info.getContent(), info.getIs_doing(), info.getLevel(), info.getDateStamp(), info.getDateDay()});
        }
        DBHelperUtil.closeDatabase();
    }

    public static synchronized void deleteInfoById(int id, Context paramContext) {
        SQLiteDatabase sqlitedatabase = DBHelperUtil.getDatabase(paramContext);
        if (sqlitedatabase != null) {
            sqlitedatabase.execSQL("DELETE FROM " + TableName + " WHERE id = ? ", new Object[]{
                    id});
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
            cv.put("dateStamp", info.getDateStamp());
            cv.put("dateDay", info.getDateDay());
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
