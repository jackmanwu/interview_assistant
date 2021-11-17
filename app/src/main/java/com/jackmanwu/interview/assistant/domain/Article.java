package com.jackmanwu.interview.assistant.domain;

public class Article {
    private Integer id;
    private Integer cid;
    private String title;
    private String url;

    public Article() {
    }

    public Article(Integer id, Integer cid, String title, String url) {
        this.id = id;
        this.cid = cid;
        this.title = title;
        this.url = url;
    }

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCid() {
        return cid;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
