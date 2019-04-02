package com.june.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public @Data class Blogger {
	
    private Integer id;
    private String nickName;
    private String signature;
    private String imageUrl;
    private String userName;
    private String password;
    private String profile;
    
}