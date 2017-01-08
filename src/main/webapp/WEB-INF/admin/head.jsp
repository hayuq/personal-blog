<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<link href="static/css/admin.css" rel="stylesheet"/>
<link href="static/css/select.css" rel="stylesheet"/>
<link href="favicon.ico" rel="SHORTCUT ICON">
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/admin.js"></script>
<script type="text/javascript" src="static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="static/js/batch_delete.js"></script>
<script type="text/javascript" src="static/js/select-ui.min.js"></script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/lang/zh-cn/zh-cn.js"></script>