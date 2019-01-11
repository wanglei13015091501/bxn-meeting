<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>考勤统计管理</title>
<head>
</head>
<style>
.meetingStatus {
	display: inline-block;
	width: 70px;
	height: 26px;
	line-height: 27px;
	text-align: center;
	position: absolute;
	top: 5px;
	right: 38%;
	color: #FFF;
	border-radius:4px;
}

.meetingStatusInit {
	background: #e0d356
}

.meetingStatusProcess {
	background: #00cc00
}

.meetingStatusEnd {
	background: #d3d3d3
}
.statusDisplay{
	background:#198ce1;
	cursor:pointer;
	color: #fff;
	width: 50%;
	height: 60px;
}
.statusStat{
	cursor:pointer;
	width: 50%;
	height: 60px;
	padding-bottom: 5px;
	
}
</style>
<body width="95%">
<div class="data-table" id="table_fixed_container">
    <div class="head">${queryMap.user.fullName}考勤明细( ${queryMap.beginDate} 至 ${queryMap.endDate} )</div>
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
    <table width="98%" cellspacing="0" cellpadding="0" border="0" align="center" style="margin: auto;width:98%;">
		<tr>
			<td  id="userStatusNormal" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="0">${userStatVo.normalNum } </span> 
					<p>正常</p>
				</div>
			</td>
			<td  id="userStatusInit" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="9">${userStatVo.unPunchNum } </span>
					<p>未打卡</p>
				</div>
			</td>
			<td  id="userStatusLate" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="1">${userStatVo.lateNum } </span>
					<p>迟到</p>
				</div>
			</td>
			<td  id="userStatusLeave" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="2">${userStatVo.leaveNum } </span>
					<p>早退</p>
				</div>
			</td>
			<td  id="userStatusLateLeave" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="8">${userStatVo.leaveLateNum } </span>
					<p>迟到/早退</p>
				</div>
			</td>
			<td  id="userStatusAbsenceLeave" align="center">
			    <div class="statusStat">
					<span style="font-size: 16px" id="10">${userStatVo.holidayNum } </span>
					<p>请假</p>
				</div>
			</td>
			<td  id="userStatusAbsence" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="3">${userStatVo.absenceNum } </span>
					<p>缺勤</p>
				</div>
			</td><!-- 缺勤 -->
			<td  id="userStatusMissing" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="4">${userStatVo.missingNum } </span>
					<p>漏卡</p>
				</div>
			</td>
		</tr>
		
	</table>
    <form id="meetingForm">
        <table width="95%" class="table" align="center">
            <thead id="placeHead">
                <tr id="firstTr">
                    <td class='d-td' width="30">序号</td>
                    <td class='d-td' width="200">会议名称</td>
                    <td class='d-td' width="80">会议地点</td>
                    <td class='d-td' width="180">会议时间</td>
                    <td class='d-td' width="50">会议状态</td>
                    <td class='d-td' width="100">签到时间</td>
                    <td class='d-td' width="100">签退时间</td>
                    <td class='d-td' width="50">状态</td>
                    <td class='d-td' width="100">备注</td>
                </tr>
            </thead>
            <tbody id="placeTBody">
            <c:forEach items="${statDetailList}" var="statDetail" varStatus="varStatus">
                <c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
               <tr id="${statDetail.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
			    <td class='d-td'>${rowIndex+1}</td>
			    <td class='d-td align-left'>${statDetail.meetingName}</td>
			    <td class='d-td align-left'>${statDetail.placeName}</td>
			    <td class='d-td align-left'>${statDetail.meetingBeginTime}--${statDetail.meetingEndTime}</td>
			    <td class='d-td align-left'>
			        <c:choose>
		      			<c:when test="${statDetail.meetingStatus eq '0'}"><span>未开始</span></c:when>
						<c:when test="${statDetail.meetingStatus eq '1'}"><span style="color: #e77b00">已开始</span></c:when>
						<c:when test="${statDetail.meetingStatus eq '2'}"><span style="color: #9400e7">已结束</span></c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td>
			    <td class='d-td align-left'>${statDetail.signTime}</td>
			    <td class='d-td align-left'>${statDetail.logoutTime}</td>
			    <td class='d-td align-left'>
			    	<c:choose>
						<c:when test="${statDetail.status eq '0'}"><span>正常</span></c:when>
						<c:when test="${statDetail.status eq '1'}"><span style="color: #e77b00">迟到</span></c:when>
						<c:when test="${statDetail.status eq '2'}"><span style="color: #9400e7">早退</span></c:when>
						<c:when test="${statDetail.status eq '3'}"><span style="color: #e70000">缺勤</span></c:when>
						<c:when test="${statDetail.status eq '4'}"><span style="color: #d2c01f">漏卡</span></c:when>
						<c:when test="${statDetail.status eq '5'}"><span style="color: #ce8678">公假</span></c:when>
						<c:when test="${statDetail.status eq '6'}"><span style="color: #ce8678">私假</span></c:when>
						<c:when test="${statDetail.status eq '7'}"><span style="color: #ce8678">病假</span></c:when>
						<c:when test="${statDetail.status eq '8'}"><span style="color: #789cce">迟到/早退</span></c:when>
						<c:when test="${statDetail.status eq '9'}"><span>未打卡</span></c:when>
	       				<c:otherwise></c:otherwise>
      				</c:choose>
      			</td>
			    <td class='d-td'>${statDetail.description}</td>
				</tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
    <div class="foot"></div>
</div>
<script type="text/javascript">

function GetDateStr(AddDayCount) { 
	var dd = new Date(); 
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
	var y = dd.getFullYear(); 
	var m = dd.getMonth()<9?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期 
	var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前月份的日期  
	return y+"-"+m+"-"+d; 
	}
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
	
	//提取查询参数，初始化搜索框
	var beginDate = getParamValue("beginDate");
	if(beginDate == null || beginDate == ''){
		beginDate = GetDateStr(-7);
	}
	$("#beginDate").val(beginDate);
	
	//提取查询参数，初始化搜索框
	var endDate = getParamValue("endDate");
	if(endDate == null || endDate == ''){
		endDate = GetDateStr(0);
	}
	$("#endDate").val(endDate);
	
});

//查询
$("#queryBtn").click(function(){
    var beginDate = $("#beginDate").val();
    var endDate = $("#endDate").val();
    window.location.href="${ctx}/meeting/stat/userDetailPage?beginDate="+beginDate + "&endDate="+endDate ;
});


</script>
</script>
</body>
