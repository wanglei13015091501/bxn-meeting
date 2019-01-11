<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>缓存管理</title>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">缓存列表</div>
			<table width="95%" class="table" align="center">
				<thead id="cacheEditHead">
					<tr>
						<c:forEach items="${tHeadDatas }" var="tHeadData" varStatus="varStatus">
							<c:set var="tdwidth">10%</c:set>
							<c:choose>
								<c:when test="${varStatus.index eq 0 }">
									<c:set var="tdwidth">30%</c:set>
								</c:when>
								<c:when test="${varStatus.index eq 1 }">
									<c:set var="tdwidth">20%</c:set>
								</c:when>
								<c:when test="${varStatus.index eq 2 }">
									<c:set var="tdwidth">10%</c:set>
								</c:when>
								<c:when test="${varStatus.index eq 3 }">
									<c:set var="tdwidth">10%</c:set>
								</c:when>
								<c:when test="${varStatus.index eq 4 }">
									<c:set var="tdwidth">10%</c:set>
								</c:when>
								<c:when test="${varStatus.last }">
									<c:set var="tdwidth">10%</c:set>
								</c:when>
							</c:choose>
					    	<td class='d-td' width="${tdwidth }">
					    		${tHeadData }
					    	</td>
						</c:forEach>
					</tr>
				</thead>
				<tbody id="cacheTbody">
					<c:forEach items="${tBodyDatas }" var="tBodyData" varStatus="varStatus">	
						<c:set var="tBodyData" value="${tBodyData }" scope="request"></c:set>
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<%@ include file="_cache-info.jsp"%>
					</c:forEach>
				</tbody>
			</table>
		<div class="foot"></div>
	</div>
<script type="text/javascript">
$(function(){
	$(".clear").click(function(e){
		if(confirm("确认要清除吗？")){
			$(e.target).closest("form").submit();
		}
	});
});
</script>
</body>
