<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>关于我 - Promising的博客</title>
	</head>
	<body>
		<div class="container">
			<jsp:include page="/WEB-INF/front/common/top.jsp"/>
			<div class="row">
				<div class="col-md-9">
					<div class="data_list">
						<div class="data_list_title">
							<img src="static/images/about_icon.png"/>
							关于我
						</div>
						<div style="padding: 30px">
						   ${blogger.profile }
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<jsp:include page="/WEB-INF/front/common/right.jsp"/>
				</div>
			</div>
		</div>
	</body>
</html>
