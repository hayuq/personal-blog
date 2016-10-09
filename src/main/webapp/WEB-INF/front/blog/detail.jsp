<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>${blog.title } - Promising的博客</title>
<script type="text/javascript">
	function submitData() {
		var userName = $("#userName").val();
		var email = $("#email").val();
		var website = $("#website").val();
		var content = $("#content").val();
		var vCode = $("#vCode").val();
		if (userName.trim() == '') {
			alert("请输入昵称！");
			return;
		}
		/* if (!/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(email)) {
			alert("请输入格式正确的邮箱！");
			return;
		} */
		if (content.trim() == '') {
			alert("请输入评论内容！");
			return;
		}
		if (vCode.trim() == '') {
			alert("请填写验证码！");
			return;
		}
		var params = {
			"content" : content,
			"vCode" : vCode,
			"userName" : userName,
			"email" : email,
			"website" : website,
			"blogId" : $("#blogId").val()
		};
		$.post("comment/save.shtml", params, function(result) {
			if (result.success) {
				alert("评论提交成功，审核通过后显示！");
				$("#userName").val('');
				$("#email").val('');
				$("#website").val('');
				$("#content").val('');
				$("#vCode").val('');
				//window.location.reload();
			} else {
				$("#info").html(result.errorInfo);
			}
		}, "json");
	}
	
	//加载余下的评论
	function showOtherComment(){
		$('.otherComment').show();
		$('#showOtherComment').hide();
	}
	
	//显示评论框
	function showCommentForm(){
		$('.commentForm').show();
		$('#showCommentForm').hide();
	}
</script>
</head>
<body>
	<div class="container">
		<jsp:include page="/WEB-INF/front/common/top.jsp" />
		<div class="row">
			<div class="col-md-9">
				<div class="data_list">
					<div class="data_list_title">
						<img src="static/images/blog_show_icon.png" /> 文章正文
					</div>
					<div>
						<div class="blog_title">
							<h3>${blog.title }</h3>
						</div>
						<div class="blog_info">
							<span class="time"><fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm" /></span> <span class="type">
									<a href="index.shtml?typeId=${blog.blogType.typeId }">${blog.blogType.typeName}</a></span>
							<span class="reading">浏览(<a href="javascript:void(0)">${blog.reading}</a>)
							</span> <span class="review">评论(<a href="javascript:void(0)">${commentList.size()}</a>)
							</span>
						</div>
						<div class="sepline"></div>
						<div class="blog_content">${blog.content }</div>
						<div class="blog_keyWord">
							<font><strong>文章关键词：</strong></font>
							<c:choose>
								<c:when test="${keywords==null}">
											&nbsp;&nbsp;无
										</c:when>
								<c:otherwise>
									<c:forEach var="keyword" items="${keywords }">
										<a href="blog/q.shtml?q=${keyword}" target="_blank"
											style="color: #337ab7">${keyword }</a>&nbsp;&nbsp;
											</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						<!-- <div>
							<div class="bshare-custom" style="margin-bottom:5px">
								分享： <a title="分享到QQ空间" class="bshare-qzone"></a> 
									<a title="分享到新浪微博" class="bshare-sinaminiblog"></a> 
									<a title="分享到人人网" class="bshare-renren"></a> 
									<a title="分享到腾讯微博" class="bshare-qqmb"></a> 
									<a title="分享到网易微博" class="bshare-neteasemb"></a> 
									<a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a> <span
									class="BSHARE_COUNT bshare-share-count">0</span>
							</div>
							<script type="text/javascript" charset="utf-8"
								src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=1&amp;lang=zh"></script>
							<script type="text/javascript" charset="utf-8"
								src="http://static.bshare.cn/b/bshareC0.js"></script>
						</div> -->
						<div class="blog_lastAndNextPage">${pageCode }</div>
					</div>
				</div>
				<div class="data_list">
					<div class="data_list_title">
						<img src="static/images/comment_icon.png" /> 评论列表
					</div>
					<div class="commentDatas">
						<c:choose>
							<c:when test="${empty commentList}">
								暂无评论
							</c:when>
							<c:otherwise>
								<c:forEach var="comment" items="${commentList }" varStatus="status">
									<c:choose>
										<c:when test="${status.index < 8 }">
											<div class="comment">
												<div class="img" style="border:none">
													<img alt="头像" src="static/uploadFiles/20161008/100821202012.jpg" width="40" height="40" style="border-radius:50%">
												</div>
												<div>
													<%-- <span style="margin-right: 20px"># ${status.index+1 }楼</span> --%>
													<span><a href="javascript:void(0)" style="font-weight: normal">${comment.userName }</a></span>
													<span style="float:right;"><fmt:formatDate value="${comment.commentDate }" type="date" pattern="yyyy-MM-dd HH:mm" /></span>
													<p style="margin-top: 10px;padding-left:50px">${comment.content }</p>
													<c:if test="${not empty comment.reply }">
														<div class="sepline" style="margin-left:50px"></div>
														<div style="margin-left:50px;margin-top:20px">
															作者回复
															<span style="float:right;"><fmt:formatDate value="${comment.replyDate }" type="date" pattern="yyyy-MM-dd HH:mm" /></span>
															<p style="margin-top: 10px;color:#256EB1">${comment.reply }</p>
														</div>
													</c:if>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="otherComment">
												<div class="comment">
													<div class="img" style="border:none">
														<img alt="头像" src="static/uploadFiles/20161008/100821202012.jpg" width="40" height="40" style="border-radius:50%">
													</div>
													<div>
														<%-- <span style="margin-right: 20px"># ${status.index+1 }楼</span> --%>
														<span><a href="javascript:void(0)"
															style="font-weight: normal">${comment.userName }</a></span>
														<span style="float: right"><fmt:formatDate
																value="${comment.commentDate }" type="date"
																pattern="yyyy-MM-dd HH:mm" /></span>
													</div>
													<p style="margin-top: 10px;padding-left:50px">${comment.content }</p>
												</div>
											</div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:if test="${commentList.size() > 8}">
					<a href="javascript:void(0)" onclick="showOtherComment()" id="showOtherComment">继续加载所有评论(${commentList.size() })</a>
				</c:if>
				<a href="javascript:void(0)" onclick="showCommentForm()" id="showCommentForm">我要评论</a>
				<div class="data_list commentForm" style="display:none">
					<div class="data_list_title">
						<img src="static/images/publish_comment_icon.png" /> 发表评论
					</div>
					<div class="publish_comment">
						<form action="comment/save.shtml" method="post">
							<div>
								<input type="hidden" name="blogId" id="blogId" value="${blog.id }" />
								<p>
									昵称：<input type="text" id="userName" name="userName" size="30" class="txt" /> *必填项
								</p>
								<p>
									邮箱：<input type="text" id="email" name="email" size="30" class="txt" />
								</p>
								<p>
									网址：<input type="text" id="website" name="website" size="30" class="txt" />
								</p>
							</div>
							<div class="img">
								<img alt="头像" src="static/uploadFiles/20161008/100821202012.jpg" width="50" height="50">
							</div>
							<div style="margin-left:20px">
								<textarea style="width: 92%" rows="5" id="content" name="content" placeholder="来说两句吧..."></textarea>
							</div>
							<div class="verCode">
								<input type="text" placeholder="验证码" value="${vCode }" name="vCode" id="vCode" size="8"/> 
								<img onclick="this.src+='?'" title="换一张" src="vCode.jsp" width="60" height="20" border="1">
								<span id="info" style="margin-left:20px;color:red"></span>
							</div>
							<div class="publishButton">
								<input class="btn btn-info" type="button" value="发表评论"
									onclick="submitData()" />
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<jsp:include page="/WEB-INF/front/common/right.jsp" />
			</div>
		</div>
	</div>
	<div class="return_top">
		<a href="javascript:void(0)"></a>
	</div>
</body>
</html>