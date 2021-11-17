package com.jackmanwu.interview.assistant.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jackmanwu.interview.assistant.common.CursorResult;
import com.jackmanwu.interview.assistant.common.SQLiteHelper;
import com.jackmanwu.interview.assistant.domain.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends BaseDao {
    public CategoryDao(SQLiteHelper sqLiteHelper) {
        super(sqLiteHelper);
    }

    public void save(Category category) {
        try (SQLiteDatabase database = sqLiteHelper.getWritableDatabase()) {
            database.insert(SQLiteHelper.TABLE_CATEGORY, null, convert(category));
        }
    }

    public CursorResult<Category> list(String cursor, int count) {
        List<Category> list = new ArrayList<>();
        try (SQLiteDatabase database = sqLiteHelper.getWritableDatabase();
             @SuppressLint("Recycle") Cursor dbCursor = database.rawQuery("select * from category where id>? order by id asc limit ?", new String[]{cursor, String.valueOf(count)})) {
            while (dbCursor.moveToNext()) {
                int id = dbCursor.getInt(0);
                list.add(new Category(id, dbCursor.getString(1)));
            }
        }
        String nextCursor = CursorResult.END_CURSOR;
        if (list.size() > 0) {
            nextCursor = String.valueOf(list.get(list.size() - 1).getId());
        }
        return new CursorResult<>(list, nextCursor);
    }

    private ContentValues convert(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", category.getName());
        return contentValues;
    }
}
