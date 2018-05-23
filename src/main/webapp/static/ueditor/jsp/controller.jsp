<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
    request.setCharacterEncoding("utf-8");
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	String result = new ActionEnter(request, rootPath).exec();

	//因为UEditor默认返回的文件路径是绝对的，类似E://upload/image/...
	//需要更改为相对路径，解决文件或图片在线管理的路径问题
	String action = request.getParameter("action");
	if(action != null && ("listfile".equals(action) || "listimage".equals(action))){
	    rootPath = rootPath.replace("\\", "/");
	    result = result.replaceAll(rootPath, "");
	}
	out.write(result);
	
%>