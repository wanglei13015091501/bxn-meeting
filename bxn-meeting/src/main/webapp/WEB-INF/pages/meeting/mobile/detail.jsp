<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html>
<html>

<head>
<title>考勤详情</title>
	<META name="decorator" content="wxmobile">
	<!-- 防止浏览器把长数字识别成电话号码，这行不会影响真正电话号码的识别 -->
	<meta name = "format-detection" content = "telephone=no">
 	<link rel="stylesheet" href="${ctx }/resources/js/jquery-weui/lib/weui.min.css">
	<link rel="stylesheet" href="${ctx }/resources/js/jquery-weui/css/jquery-weui.css">
	
	<script src="${ctx }/resources/js/jquery-weui/lib/jquery-2.1.4.js"></script>
	<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/underscore/underscore-min.js"></script>
	<script src="${ctx }/resources/js/jquery-weui/lib/fastclick.js"></script>
	<script src="${ctx }/resources/js/jquery-weui/js/jquery-weui.js"></script>
	
	
	<link rel="stylesheet" href="${ctx }/resources/css/meeting.css">
	<script type="text/javascript" src="${ctx }/resources/js/meeting.js"></script>
</head>

<body ontouchstart>

	<div class="weui-tab">
		<div class="weui-tab__bd">
			<div class="weui-panel weui-panel_access" id="container">
				

			</div>
		</div>

	</div>
	
	
	<!-- 编辑人员信息 -->
	<div id="info_edit" class="weui-popup__container">
		<div class="weui-popup__overlay"></div>
			<div class="weui-popup__modal" id="edit_container">
	    		
	  		</div>
	  	</div>
	</div>
	
	<!-- 修改会议时间 -->
	<div id="edit_meeting_time" class="weui-popup__container">
		<div class="weui-popup__overlay"></div>
			<div class="weui-popup__modal" id="container">
	    		<div class="weui-cells">
					<a class="weui-cell weui-cell_access" href="javascript:;">
						<div class="weui-cell__bd weui-cell_primary">
							<p id="meetingName"></p>
						</div>
					</a>
				</div>
				<div class="weui-cells border cells">
	
					<a class="weui-cell" href="javascript:;">
						<div class="weui-cell__hd" style="width:18px;height:18px">
							<img src="${ctx }/resources/images/icon/begin_time.png">
						</div>
						<div class="weui-cell__bd">
							<p>开始时间</p>
						</div>
						<div class="weui-cell__ft beginTime">
							<input type="time" class="weui-input" id='meetingBeginTime' value="" style="width: 88px;font-size:24px"/>
						</div>
					</a>
					<a class="weui-cell" href="javascript:;">
						<div class="weui-cell__hd" style="width:18px;height:18px">
							<img src="${ctx }/resources/images/icon/end_time.png">
						</div>
						<div class="weui-cell__bd">
							<p>结束时间</p>
						</div>
						<div class="weui-cell__ft endTime">
							<input type="time" class="weui-input" id='meetingEndTime' value="" style="width: 88px;font-size:24px"/>
						</div>
					</a>
				</div>
				<a href="javascript:;" class="weui-btn weui-btn_primary btn save-btn">保存</a>
				<a href="javascript:;" class="weui-btn weui-btn_primary btn close-popup">取消</a>
	  		</div>
	  	</div>
	</div>

	<div class="templates">
		<!-- 加载中 -->
		<script type="text/template" id="loading">
			<div class="weui-loadmore">
  				<i class="weui-loading"></i>
 				<span class="weui-loadmore__tips">正在加载</span>
			</div>
		</script>
		
		<!-- 会议详情-模版 -->
		<script type="text/template" id="template">
			<@if(attendanceData.length==0){@>
				<div class="weui-loadmore weui-loadmore_line">
  					<span class="weui-loadmore__tips">暂无数据</span>
				</div>
			<@}else{@>
				<@var item = attendanceData@>
				<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_small-appmsg cells" >
						<!-- 会议信息 -->
						<a class="weui-cells" href="javascript:;">
							<div class="weui-cells__title">
								<p><@=item.meetingName@></p>
								<span class="status 
									<@if(item.status==0){@>init">未开始<@}@>
									<@if(item.status==1){@>going">进行中<@}@>
									<@if(item.status==2){@>end">已结束<@}@>
								</span>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<img src="${ctx }/resources/images/icon/time.png">
								</div>
								<div class="weui-cell__bd">
									<p><@=item.beginTime.split(" ")[1]@> － <@=item.endTime.split(" ")[1]@></p>
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<img src="${ctx }/resources/images/icon/place.png">
								</div>
								<div class="weui-cell__bd">
									<p><@=item.placeName@></p>
								</div>
								<%--<div class="weui-cell__ft"><@=item.attendanceMapList.length@>人参加</div>--%>
								<div class="weui-cell__bd operate-btn">
									<span id="edit_meeting"><span class="icon icon-edit"></span>编辑</span>
									<span id="reload"><span class="icon icon-reload"></span>刷新</span>
								</div>
							</div>
						</a>
						<!-- 考勤状态 -->
						<div class="weui-grids">
							<a href="javascript:;" class="weui-grid js_grid status0" data-status='0'>
								<span class="status0"><@=item.userStatusMap.status0@></span>
								<p class="weui-grid__label">正常</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status1" data-status='1'>
								<span class="status1"><@=item.userStatusMap.status1@></span>
								<p class="weui-grid__label">迟到</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status2" data-status='2'>
								<span class="status2"><@=item.userStatusMap.status2@></span>
								<p class="weui-grid__label">早退</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status3" data-status='3'>
								<span class="status3"><@=item.userStatusMap.status3@></span>
								<p class="weui-grid__label">缺勤</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status5" data-status='567'>
								<span class="status5"><@=(item.userStatusMap.status5)+(item.userStatusMap.status6)+(item.userStatusMap.status7)@></span>
								<p class="weui-grid__label">请假</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status4" data-status='4'>
								<span class="status4"><@=item.userStatusMap.status4@></span>
								<p class="weui-grid__label">漏卡</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status8" data-status='8'>
								<span class="status8"><@=item.userStatusMap.status8@></span>
								<p class="weui-grid__label">迟到/早退</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status9" data-status='9'>
								<span class="status9"><@=item.userStatusMap.status9@></span>
								<p class="weui-grid__label">未打卡</p>
							</a>
						</div>
					</div>
				</div>
				<div class="weui-panel__hd">
					打卡明细<a href="searchPage?meetingId=<@=meetingId@>&signTime=<@=item.signTime@>&logoutTime=<@=item.logoutTime@>"><span class="icon search"></span></a>
				</div>


				<div id="list_container">
				
				</div>
			<@}@>
		</script>
		
		
		<script type="text/template" id="list_template">
			<@if(attendanceData.attendanceMapList.length==0){@>
				<div class="weui-loadmore weui-loadmore_line">
						<span class="weui-loadmore__tips">暂无打卡明细</span>
				</div>
			<@}else{@>
				<@_.each(attendanceData.attendanceMapList,function(user,index){@>
		
					<@
						getStatusMsg(Number(user.status));
						if(statusArr.length>0 && statusArr.join('').indexOf(user.status) == -1){
							return;
						}
					@>

					
					<div class="slide-box" data-id="<@=user.id@>">
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
							<div class="weui-cell" data-page="detail">
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
	</div>


	<script>

		var userData;

		var meetingLogoutTime;
		var meetingSignTime;
		
		var meetingId = getParamValue("id");

		// 用于状态过滤
		var statusArr = [];

		$(function() {
			FastClick.attach(document.body);

			getMeetingInfo();


			// 刷新考勤
			$('#container').on('click','#reload',function(){
				
				$.showLoading('重新计算考勤结果中...');

				/* $.ajax({
					url: 'url',
					type: 'get',
					success: function(data){
						$.hideLoading();
						$.toast("计算完毕", 'OK');
						
						// 1秒后刷新页面
			        	setTimeout(function() {
			        		window.location.reload();
		                }, 1000);
					},
					error: function(err){

					}
				}) */

				 setTimeout(function() {
					$.hideLoading();
					$.toast("计算完毕", 'OK');
			     }, 3000)
			})

			// 修改会议时间 显示弹出框
			$('#container').on('click','#edit_meeting',function(){
				// 会议开始时间、会议结束时间
	        	$('#meetingBeginTime').val(attendanceData.beginTime.split(' ')[1]);
	        	$('#meetingEndTime').val(attendanceData.endTime.split(' ')[1]);
	        	// 会议名称
	        	$('#meetingName').text(attendanceData.meetingName);
				$('#edit_meeting_time').popup();
			})

			// 修改会议时间
			$("#edit_meeting_time").on('click','.save-btn',function(){
	
				var time1 = $("#meetingBeginTime").val();
				var time2 = $("#meetingEndTime").val();
	
				if(!time1){
					$.toptip('会议开始时间不能为空', 'error');
					return;
				}
	
				if(!time2){
					$.toptip('会议结束时间不能为空', 'error');
					return;
				}

				if(time2 < time1){
					$.toptip('会议结束时间不能早于会议开始时间', 'error');
					return;
				}
	
				var date = attendanceData.beginTime.split(" ")[0];

				if(time1.split(':').length == 2){
					time1 +=':00'
				}
				if(time2.split(':').length == 2){
					time2 +=':00'
				}
				time1 = date + " " + time1;
				time2 = date + " " + time2;

				alert(time1 + '<br>' + time2)
				
				/* if(modifySignTime) modifySignTime = date + " " + modifySignTime;
				if(modifyLogoutTime) modifyLogoutTime = date + " " + modifyLogoutTime; */
				
	
				/* $.ajax({
			        url:'${ctx}/webapi/meeting/v1/mobile/',
			        type : "POST",
			        data:{
				        '_method':'PUT',
						'id':id,
				    },
			        success : function(data) {
				        // 关闭popup弹出框
			        	$.closePopup();
			        	// 给出成功提示
			        	setTimeout(function() {
			        		$.toast("修改成功", 'OK');
			        	}, 500);
			        	// 1秒后刷新页面
			        	setTimeout(function() {
			        		window.location.reload();
		                }, 1000);
			        }
				}) */
			})
		});

		function getMeetingInfo(){
			$.ajax({
		        url:'${ctx}/webapi/meeting/v1/mobile/attendance-users',
		        type : "GET",
		        cache : false,
		        data:{
					'meetingId':meetingId
			    },
			    beforeSend: function(){
			    	$('#container').html(_.template($("#loading").html()));
				},
		        success : function(data) {
		        	attendanceData = data;

		        	meetingSignTime = attendanceData.signTime;
		        	meetingLogoutTime = attendanceData.logoutTime;
		        	
					$('#container').html(_.template($("#template").html()));

					$('#list_container').html(_.template($("#list_template").html()));

					// 切换考勤状态
					$('#container .weui-grids').on('click','.weui-grid', function(){
						if($(this).hasClass('checked')){
							$(this).removeClass('checked');
							var index = statusArr.indexOf($(this).data('status'));
							// 删除 这个状态
							if(index > -1){
								statusArr.splice(index,1);
							}
						}else{
							$(this).addClass('checked')
							statusArr.push($(this).data('status'));
						}

						$('#list_container').html(_.template($("#loading").html()));
						setTimeout(function() {
							$('#list_container').html(_.template($("#list_template").html()));

							if($('#list_container .slide-box').length ==0 ){
								$('#list_container').append('<p style="font-size: 14px;padding: 10px 0;text-align: center;">无匹配数据，请切换统计状态</p>')
							}

							// 初始化左滑 按钮
							initSlide();
							
		                }, 1000);
					})
					
					
					// 初始化左滑 按钮
					initSlide();

		        }
			})
		}

		/**
		 * 初始化 左滑插件
		 */

		function initSlide(){
			/**
			 * 左滑编辑
			 */
			$(".slide-box").slide({
		        sItemClass: "slide-box",
		        operateClass: "edit-btn",
		        operateHandler: function (target) {

					var index = target.find('.edit-btn').data('index');

					userData = attendanceData.attendanceMapList[index];
					$('#edit_container').html(_.template($("#edit_template").html()));
					$('#modifyStatus option[value="'+userData.status+'"]').attr("selected",true);

					timeSelect(attendanceData.signTime,attendanceData.logoutTime);

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

							// 检查签到时间
							time = beginDate + ' ' + time;

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

							// 检查签退时间
							time = beginDate + ' ' + time;

							var time1 = meetingSignTime;
							if($('#modifySignTime').val()){
								time1 = beginDate + ' ' + $('#modifySignTime').val().replace( / /g,'');
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


			var date = attendanceData.beginTime.split(" ")[0];
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
		        	// 1秒后刷新页面
		        	setTimeout(function() {
		        		window.location.reload();
	                }, 1000);
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
			        	// 1秒后刷新页面
			        	setTimeout(function() {
			        		window.location.reload();
		                }, 1000);
			        }
				})
			}, function() {
				  //点击取消后的回调函数
			});
		})
		
		
	</script>

</body>

</html>
