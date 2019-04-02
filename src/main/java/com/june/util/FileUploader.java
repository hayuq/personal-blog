package com.june.util;

import java.awt.Image;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.june.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Component
public @Slf4j final class FileUploader implements InitializingBean {

    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (taskExecutor == null) {
            log.info("taskExecutor注入失败！！");
        } else {
            log.info("taskExecutor注入成功！！");
        }
        taskExecutor.setCorePoolSize(NCPU << 1);
    }

    /**
     * 上传图片到指定目录下，按日期存放
     * @param imageFile 图片文件
     * @param rootPath 保存根目录
     * @return 日期+文件名(yyyyMMdd/xxxxxxxxxx.xxx)格式或null
     */
    public String uploadImage(MultipartFile imageFile, String rootPath) {
        if (imageFile == null) {
            throw new ServiceException("请选择需要上传的图片");
        }
        try {
            // 判断是否是图片
            Image image = ImageIO.read(imageFile.getInputStream());
            if (image == null || image.getWidth(null) < 0 || image.getHeight(null) < 0) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new ServiceException("只允许上传图片");
        }
        String yyyyMMdd = DateUtils.getCurrentDateStr();
        String fileName = upload(imageFile, rootPath + yyyyMMdd);
        return fileName == null ? null : (yyyyMMdd + "/" + fileName);
    }

    /**
     * 上传文件，以当前时间戳为文件名保存
     * @param file MultipartFile对象
     * @param savePath 保存目录
     * @return 新文件名或null
     */
    public String upload(MultipartFile file, String savePath) {
        if (file == null) {
            throw new ServiceException("请选择需要上传的文件");
        }
        // 获取文件后缀名
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(fileExtension)) {
            return null;
        }
        // 生成新文件名
        String fileName = System.currentTimeMillis() + "." + fileExtension;
        // 异步上传
        taskExecutor.execute(() -> handleFileUpload(file, savePath, fileName));
        return fileName;
    }

    private void handleFileUpload(MultipartFile file, String savePath, String fileName) {
        log.info("开始上传文件{}至{}目录下...", fileName, savePath);
        try (InputStream in = file.getInputStream()) {
            Path dir = Paths.get(savePath);
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
            Files.copy(in, Paths.get(savePath, fileName));
            log.info("上传文件成功...");
        } catch (Exception ex) {
            log.info("上传文件失败", ex.getCause());
        }
    }
}
