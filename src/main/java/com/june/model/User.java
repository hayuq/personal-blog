package com.june.model;

import lombok.Data;

public @Data class User {
 
	private Integer id;
    private String userName;
    private String password;
    private String email;
    private String website;

}