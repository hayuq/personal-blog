<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
</head>
<body style="background:url(static/images/admin/topbg.gif) repeat-x;">
	<div class="topleft">
    	<a href="main.html" target="_parent"><img src="static/images/admin/logo2.png" title="系统首页" /></a>
    </div>
        
   <!--  <ul class="nav">
    <li><a href="frame/product_main_frame.do" target="centerFrame" class="selected"><img src="static/images/admin/icon01.png" title="工作台" /><h2>商品管理</h2></a></li>
    <li><a href="frame/dept_main_frame.do"  target="centerFrame"><img src="static/images/admin/icon02.png" title="部门管理" /><h2>部门管理</h2></a></li>
    <li><a href="frame/emp_main_frame.do"  target="centerFrame"><img src="static/images/admin/icon03.png" title="用户管理" /><h2>用户管理</h2></a></li>
    <li><a href="frame/order_main_frame.do"  target="centerFrame"><img src="static/images/admin/icon01.png" title="订单管理" /><h2>订单管理</h2></a></li>
    <li><a href="frame/stock_main_frame.do"  target="centerFrame"><img src="static/images/admin/icon04.png" title="库存管理" /><h2>库存管理</h2></a></li>
    <li><a href="frame/financial_main_frame.do" target="centerFrame"><img src="static/images/admin/icon05.png" title="财务统计" /><h2>财务统计</h2></a></li>
    <li><a href="frame/log_main_frame.do"  target="centerFrame"><img src="static/images/admin/icon06.png" title="日志管理" /><h2>日志管理</h2></a></li>
    <li><a href="frame/shop_main_frame.do"  target="centerFrame"><img src="static/images/admin/icon02.png" title="店铺管理" /><h2>店铺管理</h2></a></li>
    </ul> -->
            
    <div class="topright">    
	    <ul>
		    <li><span><img src="static/images/admin/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
		    <li><a href="#">关于</a></li>
		    <li><a href="javascript:void(0)" onclick="window.location.href='admin/login.do'">退出</a></li>
	    </ul>
	     
	    <div class="user">
		   <span> 欢迎您：${user }</span>
		    <!-- <i>消息</i>
		    <b>5</b> -->
	    </div>
    </div>
</body>
</html>