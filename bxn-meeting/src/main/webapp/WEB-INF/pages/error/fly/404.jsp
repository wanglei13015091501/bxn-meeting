<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>     
<title>数字校园平台</title>
<head>
	<META name="decorator" content="fly-show">
</head>
<body>
	<div class="error-page error-404">
		<div class="error-page-message">
			资源不存在
		</div>
		<div class="toolbar-center">
			<a href="${ ctx}" class="btn-control bct-green back"><span class="ico-link"></span>返回首页</a>
		</div>
	</div>
</body>
