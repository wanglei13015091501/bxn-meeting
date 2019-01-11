<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>考勤统计管理</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">人员统计</div>
		<form id="query-form">
			<div class="query-area">
				<table id="query-table" style="width: 100%">
					<tbody>
						<tr>

							<td style="width: 300px">
								<div style="float: left; margin-left: 27px">
									<b>日期：</b>
									<input type="text" name="beginDate" id="beginDate" required required-tip="必填开始时间" style="width: 150px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate input-fly mr20 ">
									<span>-</span>
									<input type="text" name="endDate" id="endDate" required required-tip="必填结束时间" style="width: 150px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate input-fly">
								</div>
								<div style="float: left; margin-left: 27px">
									<b>会议类型：</b>
									<select id="meetingTypeForSearch" name="meetingTypeId" style="width: 150px;" onchange="quickSearch()">
										<option value="">请选择</option>
										<c:forEach items="${meetingTypeVoList}" var="item" varStatus="i">
											<option value="${item.id}">${item.typeName }</option>
										</c:forEach>
									</select>
								</div>
								<div style="float: right; margin-right: 27px">
									<a id="queryBtn" href="javascript:;" class="btn btn-common btn-query" style="margin-left: 5px">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-search"></span>
											查询
										</span>
									</a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form id="meetingForm">
			<table width="95%" class="table" align="center">
				<thead id="placeHead">
					<tr id="firstTr">
						<td class='d-td' width="30">序号</td>
						<td class='d-td' width="50">部门</td>
						<td class='d-td' width="50">姓名</td>
						<td class='d-td' width="50">应参会次数</td>
						<td class='d-td' width="50">正常</td>
						<td class='d-td' width="50">迟到</td>
						<td class='d-td' width="50">早退</td>
						<td class='d-td' width="50">迟到/早退</td>
						<td class='d-td' width="50">缺勤</td>
						<td class='d-td' width="50">请假</td>
						<td class='d-td' width="50">漏卡</td>
						<td class='d-td' width="50">未打卡</td>
					</tr>
				</thead>
				<tbody id="placeTBody">
					<c:set var="orgName" value=""/>
					<c:forEach items="${statList}" var="stat" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<tr id="${stat.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
							<td class='d-td'>${rowIndex+1}</td>
								<c:if test="${orgName != stat.organizationName}">
									<c:set var="orgName" value="${stat.organizationName}"/>
									<td class='d-td align-left' rowspan="${stat.orgUserNum}">${stat.organizationName}</td>
								</c:if>
								
							<td class='d-td align-left' onclick="toStatDetailPage(${stat.userId})" style="cursor: pointer;">
								<span style="color: blue">${stat.fullName}</span>
							</td>
							<td class='d-td align-left'>${stat.needJoinNum}</td>
							<td class='d-td align-left'>${stat.normalNum}</td>
							<td class='d-td align-left'>${stat.lateNum}</td>
							<td class='d-td align-left'>${stat.leaveNum}</td>
							<td class='d-td align-left'>${stat.leaveLateNum}</td>
							<td class='d-td align-left'>${stat.absenceNum}</td>
							<td class='d-td align-left'>${stat.holidayNum}</td>
							<td class='d-td align-left'>${stat.missingNum}</td>
							<td class='d-td align-left'>${stat.unPunchNum}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
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
	
	var meetingTypeId = getParamValue("meetingTypeId");
	if(meetingTypeId == null ){
		meetingTypeId = '';
	}
	$("#meetingTypeForSearch").val(meetingTypeId);
	
	function getDateStr(AddDayCount) { 
		var dd = new Date(); 
		dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
		var y = dd.getFullYear(); 
		var m = dd.getMonth()<9?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期 
		var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前月份的日期  
		return y+"-"+m+"-"+d; 
		}
    
    $(function(){
    	
    	//提取查询参数，初始化搜索框
    	var beginDate = getParamValue("beginDate");
    	if(beginDate == null || beginDate == ''){
    		beginDate = getDateStr(-7);
    	}
    	$("#beginDate").val(beginDate);
    	
    	//提取查询参数，初始化搜索框
    	var endDate = getParamValue("endDate");
    	if(endDate == null || endDate == ''){
    		endDate = getDateStr(0);
    	}
    	$("#endDate").val(endDate);
    	
    });
    
    //查询
    $("#queryBtn").click(function(){
    	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
    	if(meetingTypeId==''){
    		alert("请选择会议类型");
    		return false;
    	}
        var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        window.location.href="${ctx}/meeting/stat/indexPage?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
    });
    
    function quickSearch(){
    	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
    	var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        window.location.href="${ctx}/meeting/stat/indexPage?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
    }
    function toStatDetailPage(userId){
    	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
    	var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        window.location.href="${ctx}/meeting/stat/detailPage/"+userId+"?beginDate="+beginDate + "&endDate="+endDate + "&meetingTypeId="+meetingTypeId;
    }
</script>
</body>
