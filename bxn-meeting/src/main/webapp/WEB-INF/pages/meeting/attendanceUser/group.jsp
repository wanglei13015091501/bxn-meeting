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
#createTime{
	margin-left: 15px;
}

@media (max-width: 1210px){
	#createTime{
		margin-left: 0;
	}
}

#qrSigned.disabled{
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
			<a href="javascript:void(0)" class="btn btn-common btn-export">
			     <span class="left"></span>
			     <span class="right">
			     	<span class="ico ico-export"></span>导出部门统计及明细
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
				<a href="#userTab" class="tab-item " id="tab-area-user">考勤明细</a>
				<a href="#groupTab" class="tab-item active" id="tab-area-group">部门统计</a> <!-- active为默认选中项 -->
			</div>        
		</div>
		
		<div class="tab-content" id="userTab">
		
		</div>
		<div class="tab-content" id="groupTab">
			<table width="95%" class="table" align="center">
				<thead id="placeHead">
					<tr id="firstTr">
						<td class='d-td' width="30">序号</td>
						<td class='d-td' width="200">部门</td>
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
						<tr id="${attendanceGroup.groupId }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
							<td class='d-td'>${rowIndex+1}</td>
							<td class='d-td align-left' onclick="toStatDetailPage('${attendanceGroup.groupId}')" style="cursor: pointer;">
								<span style="color: blue">${attendanceGroup.groupName}</span></td>
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
						<tr >
							<td class='d-td'></td>
							<td class='d-td align-left'>总计</td>
							<td class='d-td align-left'>${summaryGroup.needJoinNum}</td>
							<td class='d-td align-left'>${summaryGroup.actualNum}</td>
							<td class='d-td align-left'>${summaryGroup.normalNum}</td>
							<td class='d-td align-left'>${summaryGroup.lateNum}</td>
							<td class='d-td align-left'>${summaryGroup.leaveNum}</td>
							<td class='d-td align-left'>${summaryGroup.leaveLateNum}</td>
							<td class='d-td align-left'>${summaryGroup.holidayNum}</td>
							<td class='d-td align-left'>${summaryGroup.absenceNum}</td>
							<td class='d-td align-left'>${summaryGroup.missingNum}</td>
							<td class='d-td align-left'>${summaryGroup.unPunchNum}</td>
							<td class='d-td align-left'>${summaryGroup.normalRates}</td>
						</tr>
				</tbody>
			</table>
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

<script>

function toStatDetailPage(groupId){
    window.location.href="${ctx}/meeting/attendance/groupDetailPage/"+groupId+"?meetingId=${attendanceMeeting.id}";
}

$(function () {	
	
	$("#attendanceUserTab").bxnTab({
		onShow:function(tab){
			var tabId = tab[0].id;
			if(tabId == 'userTab'){
				window.location.href="${ctx}/meeting/attendance/indexPage?meetingId=${attendanceMeeting.id}";
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
	// 处理兼容性：safari下只能支持YYYY/MM/DD HH:MM:SS,ff和chrome不会受此影响
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
 
//导出
$("a.btn-export").click(function(){
	window.location.href="${ctx}/webapi/meeting/v1/attendance-export?meetingId=${attendanceMeeting.id}";
});
 
 // 返回
 $("a.btn-back").click(function(){
	 window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
 });
 
 

 </script>

</body>
</html>