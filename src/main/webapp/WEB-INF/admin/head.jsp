<%@ page language="java" pageEncoding="UTF-8" %>
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
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="static/js/batch_delete.js"></script>
<script type="text/javascript" src="static/js/select-ui.min.js"></script>
<style type="text/css">
	.scbtn{ width:80px;}
	.tablelink img{ transform: scale(0.6); position: relative; top: 7px; margin-left: 3px}
	.tablelink img.detail{ transform: scale(0.4); position: relative; top: 11px}
</style>
<script type="text/javascript">
	$(function() {
		
		$(".select").uedSelect({
			width : 100  
		});
		$('.tablelist tbody tr:odd').addClass('odd');
		
	});
	
	//图片上传前预览
	function previewImg(file) {
		var prevImg = document.getElementById('preview');
		if (file.files && file.files[0]) {
			//创建FileReader对象
			var reader = new FileReader();
			reader.onload = function(evt) {
				prevImg.src = evt.target.result;
			}
			// 读取File对象的数据
			// 当FileReader对象通过readAsDataURL读取数据成功后，就会触发load事件。
			reader.readAsDataURL(file.files[0]);
		} else {
			prevImg.src = file.value;
		}
	}
	
	function showMsg(){
		var msg = $("#msg").val();
		if(msg) alert(msg);
	}
</script>