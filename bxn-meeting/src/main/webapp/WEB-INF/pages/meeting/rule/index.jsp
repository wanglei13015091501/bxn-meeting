<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤基础设置</title>

<style type="text/css">
.disabled{
pointer-events: none;
}
</style>

</head>
<body>
<form id="meetingRuleForm" class="input-layout-form" method="post">
		<div class="head">考勤基础设置</div>
		<div class="panel">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
				
				<input type="hidden" name="parentId" id="parentId">
				<input type="hidden" name="level" id="level" value="1">
				
					<td class="label"><span class="marker">*</span><label>正常签到定义：</label></td>
					<td class="content" style="width:450px;"><span>在会议开始前</span>
						<input type="text" id="normalSign" name="normalSign" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3" value="${ruleMap.normalSign.ruleValue}" style="width:50px;" 
								required required-tip="正常签到时间必填" invalidChar/>
								<span>分钟内打卡</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr  class="category_level_1">
					<td class="label"><span class="marker">*</span><label>正常签退定义：</label></td>
					<td class="content" ><span>在会议结束后</span>
						<input type="text" id="normalLogout" name="normalLogout" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3" value="${ruleMap.normalLogout.ruleValue}" style="width:50px;" 
								required required-tip="正常签退时间必填" invalidChar/>
								<span>分钟内打卡</span>
					</td>
					<td class="description"></td>
				</tr>
				
				<tr class="category_level_1">
					<td class="label"><span class="marker">*</span><label>迟到定义：</label></td>
					<td class="content" ><span>迟到时间在</span>
						<input type="text" id="late" name="late" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3"  value="${ruleMap.late.ruleValue}" style="width:50px;" 
								required required-tip="" invalidChar/>
								<span>分钟内不计</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr class="category_level_1">
					<td class="label"><span class="marker">*</span><label>早退定义：</label></td>
					<td class="content" ><span>早退时间在</span>
						<input type="text" id="leave" name="leave" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3"  value="${ruleMap.leave.ruleValue}" style="width:50px;" 
								required required-tip="" invalidChar/>
								<span>分钟内不计</span>
					</td>
					<td class="description"></td>
				</tr>
				
				<tr class="category_level_1">
					<td class="label"><span class="marker"><input id="notice_STATUS" name="notice_STATUS" type="checkbox" onclick="checkIsSendWarning()"
					<c:if test="${ruleMap.notice.status == '0'}"> checked = "checked" </c:if>></span>
					<label>发送会议提醒短信：</label></td>
					<td class="content" id="warningTd"><span>会议开始前</span>
						<input type="text" id="notice" name="notice" value="${ruleMap.notice.ruleValue}" style="width:50px;" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3" 
								required required-tip="" invalidChar/>
								<span>分钟</span>
					</td>
					<td class="description"></td>
				</tr>
				
				<tr>
					<td class="label"><span class="marker"></span><label>缺勤定义：</label></td>
					<td class="content" ><span class="marker"><input id="noClock_STATUS" name="noClock_STATUS" type="checkbox" 
					<c:if test="${ruleMap.noClock.status == '0'}"> checked = "checked"</c:if>></span><span>签到——签退时间范围内未打卡</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr>
					<td class="label"><span class="marker"></span></td>
					<td class="content" >
					<span class="marker"><input id="oneClock_STATUS" name="oneClock_STATUS" type="checkbox" 
					<c:if test="${ruleMap.oneClock.status == '0'}"> checked = "checked"</c:if>></span><span>签到——签退时间范围打卡打卡1次</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr>
					<td class="label"><span class="marker"></span></td>
					<td class="content" >
					<span class="marker"><input id="timeClock_STATUS" name="timeClock_STATUS" type="checkbox" onclick="checkTimeClock()"
					<c:if test="${ruleMap.timeClock.status == '0'}"> checked = "checked"</c:if>></span><span>签到——签退时间范围打卡，但签到签退有效时间间隔在</span>
					<input type="text" id="timeClock" name="timeClock" value="${ruleMap.timeClock.ruleValue}" style="width:50px;" 
								required required-tip="" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3"  invalidChar/>
								<span>分钟</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr>
					<td class="label"><span class="marker"></span></td>
					<td class="content" ><span class="marker"><input id="onlySign_STATUS" name="onlySign_STATUS" type="checkbox" 
					<c:if test="${ruleMap.onlySign.status == '0'}"> checked = "checked" </c:if>></span><span>只在正常签到时间内打卡</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr>
					<td class="label"><span class="marker"></span></td>
					<td class="content" ><span class="marker"><input id="onlyLogout_STATUS" name="onlyLogout_STATUS" type="checkbox" 
					<c:if test="${ruleMap.onlyLogout.status == '0'}"> checked = "checked"  </c:if>></span><span>只在正常签退时间内打卡</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr>
					<td class="label"><span class="marker"></span></td>
					<td class="content" ><span class="marker"><input id="maxMinuteLate_STATUS" name="maxMinuteLate_STATUS" type="checkbox" onclick="checkMaxMinuteLate();"
					<c:if test="${ruleMap.maxMinuteLate.status == '0'}"> checked = "checked" </c:if>></span><span>迟到</span>
					<input type="text" id="maxMinuteLate" name="maxMinuteLate" value="${ruleMap.maxMinuteLate.ruleValue}" style="width:50px;" 
								required required-tip="" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3"  invalidChar/>
								<span>分钟以上</span>
					</td>
					<td class="description"></td>
				</tr>
				<tr>
					<td class="label"><span class="marker"></span></td>
					<td class="content" ><span class="marker"><input id="maxMinuteLeave_STATUS" name="maxMinuteLeave_STATUS" type="checkbox"  onclick="checkMaxMinuteLeave();"
					<c:if test="${ruleMap.maxMinuteLeave.status == '0'}"> checked = "checked" </c:if>></span><span>早退</span>
					<input type="text" id="maxMinuteLeave" name="maxMinuteLeave" value="${ruleMap.maxMinuteLeave.ruleValue}" style="width:50px;" 
								required required-tip="" invalidChar onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3" />
								<span>分钟以上</span>
					</td>
					<td class="description"></td>
				</tr>
			</table>
		</div>
		
		<div class="foot" style="">
			<a class="btn btn-common submitData">
				<span class="left"></span>
				<span class="right"><span class="ico ico-submit"></span>确定</span>
			</a>
		</div>
		
	</form>
<script type="text/javascript">

$(function(){
	checkIsSendWarning();
	checkTimeClock();
	checkMaxMinuteLate();
	checkMaxMinuteLeave();
})
	function checkIsSendWarning(){
		if($("#notice_STATUS").is(':checked')){ 
			$("#notice").removeAttr("disabled");
		}else{
			$("#notice").attr("disabled", true);
		}
	}
	
function checkTimeClock(){
	if($("#timeClock_STATUS").is(':checked')){ 
		$("#timeClock").removeAttr("disabled");
	}else{
		$("#timeClock").attr("disabled", true);
	}
}

function checkMaxMinuteLate(){
	if($("#maxMinuteLate_STATUS").is(':checked')){ 
		$("#maxMinuteLate").removeAttr("disabled");
	}else{
		$("#maxMinuteLate").attr("disabled", true);
	}
}

function checkMaxMinuteLeave(){
	if($("#maxMinuteLeave_STATUS").is(':checked')){ 
		$("#maxMinuteLeave").removeAttr("disabled");
	}else{
		$("#maxMinuteLeave").attr("disabled", true);
	}
}

//提交
$(".submitData").click(function(){
	
	var ruleJson = new Array();//总数组
	
	var normalSign = new Object(); 
			normalSign.ruleName = "normalSign";
			normalSign.ruleValue = $("#normalSign").val();
			if(normalSign.ruleValue.trim() == ''){
				alert("正常签到时间必填!");
				return false;
			}
	var normalLogout = new Object();
			normalLogout.ruleName = "normalLogout";
			normalLogout.ruleValue = $("#normalLogout").val();
			if(normalLogout.ruleValue.trim() == ''){
				alert("正常签退时间必填!");
				return false;
			}
	var late = new Object();
			late.ruleName = "late";
			late.ruleValue = $("#late").val();
			if(late.ruleValue.trim() == ''){
				alert("迟到时间必填!");
				return false;
			}
	var leave = new Object();
			leave.ruleName = "leave";
			leave.ruleValue =  $("#leave").val();
			if(leave.ruleValue.trim() == ''){
				alert("早退时间必填!");
				return false;
			}
	var notice  = new Object();
			notice.ruleName = "notice";
			notice.ruleValue = $("#notice").val();
		if($("#notice_STATUS").is(':checked')){ 
			if(notice.ruleValue.trim() == ''){
				alert("会议开始前提醒分钟数必填!");
				return false;
			}
				notice.status = '0';
		}else{
				notice.status = '1';
			}
	var noClock  = new Object();
			noClock.ruleName = "noClock";
			noClock.ruleValue = $("#noClock").val();
			if($("#noClock_STATUS").is(':checked')){ 
				noClock.status = '0';
			}else{
				noClock.status = '1';
			}
	var oneClock  = new Object();
			oneClock.ruleName = "oneClock";
			if($("#oneClock_STATUS").is(':checked')){ 
				oneClock.status = '0';
			}else{
				oneClock.status = '1';
			}
	var timeClock  = new Object();
			timeClock.ruleName = "timeClock";
			timeClock.ruleValue = $("#timeClock").val();
			if($("#timeClock_STATUS").is(':checked')){ 
				if(timeClock.ruleValue.trim() == ''){
					alert("签退有效时间间隔必填!");
					return false;
				}
				timeClock.status = '0';
			}else{
				timeClock.status = '1';
			}
	var onlySign  = new Object();
			onlySign.ruleName = "onlySign";
			if($("#onlySign_STATUS").is(':checked')){ 
				onlySign.status = '0';
			}else{
				onlySign.status = '1';
			}
	var onlyLogout  = new Object();
			onlyLogout.ruleName = "onlyLogout";
			if($("#onlyLogout_STATUS").is(':checked')){ 
				onlyLogout.status = '0';
			}else{
				onlyLogout.status = '1';
			}
	var maxMinuteLate  = new Object();
			maxMinuteLate.ruleName = "maxMinuteLate";
			maxMinuteLate.ruleValue = $("#maxMinuteLate").val();
			if($("#maxMinuteLate_STATUS").is(':checked')){ 
				maxMinuteLate.status = '0';
				if(maxMinuteLate.ruleValue.trim() == ''){
					alert("缺勤迟到分钟数必填!");
					return false;
				}
			}else{
				maxMinuteLate.status = '1';
			}
			
	var maxMinuteLeave  = new Object();
			maxMinuteLeave.ruleName = "maxMinuteLeave";
			maxMinuteLeave.ruleValue = $("#maxMinuteLeave").val();
			maxMinuteLeave.status = $("#maxMinuteLeave_STATUS").val();
			if($("#maxMinuteLeave_STATUS").is(':checked')){ 
				if(maxMinuteLeave.ruleValue.trim() == ''){
					alert("缺勤早退分钟数必填!");
					return false;
				}
				maxMinuteLeave.status = '0';
			}else{
				maxMinuteLeave.status = '1';
			}
	ruleJson[0] = normalSign;
	ruleJson[1] = normalLogout;
	ruleJson[2] = late;
	ruleJson[3] = leave;
	ruleJson[4] = notice;
	ruleJson[5] = noClock;
	ruleJson[6] = oneClock;
	ruleJson[7] = timeClock;
	ruleJson[8] = onlySign;
	ruleJson[9] = onlyLogout;
	ruleJson[10] = maxMinuteLate;
	ruleJson[11] = maxMinuteLeave; 
	
	var data = "ruleJson="+JSON.stringify(ruleJson)+"&_method=PUT";
	$.post('${ctx}/webapi/meeting/v1/rules',data,function(msg) {
		$.bxn.tools.showRemindInfo(msg);
		alert("修改成功！",function(){
		window.location.href="${ctx}/meeting/rule/indexPage";
		});
	});
});
</script>
</body>
</html>