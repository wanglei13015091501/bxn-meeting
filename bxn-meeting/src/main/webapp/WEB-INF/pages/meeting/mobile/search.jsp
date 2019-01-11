<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html>
<html>

<head>
<title>考勤明细搜索</title>
<META name="decorator" content="wxmobile">
	<!-- 防止浏览器把长数字识别成电话号码，这行不会影响真正电话号码的识别 -->
	<meta name = "format-detection" content = "telephone=no">

 	<link rel="stylesheet" href="${ctx }/resources/js/jquery-weui/lib/weui.min.css">
	<link rel="stylesheet" href="${ctx }/resources/js/jquery-weui/css/jquery-weui.css">
	
	<script src="${ctx }/resources/js/jquery-weui/lib/jquery-2.1.4.js"></script>
	<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/underscore/underscore-min.js"></script>
	<script src="${ctx }/resources/js/jquery-weui/lib/fastclick.js"></script>
	<script src="${ctx }/resources/js/jquery-weui/js/jquery-weui.js"></script>
	
<!-- 	<script src="http://wechatfe.github.io/vconsole/lib/vconsole.min.js?v=2.5.2"></script> -->
	<link rel="stylesheet" href="${ctx }/resources/css/meeting.css">
	<script type="text/javascript" src="${ctx }/resources/js/meeting.js"></script>
</head>

<body ontouchstart>

	<div class="weui-search-bar weui-search-bar_focusing" id="searchBar">
		<form class="weui-search-bar__form" id="search_form">
			<input style="display:none" name="meetingId" id="meetingId" value="">
			<div class="weui-search-bar__box">
				<i class="weui-icon-search"></i>
				<input type="search" class="weui-search-bar__input" id="fullName" name="fullName" placeholder="搜索"
					required="">
				<a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
			</div>
			<label class="weui-search-bar__label" id="searchText">
				<i class="weui-icon-search"></i> <span>搜索</span>
			</label>
		</form>
		<a href="javascript:history.go(-1)" class="weui-search-bar__cancel-btn"
			id="searchCancel">取消</a>
	</div>
	
	<div id="user_container" style="width: 100%;overflow-x: hidden;"></div>
	
	<!-- 编辑人员信息 -->
	<div id="info_edit" class="weui-popup__container">
		<div class="weui-popup__overlay"></div>
			<div class="weui-popup__modal" id="edit_container">
	    		
	  		</div>
	  	</div>
	</div>
	
	
	<!-- 加载中 -->
	<script type="text/template" id="loading">
		<div class="weui-loadmore">
  			<i class="weui-loading"></i>
 			<span class="weui-loadmore__tips">正在加载</span>
		</div>
	</script>
	
	<script type="text/template" id="user_template">
	<@if(attendanceData.length==0){@>
		<div class="weui-loadmore weui-loadmore_line">
			<span class="weui-loadmore__tips">未查询到相关人</span>
		</div>
	<@}else{@>
		<@_.each(attendanceData,function(user,index){@>
			<@
				getStatusMsg(Number(user.status));
			@>
			<div class="slide-box">
				<div class="slide-content weui-flex">
					<div class="weui-flex__item name"><@=user.fullName@></div>
					<div class="weui-flex__item time"><@if(user.signTime==null){@>-<@}else{@><@=user.signTime.split(" ")[1]@><@}@></div>
					<div class="weui-flex__item time"><@if(user.logoutTime==null){@>-<@}else{@><@=user.logoutTime.split(" ")[1]@><@}@></div>
					<div class="weui-flex__item <@=statusClazz@>"><@=statusMsg@></div>
				</div>
				<a href="javascript:;" class="edit-btn" data-index="<@=index@>">编辑</a>
				<div class="memo">
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<p>教工号：<@=user.uniqueNo@></p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<p>备注：<@if(user.description==""){@>无<@}else{@><@=user.description@><@}@></p>
						</div>
					</div>
					<div class="weui-cell" data-page="search">
						<div class="weui-flex" style="width: calc(100% - 56px);text-align: center;">
							<div class="weui-flex__item">
								<span class="circle <@if(user.status == 0){@>active<@}@>" data-status="0" onclick="changeUserStatus('<@=user.id@>',this)">正常</span>
							</div>
									<div class="weui-flex__item">
								<span class="circle <@if(user.status == 1){@>active<@}@>" data-status="1" onclick="changeUserStatus('<@=user.id@>',this)">迟到</span>
							</div>
							<div class="weui-flex__item">
								<span class="circle <@if(user.status == 2){@>active<@}@>" data-status="2" onclick="changeUserStatus('<@=user.id@>',this)">早退</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		<@})@>
	<@}@>
	</script>
	
	<!-- 会议详情-模版 -->
		<script type="text/template" id="edit_template">
			<input id="id" type="hidden" value="<@=userData.id@>">
			<input id="userName" type="hidden" value="<@=userData.userName@>">
			<div class="weui-cells">
				<a class="weui-cell weui-cell_access" href="javascript:;">
					<div class="weui-cell__bd weui-cell_primary">
						<p><@=userData.fullName@>（<@=userData.uniqueNo@>）</p>
					</div>
				</a>
			</div>
			<div class="weui-cells border cells">

				<a class="weui-cell" href="javascript:;">
					<div class="weui-cell__hd">
						<img src="${ctx }/resources/images/icon/time.png">
					</div>
					<div class="weui-cell__bd">
						<p>签到时间</p>
					</div>
					<div class="weui-cell__ft beginTime">
						<input type="text" class="weui-input" id='modifySignTime' data-time="<@if(userData.signTime!=null){@><@=userData.signTime.split(" ")[1]@><@}@>" value="<@if(userData.signTime!=null){@><@=userData.signTime.split(" ")[1].replace( /:/g,' : ')@><@}@>" style="width: 90px;"/>
						<@if(userData.signTime==null){@><i class="weui-icon-cancel" onclick="$('#modifySignTime').val('')"></i><@}@>
					</div>
				</a>
				<a class="weui-cell" href="javascript:;">
					<div class="weui-cell__hd">
						<img src="${ctx }/resources/images/icon/time.png">
					</div>
					<div class="weui-cell__bd">
						<p>签退时间</p>
					</div>
					<div class="weui-cell__ft endTime">
						<input type="text" class="weui-input" id='modifyLogoutTime' data-time="<@if(userData.logoutTime!=null){@><@=userData.logoutTime.split(" ")[1]@><@}@>" value="<@if(userData.logoutTime!=null){@><@=userData.logoutTime.split(" ")[1].replace( /:/g,' : ')@><@}@>" style="width: 90px;"/>
						<@if(userData.logoutTime==null){@><i class="weui-icon-cancel" onclick="$('#modifyLogoutTime').val('')"></i><@}@>
					</div>
				</a>
				<a class="weui-cell weui-cell_access" href="javascript:;">
					<div class="weui-cell__hd">
						<img src="${ctx }/resources/images/icon/status.png">
					</div>
					<div class="weui-cell__bd">
						<p>考勤状态</p>
					</div>
					<div class="weui-cell__ft">
						<select name="modifyStatus" id="modifyStatus">
						  <option value ="0">正常</option>
						  <option value ="1">迟到</option>
						  <option value="2">早退</option>
						  <option value="3">缺勤</option>
						  <option value ="4">漏卡</option>
						  <option value ="5">公假</option>
						  <option value="6">私假</option>
						  <option value="7">病假</option>
						  <option value="8">迟到/早退</option>
						  <option value="9">未打卡</option>
						</select>
					</div>
				</a>
	
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea id="description" class="weui-textarea" maxlength="100" placeholder="请填写备注（100字）" rows="6"><@=userData.description@></textarea>
					</div>
				</div>
			</div>
			
			<a href="javascript:;" class="weui-btn weui-btn_primary btn save-btn">保存</a>
			<a href="javascript:;" class="weui-btn weui-btn_primary btn reset-btn">重置</a>
			<a href="javascript:;" class="weui-btn weui-btn_primary btn close-popup">取消</a>

		</script>


	<script>

		var userData;
		var meetingData;

		var meetingId = getParamValue("meetingId");
		var meetingSignTime = getParamValue("signTime");
		var meetingLogoutTime = getParamValue("logoutTime");
		$(function() {
			FastClick.attach(document.body);

			$('#meetingId').val(meetingId);

			// $("#search_form").onsubmit = function () {
			$('#fullName').bind('input propertychange', function() {
				if(!$(this).val()){
					return;
				}

				search();
				
			});  
		});

		function search(){
			$.ajax({
		        url:'${ctx}/webapi/meeting/v1/mobile/attendance-users/fullName',
		        type : "GET",
		        data:$('#search_form').serialize(),
			    beforeSend: function(){
			    	$('#user_container').html(_.template($("#loading").html()));
				},
		        success : function(data) {
		        	attendanceData = data;
		        	$('#user_container').html(_.template($("#user_template").html()));

		        	/**
					 * 左滑编辑
					 */
					$(".slide-box").slide({
				        sItemClass: "slide-box",
				        operateClass: "edit-btn",
				        operateHandler: function (target) {

							var index = target.find('.edit-btn').data('index');

							userData = attendanceData[index];
							
							$('#edit_container').html(_.template($("#edit_template").html()));
							$('#modifyStatus option[value="'+userData.status+'"]').attr("selected",true);

							timeSelect(meetingSignTime,meetingLogoutTime);

							// 初始化会议时间
				        	$("#modifySignTime").picker({
								title: "请选择签到时间",
								cols: [
									{
										textAlign: 'center',
								    	values: hoursArr
								  	},
									{
										textAlign: 'center',
										values: ':'
									},
									{
								   		textAlign: 'center',
										values: minutesArr
									},
									{
										textAlign: 'center',
									    values: ':'
									},
								  	{
								    	textAlign: 'center',
								    	values: secondsArr
								  	}
								],
								onClose:function(e){

									if(!$("#modifySignTime").data('time') && !$("#modifySignTime").val()){
										return;
									}

									var time = e.value.join("");

									var d = meetingLogoutTime.split(" ")[0]

									// 检查签到时间
									time = d + ' ' + time;

									if(time < meetingSignTime || time > meetingLogoutTime){
										$.toast("签到时间不能早于"+ meetingSignTime.split(" ")[1] +"<br>并且不能晚于" +meetingLogoutTime.split(" ")[1], 'text');
						                //打开结束时间日期选择框
						                setTimeout(function() {
						                	$("#modifySignTime").picker("open");

						                	$("#modifyLogoutTime").picker("close");
						                }, 2000);
						                return;
									}

								}
							});

				        	// 初始化会议时间
				        	$("#modifyLogoutTime").picker({
								title: "请选择签退时间",
								cols: [
									{
										textAlign: 'center',
								    	values: hoursArr
								  	},
									{
										textAlign: 'center',
										values: ':'
									},
									{
								   		textAlign: 'center',
										values: minutesArr
									},
									{
										textAlign: 'center',
									    values: ':'
									},
								  	{
								    	textAlign: 'center',
								    	values: secondsArr
								  	}
								],
								onClose:function(e){

									if(!$("#modifyLogoutTime").data('time') && !$("#modifyLogoutTime").val()){
										return;
									}

									var time = e.value.join("");

									var d = meetingLogoutTime.split(" ")[0]

									// 检查签退时间
									time = d + ' ' + time;

									var time1 = meetingSignTime;
									if($('#modifySignTime').val()){
										time1 = d + ' ' + $('#modifySignTime').val().replace( / /g,'');
									}

									if(time < time1 || time > meetingLogoutTime){
										$.toast("签退时间不能早于"+ time1.split(" ")[1] +"<br>并且不能晚于" +meetingLogoutTime.split(" ")[1], 'text');
						                //打开结束时间日期选择框
						                setTimeout(function() {
						                	$("#modifyLogoutTime").picker("open");
						                }, 2000);

						                return;
									}
								}
							});


							
				            $("#info_edit").popup();

				        },
				        itemClickHandler: function (target) {
				        	
				        }
				    });
		        }
			})


		}

		$("#edit_container").on('click','.save-btn',function(){
			var id = $("#id").val();
			var modifySignTime = $("#modifySignTime").val().replace( / /g,'');;
			var modifyLogoutTime = $("#modifyLogoutTime").val().replace( / /g,'');;
			var modifyStatus = $("#modifyStatus").val();
			var description = $("#description").val();

			var time1 = $("#modifySignTime").data('time');
			var time2 = $("#modifyLogoutTime").data('time');

			if(time1 && !modifySignTime){
				$.toast("请选择签到时间", 'forbidden');
				return;
			}

			if(time2 && !modifyLogoutTime){
				$.toast("请选择签到时间", 'forbidden');
				return;
			}


			var date = meetingSignTime.split(" ")[0];
			if(modifySignTime) modifySignTime = date + " " + modifySignTime;
			if(modifyLogoutTime) modifyLogoutTime = date + " " + modifyLogoutTime;
			
			$.ajax({
		        url:'${ctx}/webapi/meeting/v1/mobile/attendance-users',
		        type : "POST",
		        data:{
			        '_method':'PUT',
					'id':id,
					'modifySignTime':modifySignTime,
					'modifyLogoutTime':modifyLogoutTime,
					'modifyStatus':modifyStatus,
					'description':description
			    },
		        success : function(data) {
			        // 关闭popup弹出框
		        	$.closePopup();
		        	// 给出成功提示
		        	setTimeout(function() {
		        		$.toast("修改成功", 'OK');
		        	}, 500);

		        	search();
		        }
			})
		})
		
		// 重置单条考勤记录
		$("#edit_container").on('click','.reset-btn',function(){
			$.confirm("是否重置该参会人员考勤记录?", function() {
				var userName = $('#userName').val();
				$.ajax({
			        url:'${ctx}/webapi/meeting/v1/mobile/attendance-reset-users',
			        type : "POST",
			        data:{
				        '_method':'PUT',
				        'meetingId':meetingId,
						'userName':userName
				    },
			        success : function(data) {
				        // 关闭popup弹出框
			        	$.closePopup();
			        	// 给出成功提示
			        	setTimeout(function() {
			        		$.toast("重置成功", 'OK');
			        	}, 500);

			        	search();
			        }
				})
			}, function() {
				  //点击取消后的回调函数
			});
		})



	</script>

</body>

</html>
