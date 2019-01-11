<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>考勤统计管理</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">全勤名单</div>
		<form id="query-form">
			<div class="query-area">
				<table id="query-table" style="width: 100%">
					<tbody>
						<tr>
							<td style="width: 300px">
								<div style="float: right;">
									<a id="normalexportBtn" href="javascript:;" class="btn btn-common btn-export" style="margin-left: 5px">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-export"></span>
											导出
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
						<td class='d-td' width="200">部门</td>
						<td class='d-td' width="50">姓名</td>
						<td class='d-td' width="150">教工号/学号</td>
					</tr>
				</thead>
				<tbody id="placeTBody">
					<c:set var="orgName" value=""/>
					<c:forEach items="${userStatList}" var="userStat" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<tr id="${stat.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
							<td class='d-td'>${rowIndex+1}</td>
							<c:if test="${orgName != userStat.organizationName}">
								<c:set var="orgName" value="${userStat.organizationName}"/>
								<td class='d-td align-left' rowspan="${userStat.orgUserNum}">${userStat.organizationName}</td>
							</c:if>
							<td class='d-td align-left'>${userStat.fullName}</td>
							<td class='d-td align-left'>${userStat.uniqueNo}</td>
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
    	
     	// 导出
        $("#normalexportBtn").click(function(){
        	window.location.href="${ctx}/webapi/meeting/v1/stat-normal-export?beginDate="+beginDate + "&endDate="+endDate  + "&meetingTypeId="+meetingTypeId;
        });
        
        
    });
    
</script>
</body>
