<%@page import="java.util.Date, com.june.util.DateUtils"%>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
	<footer class="container">
		<div class="row">
			<div class="col-md-12" >
				<div align="center" style="margin: 10px auto;" >
					  Copyright © 2016 - <%= DateUtils.formatDate(new Date(), "yyyy") %> hayuq博客 版权所有
				</div>
				<div class="return_top">
					<a href="javascript:void(0)"></a>
				</div>
			</div>
		</div>
	</footer>
	<script>var ctxPath = "${ctx}/";</script>
	<script src="${ctx}/static/js/jquery.min.js"></script>
	<script src="${ctx}/static/layer/layer.js"></script>
	<script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
	<script src="${ctx}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	<script src="${ctx}/static/js/blog.js"></script>
	