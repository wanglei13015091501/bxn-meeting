<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>考勤统计管理</title>
<head>
</head>
<body width="95%">
<div class="data-table" id="table_fixed_container">
    <div class="head">${queryMap.user.fullName}考勤明细( ${queryMap.beginDate} 至 ${queryMap.endDate} )</div>
    <form id="query-form">
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

$(function () {	
	
	//返回
	$("a.btn-back").click(function(){
		window.location.href="${ctx}/meeting/stat/indexPage?beginDate=${queryMap.beginDate}&endDate=${queryMap.endDate}&meetingTypeId=${queryMap.meetingTypeId}";
	});
});
 
</script>
</body>
