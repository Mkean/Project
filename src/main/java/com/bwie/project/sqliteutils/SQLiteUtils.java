package com.bwie.project.sqliteutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：王庆
 * 时间：2017/12/27
 */

public class SQLiteUtils {

    private final SQLiteDatabase db;
    private final SQLiteHelper helper;

    public SQLiteUtils(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(String name) {
        db.execSQL("insert into records values(null,?)", new Object[]{name});
    }

    public void del() {
        db.execSQL("delete from records");
    }

    public boolean isHas(String name) {
        Cursor cursor = db.rawQuery("select * from records where name = ?", new String[]{name});
        if (cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    public List<String> selectAll() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from records", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(name);
        }
        return list;
    }

}
