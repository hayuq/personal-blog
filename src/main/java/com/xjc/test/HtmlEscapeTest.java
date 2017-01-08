package com.xjc.test;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringEscapeUtils;

import com.xjc.util.MD5EncodeUtils;

public class HtmlEscapeTest {

	public static void main(String[] args) {
//		System.out.print(StringEscapeUtils.escapeHtml4("<script>alert(123)</script>"));
		System.out.println(MD5EncodeUtils.encrypt("123", "xjc"));
	}
}
