package com.june.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public interface Constants {

    int FRONT_PAGE_SIZE = 10;
    int DEFAULT_PAGE_SIZE = 10;
    int BACK_PAGE_SIZE = 12;

    String COOKIE_NAME = "PHPSESSID";
    String CURRENT_USER = "currentUser";
    String VALIDATE_CODE_KEY = "validateCode";
    String CSRF_TOKEN = "csrfToken";

    String LIFE_CATEGORY_EN = "life";
    String LIFE_CATEGORY_CN = ConfigManager.getString(Constants.LIFE_CATEGORY_EN);
    String NEWS_CATEGORY_EN = "news";
    String NEWS_CATEGORY_CN = ConfigManager.getString(Constants.NEWS_CATEGORY_EN);

    String DEFAULT_TITLE = ConfigManager.getString("defaultTitle");
    String DEFAULT_KEYWORDS = ConfigManager.getString("defaultKeywords");
    String DEFAULT_DESCRIPTION = ConfigManager.getString("defaultDescription");

    /**
     * lucene索引存放目录
     */
    String LUCENE_INDEX_DIR = ConfigManager.getString("luceneIndexDir");

    /**
     * 文章封面图片存放目录
     */
    String COVER_DIR = ConfigManager.getString("coverDir");

    /**
     * 头像目录
     */
    String AVATAR_DIR = ConfigManager.getString("avatarDir");

    String BASE_PATH = ConfigManager.getString("basePath");

    /**
     * 主动向百度推送链接的URL地址
     */
    String SUBMIT_LINK_TOKEN = ConfigManager.getString("submitLinkToken");
    String SUBMIT_CREATE_LINK_URL = ConfigManager.getString("submitCreateLinkUrl", Constants.SUBMIT_LINK_TOKEN);
    String SUBMIT_UPDATE_LINK_URL = ConfigManager.getString("submitUpdateLinkUrl", Constants.SUBMIT_LINK_TOKEN);
    String SUBMIT_DELETE_LINK_URL = ConfigManager.getString("submitDeleteLinkUrl", Constants.SUBMIT_LINK_TOKEN);

    String CATEGORY_URL_FORMAT = ConfigManager.getString("categoryUrlFormat", Constants.BASE_PATH);
    String ARTICLE_URL_FORMAT = ConfigManager.getString("articleUrlFormat", Constants.BASE_PATH);

}

/**
 * 读取config.properties配置文件
 */
class ConfigManager {

    static String getString(String key, Object... args) {
        return MessageFormat.format(getString(key), args);
    }

    static int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    static String getString(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        return getBundle().getString(key);
    }

    private static ResourceBundle getBundle() {
        return ResourceBundle.getBundle("config");
    }

}
