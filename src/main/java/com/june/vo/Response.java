package com.june.vo;

import java.util.HashMap;
import java.util.Map;

public class Response extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;

	private static final int OK = 200;
	private static final int ERROR = 500;
	
	private static final String MSG_OK = "操作成功";
	private static final String MSG_ERROR = "操作失败";
	
	public Response(int code, String msg) {
		this.put("code", code);
		this.put("msg", msg);
	}
	
	public Response(String msg) {
		this(ERROR, msg);
	}
	
	public static Response success() {
		return new Response(OK, MSG_OK);
	}
	
	public static <V> Response success(Map<String, V> map) {
		return success().putMap(map);
	}
	
	public static <V> Response success(String key, V value) {
		return success().append(key, value);
	}
	
	public <V> Response append(String key, V value) {
		this.put(key, value);
		return this;
	}
	
	private <V> Response putMap(Map<String, V> map) {
		this.putAll(map);
		return this;
	}
	
	public static Response error() {
		return error(MSG_ERROR);
	}
	
	public static Response error(String msg) {
		return new Response(ERROR, msg);
	}
	
	public static Response error(int code, String msg) {
		return new Response(code, msg);
	}
	
	public static Response error(ResponseStatus status) {
		return error(status.getCode(), status.getMsg());
	}

}
