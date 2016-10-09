package com.xjc.test;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlEscapeTest {

	public static void main(String[] args) {
		System.out.print(StringEscapeUtils.escapeHtml4("<script>alert(123)</script>"));
	}
}
