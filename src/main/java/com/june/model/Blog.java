package com.june.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public @Data class Blog {

    private Integer id;
    private String title;
    private Date releaseDate;
    private Integer review;
    private String keyword;
    private String summary;
    private Integer reading;
    private BlogType blogType;
    private String content;
    private Integer typeId;
    private String image; // 图片URL
    private Integer commentsCount; // 通过审核的评论数，非博客实际属性
    private Integer count; // 博客数量，非博客实际属性
    private String releaseDateStr;// 日期字符串，非博客实际属性
    private String typeName;// 博客类型名称，非博客实际属性

}
