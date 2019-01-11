<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议考勤系统</title>
<script src="${ctx }/resources/js/jquery.qrcode.min.js"></script>
<style>

</style>
</head>
<body >
<div class="data-table" id="table_fixed_container">
	<div class="head">${attendanceMeeting.meetingName } - ${meetingGroup.groupName } 考勤明细</div>
	<div class="query-area">
		<table id="query-table" style="width: 100%">
            <tbody>
            <tr>
                <td width="130px">
                	
					<a href="javascript:void(0)" class="btn btn-common btn-back" style="margin-right:27px;float: right">
						<span class="left"></span>
						<span class="right">
							<span class="ico ico-back"></span>返回
						</span>
					</a>
                    <a href="javascript:void(0)" class="btn btn-common btn-export" style="float: right">
					     <span class="left"></span>
					     <span class="right">
					     	<span class="ico ico-export"></span>导出
					     </span>
					</a>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
	
	<table width="95%" class="table" align="center">
		<thead id="placeHead">
			<tr id="firstTr">
				<td class='d-td' width="200">状态</td>
				<td class='d-td' width="150">教工号/学号</td>
				<td class='d-td' width="50">参会人员</td>
				<td class='d-td' width="150">签到时间</td>
				<td class='d-td' width="150">签退时间</td>
				<td class='d-td' width="200">备注</td>
			</tr>
		</thead>
		<tbody id="placeTBody">
		<c:set var="statusName" value=""/>
			<c:forEach items="${attendanceUserList}" var="attendanceUser" varStatus="varStatus">
				<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
				<tr id="${attendanceUser.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
					<c:if test="${statusName != attendanceUser.status}">
						<c:set var="statusName" value="${attendanceUser.status}"/>
						<td class='d-td align-left' rowspan="${attendanceUser.statusUserNum}">
						<c:choose>
							<c:when test="${attendanceUser.status eq '0'}"><span>正常</span></c:when>
							<c:when test="${attendanceUser.status eq '1'}"><span style="color: #e77b00">迟到(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '2'}"><span style="color: #9400e7">早退(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '3'}"><span style="color: #e70000">缺勤(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '4'}"><span style="color: #d2c01f">漏卡(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '5'}"><span style="color: #ce8678">公假(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '6'}"><span style="color: #ce8678">私假(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '7'}"><span style="color: #ce8678">病假(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '8'}"><span style="color: #789cce">迟到/早退(${attendanceUser.statusUserNum})</span></c:when>
							<c:when test="${attendanceUser.status eq '9'}"><span>未打卡</span></c:when>
	        				<c:otherwise></c:otherwise>
      					</c:choose>
						</td>
					</c:if>
					<td class='d-td align-left'>${attendanceUser.uniqueNo}</td>
					<td class='d-td align-left'>${attendanceUser.fullName}</td>
					<td class='d-td align-left'>${attendanceUser.signTime}</td>
					<td class='d-td align-left'>${attendanceUser.logoutTime}</td>
					<td class='d-td align-left'>${attendanceUser.description}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	 <div class="foot"></div>
</div>

<script>
$(function () {	
	
	// 导出
	$("a.btn-export").click(function(){
		window.location.href="${ctx}/webapi/meeting/v1/attendance-export/${meetingGroup.id}?meetingId=${attendanceMeeting.id}";
	});
	 
	// 返回
	$("a.btn-back").click(function(){
		window.location.href="${ctx}/meeting/attendance/groupPage?meetingId=${attendanceMeeting.id}";
	});
	
});
 

 

 </script>

</body>
</html>