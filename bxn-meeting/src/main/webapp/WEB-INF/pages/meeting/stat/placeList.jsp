<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>考勤统计管理</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">会议室使用统计</div>
		<form id="query-form">
			<div class="query-area">
				<table class="table" id="query-table" width="95%">
					<tbody>
						<tr>
							<td style="width: 368px">
								<b>日期：</b>
								<input type="text" name="beginDate" id="beginDate" value="${beginDate }" required required-tip="必填开始时间" style="width: 150px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate input-fly mr20 ">
								<span>-</span>
								<input type="text" name="endDate" id="endDate" value="${endDate }" required required-tip="必填结束时间" style="width: 150px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate input-fly">
							</td>
							<td class='align-left'>
								<a id="queryBtn" href="javascript:;" class="btn btn-common btn-query" style="margin-left: 5px">
									<span class="left"></span>
									<span class="right">
										<span class="ico ico-search"></span>
										查询
									</span>
								</a>
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
						<td class='d-td' width="50">会议室</td>
						<td class='d-td' width="50">使用次数</td>
						<td class='d-td' width="50">总时长（小时）</td>
						<td class='d-td' width="50">平均时长（小时）</td>
					</tr>
				</thead>
				<tbody id="placeTBody">
					<c:forEach items="${places}" var="place" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<tr id="${place.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
							<td class='d-td'>${rowIndex+1}</td>
							<td class='d-td align-left' onclick="toStatDetailPage('${place.id}')" style="cursor: pointer;">
								<span style="color: blue">${place.placeName}</span>
							</td>
							<td class='d-td align-left'>${placeUsedCountMap[place.id]}</td>
							<td class='d-td align-left'>${placeUsedSumMap[place.id]}</td>
							<td class='d-td align-left'>${placeUsedSumMap[place.id]/placeUsedCountMap[place.id]}</td>
						</tr>
					</c:forEach>
						<tr>
							<td class='d-td'></td>
							<td class='d-td align-left'>总计</td>
							<td class='d-td align-left'>${totalCount}</td>
							<td class='d-td align-left'>${totalHours}</td>
							<td class='d-td align-left'>${totalHours/totalCount}</td>
						</tr>
				</tbody>
			</table>
		<div class="foot"></div>
	</div>
	<script type="text/javascript">
    function toStatDetailPage(placeId){
    	var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        window.location.href="${ctx}/meeting/stat/place/usedDetail?beginDate="+beginDate + "&endDate="+endDate + "&placeId="+placeId;
    }
    
    $(function(){
    	//查询
        $("#queryBtn").click(function(){
        	var beginDate = $("#beginDate").val();
            var endDate = $("#endDate").val();
            window.location.href="${ctx}/meeting/stat/place/list?beginDate="+beginDate + "&endDate="+endDate;
        });
    });
    
</script>
</body>
