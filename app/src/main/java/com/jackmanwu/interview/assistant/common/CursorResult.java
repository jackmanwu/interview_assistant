package com.jackmanwu.interview.assistant.common;


import java.util.List;

public class CursorResult<T> {
    public static final String END_CURSOR = "0";
    public static final String START_CURSOR = "-1";

    private List<T> list;
    private String cursor;

    public CursorResult() {
    }

    public CursorResult(List<T> list, String cursor) {
        this.list = list;
        this.cursor = cursor;
    }

    public List<T> getList() {
        return list;
    }

    public String getCursor() {
        return cursor;
    }
}
