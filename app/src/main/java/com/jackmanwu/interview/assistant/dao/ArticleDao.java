package com.jackmanwu.interview.assistant.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jackmanwu.interview.assistant.common.CursorResult;
import com.jackmanwu.interview.assistant.common.SQLiteHelper;
import com.jackmanwu.interview.assistant.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao extends BaseDao {
    public ArticleDao(SQLiteHelper sqLiteHelper) {
        super(sqLiteHelper);
    }

    public void save(Article article) {
        try (SQLiteDatabase database = sqLiteHelper.getWritableDatabase()) {
            database.insert(SQLiteHelper.TABLE_ARTICLE, null, convert(article));
        }
    }

    public CursorResult<Article> list(String cursor, int count) {
        List<Article> list = new ArrayList<>();
        try (SQLiteDatabase database = sqLiteHelper.getWritableDatabase();
             @SuppressLint("Recycle") Cursor dbCursor = database.rawQuery("select * from article where id>? order by id asc limit ?", new String[]{cursor, String.valueOf(count)})) {
            while (dbCursor.moveToNext()) {
                int id = dbCursor.getInt(0);
                list.add(new Article(id, dbCursor.getInt(1), dbCursor.getString(2), dbCursor.getString(3)));
            }
        }
        String nextCursor = CursorResult.END_CURSOR;
        if (list.size() > 0) {
            nextCursor = String.valueOf(list.get(list.size() - 1).getId());
        }
        return new CursorResult<>(list, nextCursor);
    }

    private ContentValues convert(Article article) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", article.getCid());
        contentValues.put("title", article.getTitle());
        contentValues.put("url", article.getUrl());
        return contentValues;
    }
}
