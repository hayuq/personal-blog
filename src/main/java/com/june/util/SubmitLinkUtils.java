package com.june.util;

import java.text.MessageFormat;
import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步向百度主动推送链接
 */
@Component
public @Slf4j final class SubmitLinkUtils {

    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolTaskExecutor taskExecutor;

    @Resource
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        SubmitLinkUtils.taskExecutor = taskExecutor;
        SubmitLinkUtils.taskExecutor.setCorePoolSize(SubmitLinkUtils.NCPU + 1);
    }

    /**
     * 向百度推送新的链接
     * @param resourceType 资源类型
     * @param params 参数
     */
    public static void createLink(ResourceType resourceType, Integer... params) {
        submit(resourceType, ActionType.CREATE, params);
    }

    /**
     * 更新向百度推送的链接
     * @param resourceType 资源类型
     * @param params 参数
     */
    public static void updateLink(ResourceType resourceType, Integer... params) {
        submit(resourceType, ActionType.UPDATE, params);
    }

    /**
     * 删除向百度推送的链接
     * @param resourceType 资源类型
     * @param params 参数
     */
    public static void deleteLink(ResourceType resourceType, Integer... params) {
        submit(resourceType, ActionType.DELETE, params);
    }

    /**
     * 操作百度链接的推送行为
     * @param resourceType 资源类型
     * @param actionType 操作类型
     * @param params 参数
     */
    public static void submit(ResourceType resourceType, ActionType actionType, Integer... params) {
        if (ObjectUtils.isEmpty(params)) {
            SubmitLinkUtils.log.info("参数为空, 取消链接推送");
            return;
        }
        String urlFormat = resourceType.urlFormat();
        StringBuilder linkUrls = new StringBuilder();
        Arrays.stream(params).forEach(param -> linkUrls.append(MessageFormat.format(urlFormat + "\r\n", param)));

        // doSubmit(actionType.url(), linkUrls.toString());
    }

}
