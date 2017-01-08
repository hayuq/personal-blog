<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"
	contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>${blog.title }-Promising的博客</title>
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
							<span style="margin: 10px; color: #929292">发布时间：<fmt:formatDate
									value="${blog.releaseDate }" type="date"
									pattern="yyyy-MM-dd HH:mm" /></span> <span
								style="margin: 10px; color: #929292">分类：<a
								href="blog.shtml?type=${blog.blogType.typeName }">${blog.blogType.typeName}</a></span>
							<span style="margin: 10px; color: #929292">浏览(<a
								href="#">${blog.reading}</a>)
							</span> <span style="margin: 10px; color: #929292">评论(<a
								href="blog/articles/${blog.id }.shtml#comment" title="去评论">${commentList.size()}</a>)
							</span>
						</div>
						<div class="sepline"></div>
						<div class="blog_content">${blog.content }</div>
						<script type="text/javascript">SyntaxHighlighter.all();</script>
						
						<div class="blog_keyWord">
							<font><strong>关键词：</strong></font>
							<c:choose>
								<c:when test="${keywords==null}">
											&nbsp;&nbsp;无
										</c:when>
								<c:otherwise>
									<c:forEach var="keyword" items="${keywords }">
										<a href="search.shtml?q=${keyword}" target="_blank"
											style="color: #337ab7">${keyword }</a>&nbsp;&nbsp;
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						
						<div class="bshare-custom">
							分享到： <a title="分享到QQ空间" class="bshare-qzone"></a> <a
								title="分享到新浪微博" class="bshare-sinaminiblog"></a> <a
								title="分享到人人网" class="bshare-renren"></a> <a title="分享到腾讯微博"
								class="bshare-qqmb"></a> <a title="分享到网易微博"
								class="bshare-neteasemb"></a> <a title="更多平台"
								class="bshare-more bshare-more-icon more-style-addthis"></a> <span
								class="BSHARE_COUNT bshare-share-count">0</span>
							<script type="text/javascript" charset="utf-8"
								src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=1&amp;lang=zh"></script>
							<script type="text/javascript" charset="utf-8"
								src="http://static.bshare.cn/b/bshareC0.js"></script>
						</div>
						
						<div class="sepline"></div>
						<div class="blog_lastAndNextPage">${pageCode }</div>
					</div>
				</div>
				<a href="javascript:void(0)" onclick="showCommentForm()"
					class="showCommentForm" id="showCommentForm">我要评论</a>
				<div class="data_list commentForm" style="display: none">
					<div class="data_list_title">
						<img src="static/images/publish_comment_icon.png" /> 发表评论
					</div>
					<div class="publish_comment">
						<form action="comment/save.shtml" method="post">
							<div>
								<input type="hidden" name="blogId" id="blogId"
									value="${blog.id }" />
								<p>
									昵&nbsp;称：<input type="text" id="userName" name="userName" size="30"
										class="txt" /> * 必填项
								</p>
								<p>
									邮&nbsp;箱：<input type="text" id="email" name="email" size="30"
										class="txt" />
								</p>
								<p>
									网&nbsp;址：<input type="text" id="website" name="website" size="30"
										class="txt" />
								</p>
							</div>
							<div class="img">
								<img alt="头像" src="images/cover/${blog.image }" width="50"
									height="50">
							</div>
							<div class="cbox">
								<div class="area-wrap">									
									<textarea class="comment-area" id="content"
										name="content" placeholder="来说两句吧..."></textarea>
								</div>
								<div class="bar-wrap">
									<span class="verCode">
										<input type="text" placeholder="验证码" value="${vCode }"
											name="vCode" id="vCode" size="6" /> <img
											onclick="this.src+='?'" title="换一张" src="vCode.jsp"
											height="20" border="1"> <span class="errorInfo"></span>
									</span>
									<span class="publishButton">
										<input class="btn btn-info" type="button" value="发表评论"
											onclick="submitComment()" />
									</span>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="data_list" name="comment" id="comment">
					<div class="data_list_title">
						<img src="static/images/comment_icon.png" /> 评论列表
					</div>
					<div class="commentDatas">
						<c:choose>
							<c:when test="${empty commentList}">
								还没有评论哦，来做第一个评论的人吧~
							</c:when>
							<c:otherwise>
								<c:forEach var="comment" items="${commentList }"
									varStatus="status">
									<c:choose>
										<c:when test="${status.index < 8 }">
											<div class="comment">
												<div class="img" style="border: none">
													<c:choose>
														<c:when test="${not empty comment.user.website }">
															<a href="http://${comment.user.website }"> <img
																alt="头像" src="images/cover/${blog.image }" width="40"
																height="40" style="border-radius: 50%">
															</a>
														</c:when>
														<c:otherwise>
															<a href="#"><img alt="头像"
																src="images/cover/${blog.image }" width="40" height="40"
																style="border-radius: 50%"></a>
														</c:otherwise>
													</c:choose>
												</div>
												<div>
													<%-- <span style="margin-right: 20px"># ${status.index+1 }楼</span> --%>
													<span> <c:choose>
															<c:when test="${not empty comment.user.website }">
																<a href="http://${comment.user.website }"
																	style="font-weight: normal">${comment.userName }</a>
															</c:when>
															<c:otherwise>
																<a href="#" style="font-weight: normal">${comment.userName }</a>
															</c:otherwise>
														</c:choose>
													</span> <span style="float: right; color: #929292"><fmt:formatDate
															value="${comment.commentDate }" type="date"
															pattern="yyyy-MM-dd HH:mm" /></span>
													<p style="margin-top: 10px; padding-left: 50px">${comment.content }</p>
													<c:if test="${not empty comment.reply }">
														<div class="sepline" style="margin-left: 50px"></div>
														<div style="margin-left: 50px; margin-top: 20px">
															作者回复 <span style="float: right; color: #929292"><fmt:formatDate
																	value="${comment.replyDate }" type="date"
																	pattern="yyyy-MM-dd HH:mm" /></span>
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
															<c:when test="${not empty comment.user.website }">
																<a href="http://${comment.user.website }"> <img
																	alt="头像" src="images/cover/${blog.image }" width="40"
																	height="40" style="border-radius: 50%">
																</a>
															</c:when>
															<c:otherwise>
																<a href="#"><img alt="头像"
																	src="images/cover/${blog.image }" width="40"
																	height="40" style="border-radius: 50%"></a>
															</c:otherwise>
														</c:choose>
													</div>
													<div>
														<%-- <span style="margin-right: 20px"># ${status.index+1 }楼</span> --%>
														<span> <c:choose>
																<c:when test="${not empty comment.user.website }">
																	<a href="http://${comment.user.website }"
																		style="font-weight: normal">${comment.userName }</a>
																</c:when>
																<c:otherwise>
																	<a href="#" style="font-weight: normal">${comment.userName }</a>
																</c:otherwise>
															</c:choose>
														</span> <span style="float: right"><fmt:formatDate
																value="${comment.commentDate }" type="date"
																pattern="yyyy-MM-dd HH:mm" /></span>
													</div>
													<p style="margin-top: 10px; padding-left: 50px">${comment.content }</p>
													<c:if test="${not empty comment.reply }">
														<div class="sepline" style="margin-left: 50px"></div>
														<div style="margin-left: 50px; margin-top: 20px">
															作者回复 <span style="float: right; color: #929292"><fmt:formatDate
																	value="${comment.replyDate }" type="date"
																	pattern="yyyy-MM-dd HH:mm" /></span>
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
					<a href="javascript:void(0)" onclick="showOtherComment()"
						id="showOtherComment" class="showOtherComment"> 查看所有评论(${commentList.size() })</a>
				</c:if>

			</div>
			<div class="col-md-3">
				<jsp:include page="/WEB-INF/front/common/right.jsp" />
			</div>
			<jsp:include page="/WEB-INF/front/common/foot.jsp"/>
		</div>
	</div>
</body>
</html>