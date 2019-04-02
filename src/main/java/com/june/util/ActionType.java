package com.june.util;

/**
 * 链接推送操作类型：增加、更新、删除
 */
public enum ActionType {
    /**
     * 新增
     */
    CREATE(Constants.SUBMIT_CREATE_LINK_URL),
    /**
     * 更新
     */
    UPDATE(Constants.SUBMIT_UPDATE_LINK_URL),
    /**
     * 删除
     */
    DELETE(Constants.SUBMIT_DELETE_LINK_URL);

    private String url;

    private ActionType(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }

}
