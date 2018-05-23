package com.june.model;

import lombok.Data;

public @Data class BlogType {
	
    private Integer typeId;

    private String typeName;

    private Integer orderNo;

    private Integer blogCount; //博客数量
    
}