<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<link rel="stylesheet" type="text/css" href="static/jquery-easyui-1.3.3/themes/default/easyui.css">
<script type="text/javascript" src="static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<div class="easyui-calendar" style="margin:0 auto;margin-bottom:10px;width:255px;height:195px;border:solid 1px #E5E5E5"></div>
<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/user_icon.png" /> 关于博主
	</div>
	<div class="user_image">
		<img src="static/images/${blogger.imageUrl }" alt="" width="100px" height="100px"/>
	</div>
	<div class="nickName">${blogger.nickName }</div>
	<div class="userSign">(${blogger.signature })</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/byType_icon.png" /> 文章分类
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blogType" items="${blogTypeList }">
				<li>
					<span>
						<a href="index.shtml?type=${blogType.typeId }">${blogType.typeName }</a><font color="#337ab7">(${blogType.blogCount })</font>
					</span>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/byDate_icon.png" /> 文章存档
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blog" items="${dateRankList }">
				<li>
					<span>
						<a href="index.shtml?releaseDate=${blog.releaseDateStr }">${blog.releaseDateStr }</a><font color="#337ab7">(${blog.count })</font>
					</span>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/list_icon.png" /> 阅读排行
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blog" items="${readingRankList }" varStatus="index">
				<li class="rank">
					<span class="number bgcolor${index.count }">${index.count }</span>
					<span>
						<a href="blog/article/${blog.id }.shtml" title="${blog.title }">${blog.title }</a>
					</span>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/comment_icon.png" /> 评论排行
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blog" items="${reviewRankList }" varStatus="index">
				<li class="rank">
					<span class="number bgcolor${index.count }">${index.count }</span>
					<span>
						<a href="blog/article/${blog.id }.shtml" title="${blog.title }">${blog.title }</a>
					</span>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="static/images/link_icon.png" /> 常用网站收藏
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="link" items="${linkList }">
				<li><span><a href="http://${link.linkUrl }" target="_blank">${link.linkName }</a></span></li>
			</c:forEach>
		</ul>
	</div>
</div>
