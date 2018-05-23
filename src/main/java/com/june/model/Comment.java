package com.june.model;

import java.util.Date;

import lombok.Data;

public @Data class Comment {
	
    private Integer id;
    private Date commentDate;
    private Boolean isPass;
    private Integer blogId;
    private Blog blog; //评论的博客
    private String userName;
    private Integer userId;
    private User user; //评论的人
    private String content;
    private String reply;
    private Date replyDate;

}