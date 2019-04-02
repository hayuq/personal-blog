<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.net.URLEncoder"%>
<%@page language="java" pageEncoding="utf-8" isELIgnored="false" contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>${blog.title } - ${blogger.nickName }的博客</title>
	<%@include file="/WEB-INF/front/head.jspf" %>
	<link href="${ctx}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/front/common/top.jsp" />
	<main class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="data_list">
					<div class="data_list_title">
						<i class="glyphicon glyphicon-th-large"></i> 文章正文
					</div>
					<div>
						<c:choose>
						<c:when test="${blog eq null }">
					    <div align="center" style="padding-top: 20px">未找到该篇文章</div>
						</c:when>
						<c:otherwise>
						<div class="blog_title">
							<h3>${blog.title }</h3>
						</div>
						<div class="blog_info">
							<span style="margin: 10px; color: #929292">
							发布时间：<fmt:formatDate value="${blog.releaseDate }" pattern="yyyy-MM-dd HH:mm" /></span> <span
								style="margin: 10px; color: #929292">分类：<a
								href="${ctx}/blog.shtml?cat=${blog.blogType.typeId }">${blog.blogType.typeName}</a>
							</span>
							<span style="margin: 10px; color: #929292">
							浏览(<a href="#">${blog.reading}</a>)
							</span> 
							<span style="margin: 10px; color: #929292">
							评论(<a href="${ctx}/blog/articles/${blog.id }.shtml#comment" title="去评论">${blog.commentsCount }</a>)
							</span>
						</div>
						<div class="blog_content">${blog.content }</div>
						<div class="blog_keyWord">
							<strong>关键词：</strong>
							<c:choose>
								<c:when test="${empty keywords }">
									&nbsp;&nbsp;无
								</c:when>
								<c:otherwise>
									<c:forEach var="keyword" items="${keywords }">
										<a href="${ctx}/search.shtml?q=${keyword.value }" target="_blank" style="color: #337ab7">
										  ${keyword.key }
										</a>&nbsp;&nbsp;
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="bdsharebuttonbox">
							<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
								class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a
								href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a
								href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a
								href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a
								href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
						</div>
						<div class="blog_lastAndNextPage">
						    <p>上一篇：
						    <c:choose>
                                <c:when test="${prev eq null }">
                                                                没有了
                                </c:when>
                                <c:otherwise>
                                <a href="${ctx}/blog/articles/${prev.id }.shtml" target="_blank">${prev.title }</a>
                                </c:otherwise>
                            </c:choose>
						    </p>
						    <p>下一篇：
						    <c:choose>
                                <c:when test="${next eq null }">
                                                                没有了
                                </c:when>
                                <c:otherwise>
                                <a href="${ctx}/blog/articles/${next.id }.shtml" target="_blank">${next.title }</a>
                                </c:otherwise>
                            </c:choose>
						    </p>
						</div>
			            </c:otherwise>
			            </c:choose>
					</div>
				</div>
                <div id="cyReward" role="cylabs" data-use="reward" align="center"></div>
				<c:if test="${blog ne null }">
				<a href="javascript:void(0)" onclick="showCommentForm()"
					class="showCommentForm" id="showCommentForm">我要评论</a>
				<div class="data_list commentForm" style="display: none">
					<div class="data_list_title">
						<i class="glyphicon glyphicon-comment"></i> 发表评论
					</div>
					<div class="publish_comment">
						<form id="commentForm" class="form-inline">
							<div>
								<input type="hidden" name="blogId" id="blogId" value="${blog.id }" />
								<p>昵&nbsp;称：<input class="form-control" type="text" id="userName" name="userName" size="40" class="txt" /> * 必填项</p>
								<p>邮&nbsp;箱：<input class="form-control" type="text" id="email" name="email" size="40" class="txt" /> * 必填项</p>
								<p>网&nbsp;址：<input class="form-control" type="text" id="website" name="website" size="40" class="txt" /> (请以http://或https://开头)</p>
							</div>
							<div class="img">
								<img alt="头像" src="${ctx}/images/cover/${blog.image }" width="50" height="50">
							</div>
							<div class="cbox">
								<div class="area-wrap">								
									<textarea class="comment-area form-control" id="content" name="content" placeholder="来说两句吧..."></textarea>
								</div>
								<div class="bar-wrap">
									<span class="verCode">
										<input class="form-control" type="text" placeholder="验证码" value="${vCode }" name="vCode" id="vCode" size="4" maxlength="4">
										<img id="codeImg" style="cursor:pointer" onclick='this.src="${ctx}/vCode.jpg?t=" + $.now();' title="点击换一张" src="${ctx}/vCode.jpg" border="1">
										<span class="errorInfo" style="display:none"></span>
									</span>
									<span class="publishButton">
										<a class="btn btn-info" onclick="submitComment(this)">发表评论</a>
									</span>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="data_list" name="comment" id="comment">
					<div class="data_list_title">
						评论列表
					</div>
					<div class="commentDatas">
						<c:choose>
						<c:when test="${empty commentList}">
							还没有评论哦，来做第一个评论的人吧~
						</c:when>
						<c:otherwise>
						<c:forEach var="comment" items="${commentList }" varStatus="status">
						<c:choose>
						<c:when test="${status.index < 8 }">
						<div class="comment">
							<div class="img" style="border: none">
								<c:choose>
								<c:when test="${not empty comment.user.website }">
								<a href="${comment.user.website }"> 
								   <img alt="头像" src="${ctx}/images/cover/${blog.image }" width="40" height="40" style="border-radius: 50%">
								</a>
								</c:when>
								<c:otherwise>
								<img alt="头像" src="${ctx}/images/cover/${blog.image }" width="40" height="40" style="border-radius: 50%">
								</c:otherwise>
								</c:choose>
							</div>
							<div>
								<span> 
								<c:choose>
									<c:when test="${not empty comment.user.website }">
									<a href="${comment.user.website }" style="font-weight: normal">${comment.userName }</a>
									</c:when>
									<c:otherwise>
									<span style="font-weight: normal;color:#337ab7" >${comment.userName }</span>
									</c:otherwise>
								</c:choose>
								</span> 
                                <%-- <span style="margin-left: 20px"># ${status.index+1 }楼</span> --%>
								<span style="float: right; color: #929292">
								   <fmt:formatDate value="${comment.commentDate }" pattern="yyyy-MM-dd HH:mm" />
								</span>
								<p style="margin-top: 10px; padding-left: 50px">${comment.content }</p>
								<c:if test="${not empty comment.reply }">
								<div class="sepline" style="margin-left: 50px"></div>
								<div style="margin-left: 50px; margin-top: 20px">
									作者回复：
									<span style="float: right; color: #929292">
									    <fmt:formatDate value="${comment.replyDate }" pattern="yyyy-MM-dd HH:mm" />
									</span>
									<p style="margin-top: 10px; color: #256EB1">${comment.reply }</p>
								</div>
								</c:if>
							</div>
						</div>
						</c:when>
						<c:otherwise>
						<div class="otherComment">
							<div class="comment">
								<div class="img" style="border: none">
									<c:choose>
										<c:when test="${comment.user.website ne null }">
											<a href="${comment.user.website }"> 
											    <img alt="头像" src="${ctx}/images/cover/${blog.image }" width="40" height="40" style="border-radius: 50%">
											</a>
										</c:when>
										<c:otherwise>
											<img alt="头像" src="${ctx}/images/cover/${blog.image }" width="40" height="40" style="border-radius: 50%">
										</c:otherwise>
									</c:choose>
								</div>
								<div>
									<span> 
									<c:choose>
										<c:when test="${comment.user.website ne null }">
											<a href="${comment.user.website }" style="font-weight: normal">${comment.userName }</a>
										</c:when>
										<c:otherwise>
											<a href="#" style="font-weight: normal">${comment.userName }</a>
										</c:otherwise>
									</c:choose>
									</span> 
									<%-- <span style="margin-left: 20px"># ${status.index+1 }楼</span> --%>
									<span style="float: right">
									    <fmt:formatDate value="${comment.commentDate }" pattern="yyyy-MM-dd HH:mm" />
									</span>
								</div>
								<p style="margin-top: 10px; padding-left: 50px">${comment.content }</p>
								<c:if test="${comment.reply ne null }">
									<div class="sepline" style="margin-left: 50px"></div>
									<div style="margin-left: 50px; margin-top: 20px">
										作者回复：
										<span style="float: right; color: #929292">
										    <fmt:formatDate value="${comment.replyDate }" pattern="yyyy-MM-dd HH:mm" />
										</span>
										<p style="margin-top: 10px; color: #256EB1">${comment.reply }</p>
									</div>
								</c:if>
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
				<a href="javascript:void(0)" onclick="showOtherComment()" id="showOtherComment" class="showOtherComment"> 
				        查看所有评论(${blog.commentsCount })
				</a>
				</c:if>
				</c:if>
			</div>
			<jsp:include page="/WEB-INF/front/common/right.jsp"/>
		</div>
	</main>
	<jsp:include page="/WEB-INF/front/common/foot.jsp"/>
	<script src="${ctx}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
	<script charset="utf-8" src="https://changyan.itc.cn/js/lib/jquery.js"></script>
    <script charset="utf-8" src="https://changyan.sohu.com/js/changyan.labs.https.js?appid=cytjoxrLl"></script>
	<script>
		$('.cy-reward-txt').html('请作者喝一杯咖啡吧!');
		SyntaxHighlighter.all();
	</script>
</body>
</html>