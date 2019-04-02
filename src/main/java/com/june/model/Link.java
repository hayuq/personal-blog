package com.june.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public @Data class Link {
	
    private Integer id;
    private String linkName;
    private String linkUrl;
    private Integer orderNo;

}