<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改个人信息</title>
<%@include file="/WEB-INF/admin/head.jsp" %>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)">首页</a></li>
			<li><a href="javascript:void(0)">个人信息管理</a></li>
			<li>修改个人信息</li>
		</ul>
	</div>
	
	<div class="rightinfo">
		<form action="blogger/modifyInfo.do" method="post" onsubmit="return checkInfo()" enctype="multipart/form-data">
			<table class="table">
				<tr>
					<td style="width:50px">昵称<input type="hidden" id="id" name="id" value="${blogger.id}"/></td>
					<td><input id="nickName" name="nickName" type="text" class="scinput" value="${blogger.nickName }"/></td>
				</tr>
				<tr>
					<td style="width:50px">头像</td>
					<td><input type="file" id="img" name="img" onchange="previewImg(this)"/>
					<img id="preview" src="images/avatar/${blogger.imageUrl }" alt="图片" width="100px" height="100px"/></td>
				</tr>
				<tr>
					<td style="width:50px">签名</td>
					<td>
						<input type="text" class="scinput" name="signature" id="sign" value="${blogger.signature }" style="width:400px"/>
					</td>
				</tr>
				<tr>
					<td style="width:50px">个人简介</td>
					<td>
						<textarea id="editor" name="profile" id="profile">${blogger.profile }</textarea>
							<script type="text/javascript">
								//实例化编辑器
								var editor = UE.getEditor('editor',{
									initialFrameWidth: 900, //初始化编辑器宽度,默认1000
							        initialFrameHeight: 300, /* 初始化编辑器宽度,默认320 */
							        elementPathEnabled : false, //是否启用元素路径，默认是显示
							        autoHeightEnabled: false, //是否自动长高,默认true
							        scaleEnabled: false, //是否可以拉伸长高,默认true
							        allowDivTransToP: false
								});
							</script>
					</td>
				</tr>
			</table>
			<input type="submit" class="scbtn" value="保存"/>
			<input type="button" class="scbtn" value="返回" onclick="history.back()" />
		</form>
	</div>
<script type="text/javascript">
	function checkInfo(){
		var nickName = $("#nickName").val();
		var sign = $("#sign").val();
		var content = editor.getContent();
		if(nickName.trim() == ''){
			alert("请填写昵称");
			return false;
		}
		if(sign.trim() == ''){
			alert("请填写签名");
			return false;
		}
		if(content.trim() == ''){
			alert("个人简介不允许为空");
			return false;
		}
		return true;
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
</body>
</html>