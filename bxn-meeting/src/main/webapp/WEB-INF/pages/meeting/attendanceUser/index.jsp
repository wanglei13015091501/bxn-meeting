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
.meetingStatus {
	display: inline-block;
    height: 24px;
    line-height: 24px;
    text-align: center;
    margin-left: 10px;
    color: #fff;
    border-radius: 4px;
    font-size: 12px;
    padding: 0px 6px;
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

#createTime{
	margin-left: 15px;
}

@media (max-width: 1210px){
	#createTime{
		margin-left: 0;
	}
}

.btn.disabled{
	pointer-events: none;
	color: #ddd;
}
.qr-content{
	display: none;
}

.qr-box{
	 width:100%; 
	 height:100%; 
	 background: rgba(0, 0, 0, 0.7);
	 position:fixed; 
	 left:0px; 
	 top:0px; 
	 display:none; 
	 z-index:1000; 
 }
 .qr-box .close{
 	position: absolute;
 	top: 10px;
 	right: 10px;
    z-index: 9999;
    min-width: 40px;
    height: 40px;
    line-height: 40px;
    background: rgb(0, 0, 0);
    opacity: 0.4;
    text-decoration: none;
    font-size: 24px;
    color: #fff;
    text-align: center;
    -webkit-webkit-border-radius: 32px;
    -moz-webkit-border-radius: 32px;
    -ms-webkit-border-radius: 32px;
    -o-webkit-border-radius: 32px;
    border-radius: 32px;
    -webkit-transtion: all 0.3s;
    -moz-transtion: all 0.3s;
    -ms-transtion: all 0.3s;
    -o-transtion: all 0.3s;
    transtion: all 0.3s;
 }
 .qr-box .close:hover,
 .qr-box .close:focus{
 	opacity: 1;
    -webkit-transform: scale(1.5);
    -ms-transform: scale(1.5);
    -o-transform: scale(1.5);
    transform: scale(1.5);
}

.qr-box .qr-code{
	position: absolute;
	top: 40px;
	left: 50%;
	border: 4px solid #b76969;
}
.qr-box .cover{
	position: absolute;
	top: 50%;
	left: 50%;
	z-index: 2;
}
.qr-box .cover span{
	position: absolute;
    display: block;
    background:#fff;
}
.qr-box .cover span:nth-child(1){
	left: 3%;
	top: 3%;
}
.qr-box .cover span:nth-child(2){
	right: 3%;
	top: 3%;
}
.qr-box .cover span:nth-child(3){
	left: 3%;
	bottom: 3%;
}


.qr-box .qr-btn{
	position: absolute;
    bottom: 20px;
    display: block;
    width: 100%;
    text-align: center;
}
.qr-box .qr-btn .expand{
	margin-right: 30px;
}
.qr-box .qr-btn span.right > span{
	font-size: 20px;
	margin-right: 4px;
}


.batch-wrapper{
	text-align: right;
    width: 96%;
    margin: 0 auto;
    margin-top: 30px;
}

.batchModifyDialog tr{
	height: 40px;
}
</style>
</head>
<body >
<div class="data-table">
	<div style="position: relative;margin-bottom: 10px">
		<div style="text-align: center;font-size: 14px;font-weight: bold">
			<span id="meetingName">${attendanceMeeting.meetingName }<span class="meetingStatus"></span></span>
		</div>
		
		
		<!-- 会议通知弹出框 -->
		<div class="notice-dialog" style="display:none;padding: 10px;width: 280px;height: 120px;" align="center">
			<textarea rows="8" cols="40" class="text-notice" maxlength="130" style="width: 280px"></textarea>
			<p style="text-align:right"><span class="noticeMaxlength"></span><span>/130</span></p>
		</div>
		
		<div style="position: absolute;top: 40px;right: 2%;">
			<a href="javascript:void(0)" class="btn btn-common" id="qrSigned" >
				<span class="left"></span>
				<span class="right">
					<span class="ico ico-user-login-limit"></span>二维码签到
				</span>
			</a>
			<a href="javascript:;" class="btn btn-common btn-notice" onclick="$('.notice-dialog').show().bxnDialog('show')">
				<span class="left"></span>
				<span class="right">
					<span class="ico ico-sms-send"></span>会议通知
				</span>
			</a>
			<a href="javascript:void(0)" class="btn btn-common btn-refresh" id="refreshAttendance">
				<span class="left"></span>
				<span class="right">
					<span class="ico ico-refresh"></span>重置考勤
				</span>
			</a>
			<a href="javascript:void(0)" class="btn btn-common btn-config">
			     <span class="left"></span>
			     <span class="right">
			     	<span class="ico ico-config"></span>编辑
			     </span>
			</a>
			<a href="javascript:void(0)" class="btn btn-common btn-copy">
			     <span class="left"></span>
			     <span class="right">
			     	<span class="ico ico-query2"></span>复制
			     </span>
			</a>
			<a href="javascript:void(0)" class="btn btn-common btn-remove">
			     <span class="left"></span>
			     <span class="right">
			     	<span class="ico ico-remove"></span>删除
			     </span>
			</a>
			<a href="javascript:void(0)" class="btn btn-common btn-back" id="backMeeting" >
				<span class="left"></span>
				<span class="right">
					<span class="ico ico-back"></span>返回
				</span>
			</a>
		</div>
		
		<div style="top:5px;margin-left: 10px;width: 280px;text-align: left;">
			<span id="creator">${attendanceMeeting.creatorName }</span>
			<span id="createTime" style="">${attendanceMeeting.createTime }</span>
			<span id="minSignTime" style="display: none">${attendanceMeeting.signTime }</span>
			<span id="maxLogoutTime" style="display: none">${attendanceMeeting.logoutTime }</span>
		</div>
		
		<div style="width: 100%;height: 1px;border-bottom-width: 2px;border-bottom-color: #0093e7;border-bottom-style: solid;"  ></div>
		
	</div>
		
	<table width="98%" cellspacing="0" cellpadding="0" border="0" align="center" style="margin: auto;width:98%;border-top: 1px solid rgb(206, 206, 206);">
		<tr>
			<td width="12"  class="label">地点：</td>
			<td width="80" class="content" id="placeName">
				${placeName}
			</td>
			<td width="12"  class="label">时间：</td>
			<td width="140" class="content" id="meetingTime">
				${attendanceMeeting.beginTime} 至 ${attendanceMeeting.endTime }
			</td>
		</tr>
		<tr>
			<td width="12"  class="label">备注：</td>
			<td width="" class="content" id="meetingDescription">
				${attendanceMeeting.description }
			</td>
		</tr>
		
	</table>
	
	<div class="data-table">
	<div id="attendanceUserTab" style="width:100%;margin:auto;" class="bxn-tab">
	
		<div class="tab-head">
			<div class="title-area"></div>
			<div class="tab-area">
				<a href="#userTab" class="tab-item active" id="tab-area-user">考勤明细</a><!-- active为默认选中项 -->
				<a href="#groupTab" class="tab-item" id="tab-area-group">部门统计</a>
			</div>        
		</div>
		
		<div class="tab-content" id="userTab">
		
	<table width="98%" cellspacing="0" cellpadding="0" border="0" align="center" style="margin: auto;width:98%;">
		<tr>
			<td  id="userStatusNormal" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="0">${attendanceStatusMap.status0 } </span> 
					<p>正常</p>
				</div>
			</td>
			<td  id="userStatusInit" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="9">${attendanceStatusMap.status9 } </span>
					<p>未打卡</p>
				</div>
			</td>
			<td  id="userStatusLate" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="1">${attendanceStatusMap.status1 } </span>
					<p>迟到</p>
				</div>
			</td>
			<td  id="userStatusLeave" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="2">${attendanceStatusMap.status2 } </span>
					<p>早退</p>
				</div>
			</td>
			<td  id="userStatusLateLeave" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="8">${attendanceStatusMap.status8 } </span>
					<p>迟到/早退</p>
				</div>
			</td>
			<td  id="userStatusAbsenceLeave" align="center">
			    <div class="statusStat">
					<span style="font-size: 16px" id="10">${attendanceStatusMap.status10 } </span>
					<p>请假</p>
				</div>
			</td>
			<td  id="userStatusAbsence" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="3">${attendanceStatusMap.status3 } </span>
					<p>缺勤</p>
				</div>
			</td><!-- 缺勤 -->
			<td  id="userStatusMissing" align="center">
				<div class="statusStat">
					<span style="font-size: 16px" id="4">${attendanceStatusMap.status4 } </span>
					<p>漏卡</p>
				</div>
			</td>
		</tr>
		
	</table>
	
	<form id="attendanceForm" class="input-layout-form"  style="clear:both">
		<!-- 批量重置、编辑 -->
		<div class="batch-wrapper">
			<a href="javascript:void(0)" class="btn btn-common disabled" id="batchRefresh">
				<span class="left"></span>
				<span class="right">
					<span class="ico ico-refresh"></span>批量重置
				</span>
			</a>
			<a href="javascript:void(0)" class="btn btn-common disabled" id="batchmodify">
				<span class="left"></span>
				<span class="right">
					<span class="ico ico-config"></span>批量编辑
				</span>
			</a>
		</div>
		<table id="attendanceTable" width="96%" class="table" align="center" cellspacing="0" cellpadding="0" style=" margin-bottom:30px;">
			<thead>
				<tr>      		 
					<td  class="d-td" width="35px">
						<input type="checkbox" class="checkAll">
					</td>
	       			<td  class="d-td" width="35px">序号</td>
	       			<td  class="d-td" width="80px">教工号/学号</td>
	       			<td  class="d-td" width="60px">参会人员</td>   
	       			<td  class="d-td" width="140px">签到时间</td>
	       			<td  class="d-td" width="140px">签退时间</td>  
	       			<td  class="d-td" width="65px">状态</td>   
	       			<td  class="d-td" width="180px">备注</td>
	       			<td  class="d-td" width="150px">操作</td>
	       		</tr>
	      	</thead>
	     	
	     	<tbody>
				<c:forEach items="${attendanceMapList}" var="attendance" varStatus="i" >
					<tr>
						<td class="d-td"><input type="checkbox" name="attendanceId" data-user-name="${attendance.userName}" value="${attendance.id}"></td>
						<td class="d-td">${i.index+1 }</td>
						<td class="d-td">${attendance.uniqueNo }</td>
						<td class="d-td" style="display: none" id="userName${attendance.id}">${attendance.userName}</td>
						<td class="d-td">${attendance.fullName }</td>
						<td class="d-td" id="signTime${attendance.id}">
							<span id="signTimeSpan${attendance.id }" >${attendance.signTime }</span>
							
							<input id="signTimeInput${attendance.id}" type="text" name="modifySignTime" value="${attendance.signTime}" class='Wdate' style="width:80%; display: none" 
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
						</td>
						<td class="d-td" id="logoutTime${attendance.id}">
							<span id="logoutTimeSpan${attendance.id}"> ${attendance.logoutTime }</span>
							
							<input id="logoutTimeInput${attendance.id}" type="text" name="modifyLogoutTime" value="${attendance.logoutTime}" class='Wdate' style="width:80%;display: none" 
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
							
						</td>
						<td class="d-td statusValue" style="display: none" id="status${attendance.id}">${attendance.status}</td>
						<td class="d-td status">
						<div id="statusDiv${attendance.id}">
							<c:choose>
								<c:when test="${attendance.status eq '0'}"><span>正常</span></c:when>
								<c:when test="${attendance.status eq '1'}"><span style="color: #e77b00">迟到</span></c:when>
								<c:when test="${attendance.status eq '2'}"><span style="color: #9400e7">早退</span></c:when>
								<c:when test="${attendance.status eq '3'}"><span style="color: #e70000">缺勤</span></c:when>
								<c:when test="${attendance.status eq '4'}"><span style="color: #d2c01f">漏卡</span></c:when>
								<c:when test="${attendance.status eq '5'}"><span style="color: #ce8678">公假</span></c:when>
								<c:when test="${attendance.status eq '6'}"><span style="color: #ce8678">私假</span></c:when>
								<c:when test="${attendance.status eq '7'}"><span style="color: #ce8678">病假</span></c:when>
								<c:when test="${attendance.status eq '8'}"><span style="color: #789cce">迟到/早退</span></c:when>
								<c:when test="${attendance.status eq '9'}"><span>未打卡</span></c:when>
		        				<c:otherwise></c:otherwise>
	      					</c:choose>
						</div>
						<div style="display: none" id="selectStatusDiv${attendance.id}">
							<select id="selectStatus${attendance.id}" style="width:80px" name="status">
								<option value="0" <c:if test="${attendance.status eq '0'}"> selected="selected" </c:if> >正常</option>
								<option value="1" <c:if test="${attendance.status eq '1'}"> selected="selected" </c:if> >迟到</option>
								<option value="2" <c:if test="${attendance.status eq '2'}"> selected="selected" </c:if> >早退</option>
								<option value="3" <c:if test="${attendance.status eq '3'}"> selected="selected" </c:if> >缺勤</option>
								<option value="4" <c:if test="${attendance.status eq '4'}"> selected="selected" </c:if> >漏卡</option>
								<option value="5" <c:if test="${attendance.status eq '5'}"> selected="selected" </c:if> >公假</option>
								<option value="6" <c:if test="${attendance.status eq '6'}"> selected="selected" </c:if> >私假</option>
								<option value="7" <c:if test="${attendance.status eq '7'}"> selected="selected" </c:if> >病假</option>
								<option value="8" <c:if test="${attendance.status eq '8'}"> selected="selected" </c:if> >迟到/早退</option>
								<option value="9" <c:if test="${attendance.status eq '9'}"> selected="selected" </c:if> >未打卡</option>
							</select>
						</div>
	      				
	     				</td>
	     				<td class="d-td" id="description${attendance.id}">
	     					<span id="descriptionSpan${attendance.id}">${attendance.description }</span>
	     					<input id="descriptionInput${attendance.id}" name="description" value="${attendance.description }" 
	     					       style="display: none;width: 200px;height: 20px" maxlength="100" >
	     				</td>
	     				<td class="d-td operation${attendance.id}" data-id="${attendance.id}" >
	     					<a href="javascript:;"  class="btn btn-inline edit" >
		     					<span class="left"></span>
		                   		<span class="right"><span class="ico ico-modify"></span>编辑</span>
		                	</a>
		                	<a href="javascript:;"  class="btn btn-inline save" style="display: none">
		     					<span class="left"></span>
		                   		<span class="right"><span class="ico ico-save"></span>保存</span>
			                </a>
			                <a href="javascript:;"  class="btn btn-inline cancel" style="display: none" >
		     					<span class="left"></span>
		                   		<span class="right"><span class="ico ico-cancel"></span>取消</span>
			                </a>
			                <a href="javascript:;"  class="btn btn-inline reset" >
		     					<span class="left"></span>
		                   		<span class="right"><span class="ico ico-refresh"></span>重置</span>
		                	</a>
			                
	     				</td>
						
					</tr>
				</c:forEach>
	     	</tbody>
		</table>
	</form>
		
		</div>
		<div class="tab-content" id="groupTab">
			
		</div>
	</div>
	</div>
</div>
<!-- 二维码内容 -->
<div class="qr-content">
	<a href="javascript:void(0);" class="close">×</a>
	<div class="cover">
		<span></span>
		<span></span>
		<span></span>
	</div>
	<div class="qr-code">
			
	</div>
	<div class="qr-btn">
		<a href="javascript:void(0)" class="btn btn-common expand">
		     <span class="left"></span>
		     <span class="right">
		     	<span class="">+</span>调大尺寸
		     </span>
		</a>
		<a href="javascript:void(0)" class="btn btn-common compress">
			<span class="left"></span>
			<span class="right">
				<span class="">-</span>调小尺寸
			</span>
		</a>
	</div>
</div>

<!-- 批量编辑弹出框 -->
<div class="batchModifyDialog" style="display:none">
	<table width="80%" class="table" align="center" cellspacing="0" cellpadding="0" style="margin:0 auto">
		<tr>
			<td class="d-td" width="20%">签到时间</td>
			<td class="d-td">
				<input id="signTimeInput" type="text" name="modifySignTime" value="" class='Wdate' style="width:80%;" 
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			</td>
		</tr>
		<tr>
			<td class="d-td">签退时间</td>
			<td class="d-td">
				<input id="logoutTimeInput" type="text" name="modifyLogoutTime" value="" class='Wdate' style="width:80%;" 
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			</td>
		</tr>
		<tr>
			<td class="d-td">状态</td>
			<td class="d-td">
				<select id="selectStatus" style="width:80px" name="status">
					<option value="0">正常</option>
					<option value="1">迟到</option>
					<option value="2">早退</option>
					<option value="3">缺勤</option>
					<option value="4">漏卡</option>
					<option value="5">公假</option>
					<option value="6">私假</option>
					<option value="7">病假</option>
					<option value="8">迟到/早退</option>
					<option value="9">未打卡</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="d-td">备注</td>
			<td class="d-td">
				<textarea id="descriptionInput" name="description" value="" style="width: 265px;height: 76px" maxlength="100" ></textarea>
			</td>
		</tr>
	</table>
</div>


<script>
var statusArray = new Array();

$(function () {	
	
	$("#attendanceUserTab").bxnTab({
		onShow:function(tab){
			var tabId = tab[0].id;
			if(tabId == 'groupTab'){
				window.location.href="${ctx}/meeting/attendance/groupPage?meetingId=${attendanceMeeting.id}";
			}	
		}	
	});
	
	if("${qrcodeSwitch}" == "1"){ // 二维码开关，0-启用，1-关闭
		$('#qrSigned').hide();
	}
	
	// 判断会议状态 meetingStatus
	if('${attendanceMeeting.status}' == '1'){
		$(".meetingStatus").addClass("meetingStatusProcess");
		$(".meetingStatus").html("进行中");
	}else if('${attendanceMeeting.status}' == '2'){
		$(".meetingStatus").addClass("meetingStatusEnd");
		$(".meetingStatus").html("已结束");
	}else{
		$(".meetingStatus").addClass("meetingStatusInit");
		$(".meetingStatus").html("未开始");
	}

	var timestamp = new Date().getTime();

	if(timestamp >= Date.parse(new Date('${attendanceMeeting.signTime }'.replace(/\-/g, "\/"))) && timestamp <= Date.parse(new Date('${attendanceMeeting.logoutTime }'.replace(/\-/g, "\/")))){
		$('#qrSigned').removeClass('disabled');
	}else{
		$('#qrSigned').addClass('disabled');
	}
	
	//初始化对话框
	$(".notice-dialog").bxnDialog({
		title:"会议通知",
		ico:"ico-save",
		buttons:[{
			title:"确定",
			ico:"ico-save",
			onClick:function(){
				var textNotice = $(".text-notice").val();
				if(textNotice.trim() == ''){
					alert("会议通知内容不能为空");
					return;
				}
				$.bxn.confirm({msg:"是否要发送会议通知。",width:"200",height:"80"},function(){
					var loading = $.bxn.tools.showFullPageLoading("正在发送会议通知，请稍候...");
					$.ajax({
						type: "POST",
						url:"${ctx}/webapi/meeting/v1/attendance-notices?meetingId=${attendanceMeeting.id}&content="+textNotice,
						async: true,
						error: function(request) {
						},
						success: function(msg) {
							$.bxn.tools.showRemindInfo(msg);
							alert("发送成功",function(){
								$(".notice-dialog").bxnDialog("close");
							});
							
						},
						complete : function() {
							$.bxn.tools.closeFullPageLoading(loading);
						}
						
					});
				});
			}
		},{
			title:"关闭",
			ico:"ico-cancel1",
			onClick:function(){
				$(".notice-dialog").bxnDialog("close");
			}
		}],
		height:200//自定义控件属性，更多属性参照bxn.control.js  $.bxnDialog.defaults属性声明
	});
	
	var textNotice = "您好，${attendanceMeeting.beginTime}--${attendanceMeeting.endTime }，在${placeName}，召开${attendanceMeeting.meetingName }。";
	var desc = "${attendanceMeeting.description}";
	if(desc != ""){
		textNotice = textNotice + desc;
	}
	if(textNotice.length > 130){
		textNotice = textNotice.substring(0,130);
	}
	$(".text-notice").val(textNotice);
	
	$(".noticeMaxlength").text(textNotice.length);

	
	//显示二维码弹出框
	$('a#qrSigned').click(function(){

		var height = $(window).height();
		// 二维码最大高度，适应屏幕的最高高度
		var maxHeight = height-160;
		// 二维码的最小高度： 300*300
		var minHeight = 300;
		// 二维码当前的高度,设置为 最大高度和最小高度的中间值
		var currentHeight = (minHeight + maxHeight)/2;

		// 获取更新二维码的时间间隔（秒）
		var time = 3;
		// 二维码的有效显示时间
		var validTime = 0.2;

		// 是否开启防拍功能
		var openCover = true;
		
		// 构建二维码弹框
		var qrBox = $('<div class="qr-box"></div>');
		
		$(qrBox).html($('.qr-content').html());

		var codeBox = $(qrBox).find('.qr-code');

		// 文本内容
		var qrUrl;
		
		getQrText();

		// 定时器执行，3秒获取一次
		var getQrtext = setInterval(getQrText,time * 1000);

		// 获取需生成二维码的文本内容
		function getQrText(){
			
			$.ajax({
	            url : '${ctx}/webapi/meeting/v1/attendance-qrCodes',
	            type : 'get',
	            async: false,
	            data : {'meetingId': '${attendanceMeeting.id}'},
	            dataType : 'json',
	            beforeSend : function() {
	                
	            },
	            success : function(result) {
	            	qrUrl = result.qrUrl;
	            }
			})

			timestamp = new Date().getTime();
			// 生成二维码
			createCode();
		}

		function createCode(){
			// 显示弹窗前设置二维码的位置
			$(codeBox).css({
				'marginLeft': -currentHeight/2,
				'top': '50%',
				'marginTop': -currentHeight/2-20
			}).html("");

			
			$(codeBox).qrcode({
				//render: "table", //table方式,兼容不支持canvas的浏览器
				width: currentHeight,
				height: currentHeight,
			    text: qrUrl,
			    correctLevel:1
			});

			if(openCover){
				// 防拍遮罩层
				$(qrBox).find('.cover').css({
					'width': currentHeight,
					'height': currentHeight,
					'marginLeft': -currentHeight/2+4,
					'marginTop': -currentHeight/2-16
				});

				$(qrBox).find('.cover span').css({
					'width': currentHeight*0.07,
			   		'height': currentHeight*0.07

				})
					

				// 0.2秒的扫描窗口
				// 获取到链接时的时间戳
				var beginTime = timestamp;

				// 当前二维码有效截止时间前0.2s时时间戳
				var endTime = (beginTime + time * 1000)-(validTime * 1000);

				// 防拍层显示时间
				var t1 = parseInt((Math.random()*(endTime-beginTime) + beginTime), 10);
				var t2 = t1 + (validTime * 1000);

				var showCover = setInterval(function(){
					var t = new Date().getTime();

					if(t>= t1 && t <=t2){
						$(qrBox).find('.cover').hide();

					}else{
						$(qrBox).find('.cover').show();

						if(t > t2){
							clearInterval(showCover);
						}
					}
				},1);
			}
		}
		
		// 二维码弹框显示
		$(qrBox).appendTo('body').css('zIndex','10001').fadeIn(300);

		// 关闭二维码弹框
		$(qrBox).find('a.close').click(function(){
			// 清除获取地址的定时器
			clearInterval(getQrtext);
			// 移除二维码弹框
			$('.qr-box').remove();
			
		})

		// 调大尺寸
		$(qrBox).find('.expand').click(function(){

			if(currentHeight < maxHeight){
				currentHeight = (currentHeight + 40) >= maxHeight ? maxHeight : (currentHeight + 40);
				createCode();
			}
		})
		// 调小尺寸
		$(qrBox).find('.compress').click(function(){

			if(currentHeight > minHeight){
				currentHeight = (currentHeight - 40) <= minHeight ? minHeight : (currentHeight - 40);
				
				createCode();
			}

		})
		
	})
	
 });
 
 // 发动通知监听
 $(".text-notice").keyup(function(){
	 $(".noticeMaxlength").text($(".text-notice").val().length);
 });
 
 
 // 刷新考勤
 $("a.btn-refresh").click(function(){
	 $.bxn.confirm({msg:"是否要重置考勤？重置后将重新计算考勤。",width:"350",height:"80"},function(){
		 var loading = $.bxn.tools.showFullPageLoading("正在重置考勤，请稍候...");
			var	url = "${ctx}/webapi/meeting/v1/attendance-refresh?meetingId=${attendanceMeeting.id}" ;
			$.ajax({
				type: "POST",
				url:url,
				async: true,
				error: function(request) {
				},
				success: function(msg) {
					$.bxn.tools.closeFullPageLoading(loading);
					$.bxn.tools.showRemindInfo(msg);
					alert("刷新成功",function(){
						window.location.href = "${ctx}/meeting/attendance/indexPage?meetingId=${attendanceMeeting.id}";
					});
					
				}
			});
     },function(){
     });
	
	 
 });
 
 // 编辑会议
 $("a.btn-config").click(function(){
	 window.location.href="${ctx}/meeting/attendance/meeting/editPage/${attendanceMeeting.id}"; 
 });
 
 // 复制会议
 $("a.btn-copy").click(function(){
	 $.bxn.confirm({msg:"是否要复制会议？",width:"350",height:"80"},function(){
         var data = "_method=PUT";
 		$.post('${ctx}/webapi/meeting/v1/meeting-copy/${attendanceMeeting.id}',data,
 		function(msg) {
 			$.bxn.tools.showRemindInfo(msg);
 			 window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
 		});
     },function(){
     });
 });
 
 //删除会议
 $("a.btn-remove").click(function(){
	 $.bxn.confirm({msg:"是否删除会议？",width:"350",height:"80"},function(){
        var data = "_method=DELETE";
 		$.post('${ctx}/webapi/meeting/v1/meetings/${attendanceMeeting.id}',data,
 		function(msg) {
 			$.bxn.tools.showRemindInfo(msg);
 			 window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
 		});
 		
     },function(){
     });
 });
 
 // 返回
 $("a.btn-back").click(function(){
	 window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
 });


//初始化批量编辑
 var batchModifyDialog = $(".batchModifyDialog").bxnDialog({
 	title:"批量编辑",//对话框标题
 	width:500,//对话框宽度
 	height:210,//对话框高度
 	buttons:[{
 		title:"保存",//保存按钮文字
 		ico:"ico-yes",
 		onClick:function(){

 			var checkedIds = [];
 	 		// 取选中的考勤记录
 			var checkboxChecked = $("input[type='checkbox'][name='attendanceId']:checked");
 			if(checkboxChecked.length == 0){
				return false;
 	 		}else{
 	 			checkboxChecked.each(function(index,item){
 	 				checkedIds.push($(item).val());
 	 	 		});

 	 			saveModify(checkedIds,'1');
 	 	 	}

 		}
 	},{
 		title:"关闭",//关闭按钮文字
 		ico:"ico-cancel",
 		onClick:function(){
 			batchModifyDialog.bxnDialog("close");
 			// 清空数据
 			$('#signTimeInput,#logoutTimeInput,#descriptionInput').val('');
 		}
 	}]
 });

 $("#batchmodify").click(function(){
	if ($.bxn.editing){
		alert("页面有正在编辑的内容！");
		return;
	} 
		
 	$(".batchModifyDialog").show();
 	batchModifyDialog.bxnDialog("show");
 });
  

//全选
 $(".checkAll").click(function(){
 	if($(this).is(":checked")){
 		$("input[type='checkbox']").each(function() {
			$(this).prop("checked",true);
		});
 	}else{
 		$("input[type='checkbox']").each(function() {
			$(this).prop("checked",false);
		});
 	}

 	watchCheckbox();
 });

 $("input[name='attendanceId']").change(function() { 
	 watchCheckbox();
});
 // 监听复选框
 function watchCheckbox(){
	var checkboxChecked = $("input[type='checkbox'][name='attendanceId']:checked");
	if(checkboxChecked.length == 0){
		$('.batch-wrapper').find('.btn').addClass('disabled');
	}else {
		$('.batch-wrapper').find('.btn').removeClass('disabled');
		$(this).prop("checked",false);
	}
 }
 
// 编辑
$("#attendanceForm").on("click",".edit",function(e){
	if ($.bxn.editing){
		alert("页面有正在编辑的内容！");
		return;
	} 
	var id=$(e.target).closest("td").attr("data-id");
	
	//时间变化
	$("#signTimeSpan"+id).hide();
	$("#signTimeInput"+id).show();
	$("#logoutTimeSpan"+id).hide();
	$("#logoutTimeInput"+id).show();
	
	// 状态变化
	$("#selectStatusDiv"+id).show();
	$("#statusDiv"+id).hide();
	
	//备注变化
	$("#descriptionSpan"+id).hide();
	$("#descriptionInput"+id).show();
	
	// 操作按钮变化
	$(".operation"+id +" .edit").hide();
	$(".operation"+id +" .save").show();
	$(".operation"+id +" .cancel").show();
	$(".operation"+id +" .reset").hide();
	
	$.bxn.editing = true;//是否编辑状态
	$.bxn.newing = true;
});

// 提交编辑，type:0-单个编辑，1-批量编辑
function saveModify(ids,type){
	
	//取签到签退的时间边界
	var minSignTime = $("#minSignTime").html().trim();
	var maxLogoutTime = $("#maxLogoutTime").html().trim();

	var signTime,logoutTime;
	// 取编辑后的时间
	var modifySignTime,modifyLogoutTime;

	var modifyStatus,description;

	var allowSave = true;

	if(ids.length == 0){
		allowSave = false;
	}
	
	for(var i=0;i<ids.length;i++){
		var id = ids[i];

		//取原来的时间
		signTime = $("#signTimeSpan"+id).html().trim();
		logoutTime = $("#logoutTimeSpan"+id).html().trim();

		if(type == 0){ // 单个编辑
			modifySignTime = $("#signTimeInput"+id).val();
			modifyLogoutTime = $("#logoutTimeInput"+id).val();

			modifyStatus = $("#selectStatus"+id).val();
			description = $("#descriptionInput"+id).val();
			
		}else{
			modifySignTime = $("#signTimeInput").val();
			modifyLogoutTime = $("#logoutTimeInput").val();

			modifyStatus = $("#selectStatus").val();
			description = $("#descriptionInput").val();
		}
		
		if(signTime != ''){
			if(modifySignTime == ''){
				alert("签到时间不能为空");
				allowSave = false;
				return false;
			}
		}
		if(logoutTime != ''){
			if(modifyLogoutTime == ''){
				alert("签退时间不能为空");
				allowSave = false;
				return false;
			}
		}

		var parseMinSignTime = Date.parse(new Date(minSignTime.replace(/\-/g, "\/")));
		var parseMaxLogoutTime = Date.parse(new Date(maxLogoutTime.replace(/\-/g, "\/")));
		
		if(modifySignTime != ''){
			var parseModifySignTime = Date.parse(new Date(modifySignTime.replace(/\-/g, "\/")));
			
			if(signTime != ''){
				var parseSignTime = Date.parse(new Date(signTime.replace(/\-/g, "\/")));
				if(parseSignTime != parseModifySignTime ){
					if(parseModifySignTime > parseMaxLogoutTime || parseModifySignTime < parseMinSignTime ){
						alert("修改的签到时间已经超过会议允许的签到时间");
						allowSave = false;
						return false;
					}
				}
			}else{
				if(parseModifySignTime > parseMaxLogoutTime || parseModifySignTime < parseMinSignTime ){
					alert("修改的签到时间已经超过会议允许的签到时间");
					allowSave = false;
					return false;
				}
			}
			
		}
		
		if(modifyLogoutTime != ''){
			var parseModifyLogoutTime = Date.parse(new Date(modifyLogoutTime.replace(/\-/g, "\/")));
			
			if(logoutTime != ''){
				var parseLogoutTime = Date.parse(new Date(logoutTime.replace(/\-/g, "\/")));
				if(parseLogoutTime != parseModifyLogoutTime ){
					if(parseModifyLogoutTime > parseMaxLogoutTime || parseModifyLogoutTime < parseMinSignTime){
						alert("修改的签退时间已经超过会议允许的签退时间");
						allowSave = false;
						return false;
					}
				}
			}else{
				if(parseModifyLogoutTime > parseMaxLogoutTime || parseModifyLogoutTime < parseMinSignTime){
					alert("修改的签退时间已经超过会议允许的签退时间");
					allowSave = false;
					return false;
				}
			}
			
		}
		
		if(modifySignTime != '' && modifyLogoutTime != ''){
			var parseModifySignTime = Date.parse(new Date(modifySignTime.replace(/\-/g, "\/")));
			var parseModifyLogoutTime = Date.parse(new Date(modifyLogoutTime.replace(/\-/g, "\/")));
			
			if(parseModifySignTime >= parseModifyLogoutTime){
				alert("签到时间不能大于签退时间");
				allowSave = false;
				return false;
			}
			
		}
		
		//检查漏卡状态
		if(modifyStatus == '4'){
			if('${attendanceMeeting.status}' == '0'){
				alert("会议尚未开始，无法报备漏卡");
				allowSave = false;
				return false;
			}
		}
	}

	if(!allowSave) return false;

	var data = "ids="+ ids.join(',') +"&signTime="+ signTime+"&logoutTime=" + logoutTime +"&modifySignTime=" + modifySignTime + "&modifyLogoutTime=" + modifyLogoutTime + 
    "&modifyStatus=" + modifyStatus +"&description="+description +"&meetingId=${attendanceMeeting.id}";

	$.ajax({
		cache: true,
		type: "POST",
		url:"${ctx}/webapi/meeting/v1/attendances",
		data:data,
		async: false,
		error: function(request) {
		},
		success: function(msg) {
			$.bxn.tools.showRemindInfo(msg);
			window.location.href = "${ctx}/meeting/attendance/indexPage?meetingId=${attendanceMeeting.id}";
		}
	});
	
}

// 保存
$("#attendanceForm").on("click",".save",function(e){
   var id =$(e.target).closest("td").attr("data-id");

   saveModify([id],'0');
});


//取消
$("#attendanceForm").on("click",".cancel",function(e){
	var id=$(e.target).closest("td").attr("data-id");
	
	//时间变化
	$("#signTimeSpan"+id).show();
	$("#signTimeInput"+id).hide();
	$("#logoutTimeSpan"+id).show();
	$("#logoutTimeInput"+id).hide();
	
	// 状态变化
	$("#selectStatusDiv"+id).hide();
	$("#statusDiv"+id).show();
	
	//备注变化
	$("#descriptionSpan"+id).show();
	$("#descriptionInput"+id).hide();
	
	// 操作按钮变化
	$(".operation"+id +" .edit").show();
	$(".operation"+id +" .save").hide();
	$(".operation"+id +" .cancel").hide();
	$(".operation"+id +" .reset").show();
	
	$.bxn.editing = false;//是否编辑状态
	$.bxn.newing = false;
});

//重置单条考勤记录
$("#attendanceForm").on("click",".reset",function(e){
	var id =$(e.target).closest("td").attr("data-id");
	var userName = $("#userName"+id).html().trim();

	// 重置考勤记录
	reset(userName);
});

// 批量重置考勤记录
$("#batchRefresh").on("click",function(){

	var checkboxChecked = $("input[type='checkbox'][name='attendanceId']:checked");
	if(checkboxChecked == 0){
		alert('请先勾选要重置的参会人员');
	} else {
		var userNames = [];
		checkboxChecked.each(function(index,item){
			userNames.push($(item).data('user-name'));
	 	});
		// 重置考勤记录
		reset(userNames.join(','));
	}
	
});

// 重置参会人员的考勤
function reset(userNames){
	if ($.bxn.editing){
		alert("页面有正在编辑的内容！");
		return;
	}

	$.bxn.confirm({msg:"是否重置所选参会人员的考勤记录？",width:"350",height:"80"},function(){
        var data = "_method=PUT&meetingId=${attendanceMeeting.id}&userNames="+userNames;
 		$.post('${ctx}/webapi/meeting/v1/attendances',data,
 		function(msg) {
 			$.bxn.tools.showRemindInfo(msg);
 			window.location.href = "${ctx}/meeting/attendance/indexPage?meetingId=${attendanceMeeting.id}";
 		});
 		
     },function(){
     });
}

$(".statusStat").click(function(e){
	 if($(this).hasClass("statusDisplay")){
		 $(this).removeClass("statusDisplay");
		 if($(this).find("span").attr("id") == '10'){ // 请假包含公假、私假、病假 都要删除
			 var index5 = statusArray.indexOf("5"); // 公假
		     if(index5 > -1){
		    	 statusArray.splice(index5,1);
		     }
		     var index6 = statusArray.indexOf("6"); // 私假
		     if(index6 > -1){
		    	 statusArray.splice(index6,1);
		     }
		     var index7 = statusArray.indexOf("7"); // 病假
		     if(index7 > -1){
		    	 statusArray.splice(index7,1);
		     }
		 }
		 else{
			 var index = statusArray.indexOf($(this).find("span").attr("id"));
			 if(index > -1){
				 statusArray.splice(index,1);
			 }
		 }
	 }else{
		 $(this).addClass("statusDisplay");
		 if($(this).find("span").attr("id") == '10'){
			 statusArray.push('5');
			 statusArray.push('6');
			 statusArray.push('7');
		 }else{
			 statusArray.push($(this).find("span").attr("id"));
		 }
	 }
	 // 过滤显示
	 filterStatus(statusArray);
	
});

// 状态过滤
function filterStatus(statusArray){
	var trList = $("#attendanceForm").children("table").children("tbody").children("tr");
	if(statusArray.length == 0){
		$("#attendanceForm").find("tr").show();
		return ;
	}
	
	for(var i = 0; i < trList.length; i++){
		var status = $(trList[i]).find("td.statusValue").text().trim();
		if(statusArray.indexOf(status) > -1){
			$(trList[i]).show();
		}else{
			$(trList[i]).hide();
		}
		 
	}
}


 </script>

</body>
</html>