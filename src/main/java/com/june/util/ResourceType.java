package com.june.util;

/**
 * 向百度推送的链接类型，文章、类别
 */
public enum ResourceType {

    /**
     * 文章
     */
    ARTICLE(Constants.ARTICLE_URL_FORMAT),

    /**
     * 类别
     */
    CATEGORY(Constants.CATEGORY_URL_FORMAT);

    private String urlFormat;

    private ResourceType(String urlFormat) {
        this.urlFormat = urlFormat;
    }

    public String urlFormat() {
        return urlFormat;
    }

}
