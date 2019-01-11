<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>考勤统计管理</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">${meetingGroup.groupName }历次出勤明细</div>
		<form id="query-form">
			<div class="query-area">
				<table id="query-table" style="width: 100%">
					<tbody>
						<tr>
							<td style="width: 300px">
								<div style="float: right;">
									<a id="normallistBtn" href="javascript:;" class="btn btn-common btn-normallist" style="margin-left: 5px">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-user-group"></span>
											全勤名单
										</span>
									</a>
									<a id="gobackBtn" href="javascript:;" class="btn btn-common btn-goback" style="margin-left: 5px;margin-right: 27px">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-back"></span>
											返回
										</span>
									</a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
			<table width="95%" class="table" align="center">
				<thead id="placeHead">
					<tr id="firstTr">
						<td class='d-td' width="30">序号</td>
						<td class='d-td' width="200">会议名称</td>
						<td class='d-td' width="50">应到</td>
						<td class='d-td' width="50">实到</td>
						<td class='d-td' width="50">全勤</td>
						<td class='d-td' width="50">迟到</td>
						<td class='d-td' width="50">早退</td>
						<td class='d-td' width="50">迟到/早退</td>
						<td class='d-td' width="50">请假</td>
						<td class='d-td' width="50">缺勤</td>
						<td class='d-td' width="50">漏卡</td>
						<td class='d-td' width="50">未打卡</td>
						<td class='d-td' width="50">全勤率</td>
					</tr>
				</thead>
				<tbody id="placeTBody">
					<c:forEach items="${attendanceGroupList}" var="attendanceGroup" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<tr id="${stat.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
							<td class='d-td'>${rowIndex+1}</td>
							<td class='d-td align-left'>${attendanceGroup.meetingName}</td>
							<td class='d-td align-left'>${attendanceGroup.needJoinNum}</td>
							<td class='d-td align-left'>${attendanceGroup.actualNum}</td>
							<td class='d-td align-left'>${attendanceGroup.normalNum}</td>
							<td class='d-td align-left'>${attendanceGroup.lateNum}</td>
							<td class='d-td align-left'>${attendanceGroup.leaveNum}</td>
							<td class='d-td align-left'>${attendanceGroup.leaveLateNum}</td>
							<td class='d-td align-left'>${attendanceGroup.holidayNum}</td>
							<td class='d-td align-left'>${attendanceGroup.absenceNum}</td>
							<td class='d-td align-left'>${attendanceGroup.missingNum}</td>
							<td class='d-td align-left'>${attendanceGroup.unPunchNum}</td>
							<td class='d-td align-left'>${attendanceGroup.normalRates}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<div class="foot"></div>
	</div>
	<script type="text/javascript">

	/**
	 * 从url中获取某一个参数的值
	 * @param name(参数名)
	 * @return result(参数值)
	 */
	function getParamValue(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = decodeURI(window.location.search).substr(1).match(reg); 
		if (r != null) return r[2]; 
		return null; 
	}
	
    $(function(){
    	var meetingTypeId = getParamValue("meetingTypeId");
    	var beginDate = getParamValue("beginDate");
    	var endDate = getParamValue("endDate");
    	
    	// 返回
        $("#gobackBtn").click(function(){
            window.location.href="${ctx}/meeting/stat/group/indexPage?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
        });
    	
     	// 全勤名单
        $("#normallistBtn").click(function(){
        	window.location.href="${ctx}/meeting/stat/group/normalDetailPage/${meetingGroup.id}?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
        });
     	
        
    });
    
</script>
</body>
