<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>写博客</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
<style>
	.scbtn { float: left; margin-top: 20px; margin-left: 10px;}
</style>
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
	$(function(){
		$(".select").uedSelect({
			width : 180
		});
	});
		
	function checkData(){
		var img = $("#img").val();
		var title = $("#title").val();
		var type = $("#type").val();
		var keyword = $("#keyword").val();
		var content = editor.getContent();
		var summary = editor.getContentTxt().substr(0,300);
		if(title.trim() == ''){
			alert("请填写标题");
			return;
		}
		if(type.trim() == 0){
			alert("请选择类别");
			return;
		}
		if(content.trim() == ''){
			alert("博客内容不允许为空");
			return;
		}
	}
	
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
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="blog/list.do">博客管理</a></li>
			<li>写博客</li>
		</ul>
	</div>
	<div class="rightinfo">
		<form action="blog/add.do" method="post" id="addForm" name="addForm" onsubmit="return checkData()" enctype="multipart/form-data">
			<table class="table">
				<tr>
					<td style="width:50px">博客标题</td>
					<td><input id="title" name="title" type="text" class="scinput" style="width:300px"/></td>
				</tr>
				<tr>
					<td style="width:50px">所属类别</td>
					<td>
						<select class="select" id="type" name="typeId">
							<option value='0'>--请选择--</option>
								<c:forEach var="blogType" items="${blogTypeList }">
									<option value="${blogType.typeId }">${blogType.typeName }</option>
								</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:50px">显示图片</td>
					<td><input type="file" name="img" id="img" onchange="previewImg(this)"/>
					<img id="preview" width="100px" height="100px"/></td>
				</tr>
				<tr>
					<td style="width:50px">博客内容</td>
					<td>
						<textarea id="editor" name="content"></textarea>
							<script type="text/javascript">
								//实例化编辑器
								var editor = UE.getEditor('editor',{
									initialFrameWidth: 900, //初始化编辑器宽度,默认1000
							        initialFrameHeight: 360, /* 初始化编辑器宽度,默认320 */
							        elementPathEnabled : false, //是否启用元素路径，默认是显示
							        autoHeightEnabled: false, //是否自动长高,默认true
							        scaleEnabled: false, //是否可以拉伸长高,默认true
							        allowDivTransToP: false
								});
							</script>
					</td>
				</tr>
				<tr>
		   			<td style="width:50px">关键字：</td>
		   			<td><input type="text" id="keyword" name="keyword" class="scinput" style="width:300px"/>&nbsp;(多个关键字中间用空格隔开)</td>
		   		</tr>
			</table>
			<input name="" type="submit" class="scbtn" value="发布博客" onclick="submitData()"/>
			<input name="" type="button" class="scbtn" value="返回" onclick="history.back()" />
		</form>
	</div>
</body>
</html>