<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-12" >
		<div align="center" style="margin: 10px auto;" >
			  Copyright © 2016-<%=new SimpleDateFormat("yyyy").format(new Date()).toString() %> 
			<a  href="http://www.juncheng1994.cn" target="_blank">Promising博客</a> 版权所有。
		</div>
		<div class="return_top">
				<a href="javascript:void(0)"></a>
			</div>
			<script type="text/javascript">
				//回顶部
				$(function() {
					$(window).scroll(function() {
						var scroll_top = $(document).scrollTop();
						if (scroll_top >= 100) {
							$(".return_top").fadeIn();
						} else {
							$(".return_top").fadeOut();
						}
					});
					$(".return_top").click(function() {
						$("html,body").stop(true).animate({
							scrollTop : "0"
						}, 100);
					});
				});
			</script>
	</div>
</div>