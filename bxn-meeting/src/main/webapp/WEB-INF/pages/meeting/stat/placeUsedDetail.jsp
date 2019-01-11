<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>会议室使用统计</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head"><span class="t-title">${placeVo.placeName }使用明细（${beginDate } ~ ${endDate }）</span>
			<span class="no-print toolbr" style="">
				<a id="backBtn" href="javascript:void(0)" class="btn btn-common">
					<span class="left"></span>
					<span class="right"><span class="ico ico-back"></span>返回</span>
				</a>
			</span>
		</div>
		<table width="95%" class="table" align="center">
			<thead id="placeHead">
				<tr id="firstTr">
					<td class='d-td' width="60">序号</td>
					<td class='d-td' width="50%">会议名称</td>
					<td class='d-td' width="50%">会议地点</td>
					<td class='d-td' width="300">会议时间</td>
					<td class='d-td' width="90">时长（小时）</td>
					<td class='d-td' width="60">参会人员</td>
				</tr>
			</thead>
			<tbody id="placeTBody">
				<c:forEach items="${attendanceMeetingVos}" var="attendanceMeetingVo" varStatus="varStatus">
					<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
					<tr id="${attendanceMeetingVo.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
						<td class='d-td'>${rowIndex+1}</td>
						<td class='d-td align-left'>${attendanceMeetingVo.meetingName }</td>
						<td class='d-td align-left'>${attendanceMeetingVo.placeName}</td>
						<td class='d-td align-left'>${attendanceMeetingVo.beginTime}-${attendanceMeetingVo.endTime}</td>
						<td class='d-td align-right'>${placeUsedSumMap[attendanceMeetingVo.id]}</td>
						<td class='d-td align-right'>${attendanceMeetingVo.userNum}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="foot"></div>
	</div>
<script type="text/javascript">
$(function(){
   	// 返回
   	$("#backBtn").click(function(){
           var beginDate = "${beginDate }";
           var endDate = "${endDate }";
           window.location.href="${ctx}/meeting/stat/place/list?beginDate="+beginDate + "&endDate="+endDate;
       });
   });
   
</script>
</body>
