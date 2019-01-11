<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="decorator" content="mis-index-blue">
    <title>会议考勤系统</title>
</head>
<body title="会议考勤系统" text="统一 / 高效 / 协同 / 准确">

	<div class="content clearfix">
		<sec:authorize ifAnyGranted="PERM_MEETING_ADMIN,PERM_MEETING_OWNER">
		<a href="../meeting/attendance/meeting/indexPage" 
		bg="<c:url value='/resources/images/attendance_bg.png'/>" 
		bgHover="<c:url value='/resources/images/attendance_hoverbg.png'/>">会议考勤管理</a>
		</sec:authorize>
		
		<sec:authorize ifAnyGranted="PERM_MEETING_ADMIN">
		<a href="../meeting/stat/indexPage" 
		bg="<c:url value='/resources/images/statistics_bg.png'/>" 
		bgHover="<c:url value='/resources/images/statistics_hoverbg.png'/>">考勤统计</a>
		</sec:authorize>
		<sec:authorize ifNotGranted="PERM_MEETING_ADMIN">
		<a href="../meeting/stat/userDetailPage" 
		bg="<c:url value='/resources/images/statistics_bg.png'/>" 
		bgHover="<c:url value='/resources/images/statistics_hoverbg.png'/>">考勤统计</a>
		</sec:authorize>
		
		<sec:authorize ifAnyGranted="PERM_MEETING_ADMIN">
		<a href="../meeting/rule/indexPage" 
		bg="<c:url value='/resources/images/config_bg.png'/>" 
		bgHover="<c:url value='/resources/images/config_hoverbg.png'/>">考勤设置</a>
		</sec:authorize>
	</div>
</body>
</html>