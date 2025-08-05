package com.baishun.mcpservercsdnwebflux.model;

/**
 * CSDN发帖响应数据模型
 */
public class CsdnPostResponse {
    private int code;
    private String traceId;
    private Data data;
    private String msg;

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public static class Data {
        private String url;
        private long article_id;
        private String title;
        private String description;
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public long getArticle_id() { return article_id; }
        public void setArticle_id(long article_id) { this.article_id = article_id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
} 