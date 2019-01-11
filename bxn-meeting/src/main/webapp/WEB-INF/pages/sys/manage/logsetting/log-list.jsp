<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>日志管理</title>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">日志列表</div>
		<form id="logDownloadSaveForm" class='input-layout-form'>
			<table width="95%" class="table" align="center">
				<thead id="logDownloadEditHead">
					<tr>
						<c:forEach items="${tHeadDatas }" var="tHeadData" varStatus="varStatus">
							<c:set var="tdwidth">15%</c:set>
							<c:choose>
								<c:when test="${varStatus.index eq 0 }">
									<c:set var="tdwidth">50px</c:set>
								</c:when>
								<c:when test="${varStatus.index eq 1 }">
									
								</c:when>
								<c:when test="${varStatus.index eq 2 }">
									<c:set var="tdwidth">50px</c:set>
								</c:when>
								<c:when test="${varStatus.last }">
									<c:set var="tdwidth">90px</c:set>
								</c:when>
							</c:choose>
					    	<td class='d-td' width="${tdwidth }">
					    		${tHeadData }
					    	</td>
						</c:forEach>
					</tr>
				</thead>
				<tbody id="logDownloadTbody">
					<c:forEach items="${tBodyDatas }" var="tBodyData" varStatus="varStatus">
						<c:set var="tBodyData" value="${tBodyData }" scope="request"></c:set>
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<%@include file="_log-info.jsp" %>
					</c:forEach>
				</tbody>
			</table>
		</form>
		<div class="foot"></div>
	</div>
<script type="text/javascript">
$(function(){
	$(".in").click(function(e){
		var filePath = $(e.target).closest("td").find("input[name='filePath']").val();
		var type = $(e.target).closest("td").find("input[name='type']").val();
		window.location.href="${ctx}/systemadmin/manage/logdownload/manage/list?filePath="+filePath+'&type='+type;
	});
	
	$(".download").click(function(e){
		var filePath = $(e.target).closest("td").find("input[name='filePath']").val();
		var type = $(e.target).closest("td").find("input[name='type']").val();
		window.location.href="${ctx}/systemadmin/manage/logdownload/manage/download?filePath="+filePath+'&type='+type;
	});
});
</script>
</body>
