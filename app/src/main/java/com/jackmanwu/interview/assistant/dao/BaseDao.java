package com.jackmanwu.interview.assistant.dao;

import com.jackmanwu.interview.assistant.common.SQLiteHelper;

public class BaseDao {
    public SQLiteHelper sqLiteHelper;

    public BaseDao(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
    }
}
