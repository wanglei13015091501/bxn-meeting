<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>    
<title>数字校园平台</title>
<head>
	<META name="decorator" content="mis-no-menu">
	<script type="text/javascript">
	</script>
</head>
<body>
	<div class="error-page error-403">
		<div class="toolbar-center">
			<a href="${ ctx}" class="btn btn-common back">
				<span class="left"></span>
				<span class="right"><span class="ico ico-back"></span>返回首页</span>
			</a>
		</div>
	</div>
</body>