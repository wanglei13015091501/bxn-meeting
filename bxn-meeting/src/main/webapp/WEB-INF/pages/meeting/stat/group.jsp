<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>考勤统计管理</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">部门统计</div>
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
										<c:forEach items="${meetingTypeVoList}" var="item" varStatus="varStatus">
											<c:choose>
												<c:when test="${varStatus.index eq 0 }">
													<option value="${item.id}" selected="selected">${item.typeName }</option>
												</c:when>
												<c:otherwise>
													<option value="${item.id}">${item.typeName }</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<div style="float: right;">
									<a id="queryBtn" href="javascript:;" class="btn btn-common btn-query" style="margin-left: 5px">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-search"></span>
											查询
										</span>
									</a>
									<a id="normallistBtn" href="javascript:;" class="btn btn-common btn-normallist" style="margin-left: 5px">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-user-group"></span>
											全勤名单
										</span>
									</a>
									<a id="normalexportBtn" href="javascript:;" class="btn btn-common btn-export" style="margin-left: 5px;margin-right: 27px">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-export"></span>
											导出全勤名单
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
						<td class='d-td' width="50">部门</td>
						<td class='d-td' width="50">参会人次</td>
						<td class='d-td' width="50">全勤率</td>
					</tr>
				</thead>
				<tbody id="placeTBody">
					<c:forEach items="${statList}" var="stat" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<tr id="${stat.groupId }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
							<td class='d-td'>${rowIndex+1}</td>
							<td class='d-td align-left' onclick="toStatDetailPage('${stat.groupId}')" style="cursor: pointer;">
								<span style="color: blue">${stat.groupName}</span>
							</td>
							<td class='d-td align-left'>${stat.needJoinNum}</td>
							<td class='d-td align-left'>${stat.normalRates}</td>
						</tr>
					</c:forEach>
						<tr>
							<td class='d-td'></td>
							<td class='d-td align-left'>总计</td>
							<td class='d-td align-left'>${summaryVo.needJoinNum}</td>
							<td class='d-td align-left'>${summaryVo.normalRates}</td>
						</tr>
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
	
	function getDateStr(AddDayCount) { 
		var dd = new Date(); 
		dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
		var y = dd.getFullYear(); 
		var m = dd.getMonth()<9?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期 
		var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前月份的日期  
		return y+"-"+m+"-"+d; 
	}
	
    function quickSearch(){
    	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
    	var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        window.location.href="${ctx}/meeting/stat/group/indexPage?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
    }
    
    function toStatDetailPage(groupId){
    	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
    	var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        window.location.href="${ctx}/meeting/stat/group/detailPage/"+groupId+"?beginDate="+beginDate + "&endDate="+endDate + "&meetingTypeId="+meetingTypeId;
    }
    
    $(function(){
    	var meetingTypeId = getParamValue("meetingTypeId");
    	if(meetingTypeId == null ){
    		meetingTypeId = '';
    	}else{    		
    		$("#meetingTypeForSearch").val(meetingTypeId);
    	}
    	
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
    	
    	//查询
        $("#queryBtn").click(function(){
        	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
            var beginDate = $("#beginDate").val();
            var endDate = $("#endDate").val();
            window.location.href="${ctx}/meeting/stat/group/indexPage?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
        });
    	
    	// 全勤名单
    	$("#normallistBtn").click(function(){
        	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
            var beginDate = $("#beginDate").val();
            var endDate = $("#endDate").val();
            window.location.href="${ctx}/meeting/stat/group/normalPage?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
        });
    	
    	// 导出全勤名单
    	$("#normalexportBtn").click(function(){
        	var meetingTypeId = $("#meetingTypeForSearch option:selected").val();
            var beginDate = $("#beginDate").val();
            var endDate = $("#endDate").val();
            window.location.href="${ctx}/webapi/meeting/v1/stat-normal-export?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
        });
    });
    
</script>
</body>
