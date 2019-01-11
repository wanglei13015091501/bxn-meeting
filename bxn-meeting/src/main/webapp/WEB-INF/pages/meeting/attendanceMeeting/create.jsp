<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.lang.String"%>
<%@ page import="cn.boxiao.bxn.meeting.util.JsonHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤基础设置</title>

<style type="text/css">
.disabled {
	pointer-events: none;
}

.input-layout-form .panel {
	border: 1px solid #ddd;
	margin-left: 0;
}

.input-layout-form .panel .p-head {
	min-height: 40px;
	line-height: 40px;
	border-color: #ddd;
}
.input-layout-form .panel .p-head .p-head-title {
    position: initial;
}

.group {
	display: inline-block;
}

.group>span {
	font-size: 12px;
    padding: 0px 8px;
    line-height: 30px;
    background: #ddd;
    cursor: pointer;
    margin-right: 10px;
    margin-bottom: 4px;
    color: #333;
    display: inline-block;
}
.group>span.active{
	background: #0093e7;
	color: #fff;
}
.group>span:first-child{
	margin-left: 10px;
}

.person-container {
	padding: 10px;
	min-height: 200px;
	overflow: hidden;
}

.container-title{
	margin-bottom: 10px;
}
/* 添加人员 */
.person-container .person-item .content .label-ico {
	position: absolute;
	left: 6px;
	top: 4.5px;
}

.person-container .person-item {
	display: inline-block;
    margin-right: 10px;
    margin-bottom: 10px;
    position: relative;
    width: 30%;
    border: 1px solid #0093e7;
    border-radius: 4px;
}

.person-container .person-item .content {
	width: 222px;
	padding-left: 24px;
	padding-right: 24px;
	line-height: 26px;
}
.person-container .person-item .content .value-area{
	color: initial;
    text-decoration: none;
}

.person-container .person-item .content .btn-remove {
	position: absolute;
	right: 6px;
	top: 5.5px;
}
</style>

</head>
<body>
<form id="meetingForm" class="input-layout-form" method="post">
		<div class="head">新建会议</div>
		<div class="panel" style="border:none">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
				
				<input type="hidden" name="parentId" id="parentId">
				<input type="hidden" name="level" id="level" value="1">
				
					<td class="label"><span class="marker">*</span><label>会议名称：</label></td>
					<td class="content" style="width:350px;">
						<input type="text" id="meetingName" name="meetingName" value="" style="width:92%;" 
								required required-tip="必须填会议名称" invalidChar maxlength="128"/>
					</td>
					<td class="description">仅限128字</td>
				</tr>
				<tr  class="category_level_1">
					<td class="label" ><span class="marker">*</span><label>地点：</label></td>
					<td class="content" style="width:350px;">
					<select id="placeForSearch" name="placeId" style="width:330px;" >
						<c:forEach items="${placeVoList}" var="item" varStatus="i">
							<option value="${item.id}">${item.placeName }</option>
						</c:forEach>
					</select>
					</td>
					<td class="description"></td>
				</tr>
				
				<tr  class="category_level_1">
					<td class="label"><span class="marker">*</span><label>时间：</label></td>
					<td class="content" style="width:350px;">
					<span></span>
						<input type="text" name="beginTime" id="beginTime"  required required-tip="必填开始时间"  style="width:150px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate input-fly mr20 ">
						<span>到</span>
						<input type="text" name="endTime" id="endTime"  required required-tip="必填结束时间"  style="width:150px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate input-fly">
					</td>
					<td class="description"></td>
				</tr>
				
				<tr  class="category_level_1">
					<td class="label" ><span class="marker">*</span><label>会议类型：</label></td>
					<td class="content" style="width:350px;">
					<select id="meetingTypeForSearch" name="meetingTypeId" style="width:330px;" onchange="getMeetingGroup()">
						<c:forEach items="${meetingTypeVoList}" var="item" varStatus="i">
							<option value="${item.id}">${item.typeName }</option>
						</c:forEach>
					</select>
					</td>
					<td class="description"></td>
				</tr>
				
				<!-- 是否循环会议：循环标记(0：不循环，1：每天， 2:每周， 3:每月) -->
				<input name="cycling" type="hidden" value="0">
				<tr class="cycling">
					<td class="label" ><label>是否循环会议：</label></td>
					<td class="content" style="width:350px;">
						<input name="cyclingCheckbox" type="checkbox">
					</td>
					<td class="description"></td>
				</tr>
				
				<tr class="cycling-type" style="display:none">
					<td class="label" ><label>循环方式：</label></td>
					<td class="content" style="width:350px;">
						<input name="cyclingRadio" value="1" type="radio" checked> 每日
						<input name="cyclingRadio" value="2" type="radio"> 每周
						<input name="cyclingRadio" value="3" type="radio"> 每月
					</td>
					<td class="description"></td>
				</tr>
				
				<tr class="category_level_1">
					<td class="label"><span class="marker">*</span><label>选择参会人员：</label></td>
					<td class="content read-orgs">
						<!-- <input type="text" style="width: 75%;" id="addClazzStuInput" readonly="readonly" class="addClazzStuInput" onclick="chooseUserForStu()" />
						<input type="hidden" id="studentIds" name="userIds" required required-tip="必填参会人员" style="width: 80%" invalidChar />
						<a href="javascript:;" class="btn btn-inline addClazzStu" style="vertical-align: middle;" onclick="chooseUserForStu()">
							<span class="left"></span>
							<span class="right">
								<span class="ico ico-modify"></span>
								选择
							</span>
						</a> -->
						
					</td>
					<td class="description"></td>
				</tr>
				<tr class="category_level_1">
					<td class="label"></td>
					<td class="content read-orgs" colspan="2">
						<div class="panel">
							<div class="p-head">
								<!-- 组织、部门 -->
								<span class="p-head-title" id="group-container">
									
								</span>
							</div>
							<div class="person-container">
								
								<input type="hidden" id="userIds" name="userIds" />
								
								<!-- 人员列表 -->
								<div class="container-title">
									<span>已选人员（<span class="num">0</span>）</span>
									<a href="javascript:;" class="btn btn-inline addClazzStu" style="vertical-align: middle;float:right" onclick="chooseUserForStu()">
										<span class="left"></span>
										<span class="right">
											<span class="ico ico-user-group"></span>
											选择
										</span>
									</a>
								</div>
								<!-- 已选人员查看框 -->
								<div class="container-content">
									
									
								</div>
							</div>
						</div>
					</td>
				</tr>
				
				<tr class="category_level_1">
					<td class="label"><label>其他组织者：</label></td>
					<td class="content write-orgs">
					<input type="text" style="width: 75%;" id="addOrganizerInput" readonly="readonly" class="addOrganizerInput" onclick="chooseOrganizers()" />
					<input type="hidden" id="organizerIds" name="organizerIds" style="width: 80%" invalidChar/>
						<a href="javascript:;" class="btn btn-inline addOrganizer" style="vertical-align: middle;" onclick="chooseOrganizers()">
							<span class="left"></span>
							<span class="right">
								<span class="ico ico-user-group"></span>
								选择
							</span>
						</a>
					</td>
					<td class="description"></td>
				</tr>
				
				<tr>
					<td class="label"><label>备注：</label></td>
					<td class="content">
					<textarea name="description" id="description"  rows="3" style="width:95%; height: 100px;"  invalidChar maxlength="128"></textarea>
					</td>
					<td class="description"></td>
				</tr>
			</table>
		</div>
		
		<div class="foot" style="">
			<a id="submitForm" href="javascript:void(0)" class="btn btn-common submit">
				<span class="left"></span>
				<span class="right"><span class="ico ico-submit"></span>确定</span>
			</a><a id="cancelForm" href="javascript:void(0)" class="btn btn-common " >
				<span class="left"></span>
				<span class="right"><span class="ico ico-cancel"></span>取消</span>
			</a>
		</div>
		
	</form>
<script type="text/javascript">

<%-- 从URL中提取schoolCode --%>

var groupUser;
var userIds = [];

$(function(){
	var currDate = new Date();
	var year = currDate.getFullYear();
	var month =  currDate.getMonth()+1;
	var day = currDate.getDate();
	if(month<10){
		month = '0'+month;
	}
	if(day<10){
		day = '0'+day;
	}
	
	var date =  year + "-" + month + "-" + day;
	
	$("#beginTime").val(date + " " + ((currDate.getHours())<23?(currDate.getHours()+1):"08") + ":00:00");
	$("#endTime").val(date + " " + ((currDate.getHours())<20?(currDate.getHours()+3):"10") + ":00:00");

	// 根据会议类型查部门信息
	getMeetingGroup();

	// 移除已选中的人员
	$(".input-layout-form").on("click",".person-container .btn-remove",function(){
		$(this).closest(".person-item").remove();

		// 更新选中人员
		tools.getAllId();
	});

	// 点击某部门 选中该部门下的人员
	$('#group-container').on('click', '.group > span', function(){
		var groupId = $(this).data('id');
		// 取消选中该部门下的人员
		if($(this).hasClass('on')){
			// 查部门下的人员信息
			getGroupUser(groupId);
			
			$(groupUser).each(function(item){
				var user = groupUser[item];
				if(tools.isChosen(user.userId)){
					$('.container-content .person-item[data-id="'+user.userId+'"]').remove();
				}
			})
			
			$(this).removeClass('on');

		}else{// 选中该部门下的人员
			// 查部门下的人员信息
			getGroupUser(groupId);

			$(this).addClass('on');

			$(groupUser).each(function(item){
				var user = groupUser[item];

				// 检查用户是否已经被选择，忽略掉已被选择的
				if(!tools.isChosen(user.userId)){
					$('.container-content').append(createUserHtml(user.userId, user.fullName, user.uniqueNo));
				}
			})
		}

		// 更新选中的人员id
		tools.getAllId();

	})

})


// 构造 已选择的用户dom元素
createUserHtml = function(id, name, uniqueNo){
	return '<div class="person-item" data-id="'+ id +'">'+
				'<span class="content">'+
					'<span class="label-ico ico ico-selected-user"></span>'+
					'<a class="value-area" href="javascript:;" id="'+ id +'">'+ name +'('+ uniqueNo +')</a>'+
					'<a href="javascript:;" class="btn-remove" title="删除"><span class="ico ico-cancel3"></span></a>'+
				'</span>'+
			'</div>';
}

var tools = tools || {},

tools = {
	// 获取已选择的参会人员
	getAllId: function(){
		var checkedIds = $(".person-container .container-content .person-item").map(function(){
			return $(this).data("id");
		}).get();

		userIds = checkedIds;
		// 更新已选人员数量
		$('.container-title span.num').text(userIds.length);
		return checkedIds;
	},

	// 判断某人员是否已被选择了,true ,false
	isChosen: function(userId){
		return flag = tools.getAllId().indexOf(userId)==-1 ? false : true;
	},
}

// 获取某个部门下的人员
function getGroupUser(groupId){
	$.ajax({
		url:"${ctx}/webapi/meeting/v1/meetings/group-users",
		data:{'groupId':groupId},
		cache: false,
		async: false,
		beforeSend: function(){
			//$('.container-content').append('<p class="info">人员添加中</p>');
		},
		success:function(data){
			groupUser = data;
		}
	});
}


// 根据会议类型加载部门
function getMeetingGroup(){
	// 会议类型
	var meetingType = $('#meetingTypeForSearch option:selected').val();

	$.ajax({
		url:"${ctx}/webapi/meeting/v1/meetings/"+meetingType,
		success:function(d){

			if(d.groupId){
				// 生成部门信息
				var html = '';
					html += '<span data-id="'+ d.groupId +'">'+ d.groupName +'</span>';
					html +=	'<div class="group">';

				for(var i = 0;i<d.childMeetingGroup.length;i++){
					var item = d.childMeetingGroup[i];
					html += '<span data-id="'+ item.groupId +'">'+ item.groupName +'</span>';
				}

					html += '</div>'

				$('#group-container').empty().html(html);
			}else{
				$('#group-container').empty().html('当前会议类型未关联分组');
			}
			
		}
	});
}


// 选择参加会议者
function chooseUserForStu(){
	var picker = new $.bxn.personPicker({
		onFinishPicked : function(users){

			//选择完成回调赋值
			
			// 清空原来选择的人员，重新渲染
			$('.container-content').empty();
			
			$(users).each(function(item){
				$('.container-content').append(createUserHtml(users[item].id, users[item].text, users[item].uniqueNo));
			});
			tools.getAllId();
			//$("#studentIds").val(ids.substring(0,ids.length-1));
			//$("#addClazzStuInput").val(names.substring(0,names.length-1));
		},
		title:$.bxn.personPicker.messages.defaultTitle,
		rootRuleId:"getOrganizationsByUserCategoryRule",//固定
		personProviderGroups:[{id:"1",name:"教师"},{id:"4",name:"学生"}],//2,3,4可多个
		currentSchool:{schoolCode:"${schoolVo.ibcCode}",schoolName:"${schoolVo.name}"},//schoolCode & schoolName 必须传
		multi:true,//是否多选
		checkedIds:userIds//已选项，打开默认勾选
	});
}

// 选择会议组织者
function chooseOrganizers(){
	var picker = new $.bxn.personPicker({
		onFinishPicked : function(users){
			//选择完成回调赋值
			var names = "";
			var ids = "";
			$(users).each(function(item){
				ids += users[item].id+",";						
				names += users[item].text+",";
			});
			$("#organizerIds").val(ids.substring(0,ids.length-1));
			$("#addOrganizerInput").val(names.substring(0,names.length-1));
		},
		title:$.bxn.personPicker.messages.defaultTitle,
		rootRuleId:"getOrganizationsByUserCategoryRule",//固定
		personProviderGroups:[{id:"1",name:"教师"},{id:"4",name:"学生"}],//2,3,4可多个
		currentSchool:{schoolCode:"${schoolVo.ibcCode}",schoolName:"${schoolVo.name}"},//schoolCode & schoolName 必须传
		multi:true,//是否多选
		checkedIds:[$("#organizerIds").val()]//已选项，打开默认勾选
	});
}


var $cycling = $('input[name="cycling"]');
// 处理会议循环类型
$('input[name="cyclingCheckbox"]').change(function(){
	var checked = $(this).is(":checked");

	if(checked){
		$('.cycling-type').show();
		
		var $cyclingRadio = $('input[name="cyclingRadio"]:checked');
		$cycling.val($cyclingRadio.val());
	} else { // 取消选中时，重置为0
		$('.cycling-type').hide();
		$cycling.val(0);
	}
});
$('input[name="cyclingRadio"]').change(function(){
	$cycling.val($(this).val());
});

//提交
$(".submit").click(function(){
	
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	if(beginTime.substring(0,10)!=endTime.substring(0,10)){
		alert("开始时间和结束时间必须在同一天!");
		return false;
	}
	if(beginTime > endTime){
		alert("结束时间不能小于开始时间!");
		return false;
	}

	if(tools.getAllId().length == 0){
		alert("参会人员不能为空，请选择参会人员！");
		return false;
	}

	// 获取参会人员id，填充至隐藏域中
	$('#userIds').val(userIds.join());

	$("#meetingForm").submit();
});
$("#meetingForm").bxnForm({
   	ajaxSubmit:true,
    url:"${ctx}/webapi/meeting/v1/meetings",
    type:"post",
    validataForm:true,
    success:function(form,data){
    	if(data.length==0){
    		window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
        }else{
        	$.bxn.alert({msg:data.join("<br>"),width:340},function(){
	        	$.bxn.tools.showRemindInfo(data.join("<br>"));
	        	window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
   			});
        }
    }
});

//取消
$("#meetingForm").on("click","#cancelForm",function(){
	window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
});
</script>
</body>
</html>