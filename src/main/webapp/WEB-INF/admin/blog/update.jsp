<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改博客</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
<style>
	.scbtn { float: left; margin-top: 20px; margin-left: 10px;}
</style>
<!-- ueditor -->
<script>
  //var UEDITOR_HOME_URL = "/Blog/static/ueditor/"; //从项目的根目录开始
</script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	$(function(){
		$(".select").uedSelect({
			width : 180
		});
	});
	
	//发布博客
	function updateBlog(){
		var id = $("#id").val();
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
		
		var params = {"id":id,"title":title,"content":content,"typeId":type,"summary":summary,"keyword":keyword};
		
		$.post("blog/update.do", params, function(result) {
			if (result.success) {
				alert("博客更新成功！");
				$("#title").val('');
				$("#type").val('');
				$("#keyword").val('');
				editor.setContent('');
			} else {
				alert("更新失败");
			}
		}, "json");
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
			<table class="table">
				<tr>
					<td style="width:50px">博客标题<input type="hidden" id="id" value="${blog.id}"/></td>
					<td><input id="title" type="text" class="scinput" style="width:300px" value="${blog.title }"/></td>
				</tr>
				<tr>
					<td style="width:50px">所属类别</td>
					<td>
						<select class="select" id="type" name="type">
							<option value="">--请选择--</option>
							<c:forEach var="blogType" items="${blogTypeList }">
								<option value="${blogType.typeId }" <c:if test="${blog.typeId == blogType.typeId }">selected="seleted"</c:if>>${blogType.typeName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:50px">显示图片</td>
					<td><input type="file" id="image"/></td>
				</tr>
				<tr>
					<td style="width:50px">博客内容</td>
					<td>
						<textarea id="editor">${blog.content }</textarea>
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
								/* var content = "${blog.content}";
								ue.ready(function() {
								    ue.setContent(content);
								}); */
							</script>
					</td>
				</tr>
				<tr>
		   			<td style="width:50px">关键字：</td>
		   			<td><input type="text" id="keyword" name="keyword" value="${blog.keyword }" class="scinput" style="width:300px"/>&nbsp;(多个关键字中间用空格隔开)</td>
		   		</tr>
			</table>
			<input name="" type="button" class="scbtn" value="发布博客" onclick="updateBlog()"/>
			<input name="" type="button" class="scbtn" value="返回" onclick="history.back()" />
	</div>
</body>
</html>