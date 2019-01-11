<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html>
<html>

<head>
<title>部门考勤详情</title>
	<META name="decorator" content="wxmobile">
	<!-- 防止浏览器把长数字识别成电话号码，这行不会影响真正电话号码的识别 -->
	<meta name = "format-detection" content = "telephone=no">

 	<link rel="stylesheet" href="${ctx }/resources/js/jquery-weui/lib/weui.min.css">
	<link rel="stylesheet" href="${ctx }/resources/js/jquery-weui/css/jquery-weui.css">
	
	<script src="${ctx }/resources/js/jquery-weui/lib/jquery-2.1.4.js"></script>

	<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/underscore/underscore-min.js"></script>
	<script src="${ctx }/resources/js/jquery-weui/lib/fastclick.js"></script>
	<script src="${ctx }/resources/js/jquery-weui/js/jquery-weui.js"></script>
	
	<!--highstock.js-->
	<script type="text/javascript" src="${ctx }/resources/js/highstock.js"></script>
	<!-- highcharts空数据展示 -->
	<script type="text/javascript" src="${ctx }/resources/js/no-data-to-display.js"></script>
	
	<link rel="stylesheet" href="${ctx }/resources/css/meeting.css">
	<script type="text/javascript" src="${ctx }/resources/js/meeting.js"></script>
</head>

<body ontouchstart>
	<div class="weui-navbar">
		<div class="weui-navbar__item weui_bar__item_on group caret">
			<span>部门:</span>
			<input class="weui-input" id="group" type="text" value="全部" readonly="" style="width: 40%;text-align: center;overflow: hidden; white-space: normal; text-overflow: ellipsis;">
		</div>
	</div>
	<div class="weui-tab" style="padding-top: 50px;">
		<div class="weui-tab__bd">
			<div class="weui-panel weui-panel_access" id="container">
				

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
		
		<!-- 全部会议详情-模版 -->
		<script type="text/template" id="template">
			<@if(attendanceData.length==0){@>
				<div class="weui-loadmore weui-loadmore_line">
  					<span class="weui-loadmore__tips">暂无数据</span>
				</div>
			<@}else{@>
				<@var item = attendanceData@>
				<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_small-appmsg cells" >
						<!-- 考勤饼图 -->
						<div id="pie_container">
							
						</div>
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
				<div class="weui-panel__hd">考勤明细</div>
				<!-- 人员明细 -->
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
					</div>
				<@})@>
			<@}@>
		</script>
		
		
		<script type="text/template" id="group_list_template">
			<@if(attendanceData.users.length==0){@>
				<div class="weui-loadmore weui-loadmore_line">
					<span class="weui-loadmore__tips">暂无打卡明细</span>
				</div>
			<@}else{@>
				<@_.each(attendanceData.users,function(user,index){@>
					<@
						if(statusArr.length>0 && statusArr.join('').indexOf(user.status) == -1){
							return;
						}
					@>
					<div class="slide-box" data-id="<@=user.id@>">
						<div class="slide-content weui-flex">
							<div class="weui-flex__item name"><@=user.fullName@></div>
							<div class="weui-flex__item time"><@if(user.signTime==null){@>-<@}else{@><@=user.signTime.split(" ")[1]@><@}@></div>
							<div class="weui-flex__item time"><@if(user.logoutTime==null){@>-<@}else{@><@=user.logoutTime.split(" ")[1]@><@}@></div>
							<div class="weui-flex__item <@=statusClazz@>"><@=user.status@></div>
						</div>
					</div>
				<@})@>
			<@}@>
		</script>
		
		
		
		<!-- 部门会议详情-模版 -->
		<script type="text/template" id="group_template">
				<@var item = attendanceData.count@>
				<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_small-appmsg cells" >
						<!-- 考勤饼图 -->
						<div id="pie_container">
							
						</div>
						<div class="weui-flex">
							<div class="weui-flex__item"></div>
							<div class="weui-flex__item" style="text-align: center;color:#666;">应到：<@=item.needJoinNum@></div>
							<div class="weui-flex__item" style="text-align: center;color:#666;">实到：<@=item.actualNum@></div>
							<div class="weui-flex__item"></div>
						</div>
						<!-- 考勤状态 -->
						<div class="weui-grids">
							<a href="javascript:;" class="weui-grid js_grid status0" data-status='0'>
								<span class="status0"><@=item.normalNum@></span>
								<p class="weui-grid__label">正常</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status1" data-status='1'>
								<span class="status1"><@=item.lateNum@></span>
								<p class="weui-grid__label">迟到</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status2" data-status='2'>
								<span class="status2"><@=item.leaveNum@></span>
								<p class="weui-grid__label">早退</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status3" data-status='3'>
								<span class="status3"><@=item.absenceNum@></span>
								<p class="weui-grid__label">缺勤</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status5" data-status='567'>
								<span class="status5"><@=item.holiday@></span>
								<p class="weui-grid__label">请假</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status4" data-status='4'>
								<span class="status4"><@=item.missingNum@></span>
								<p class="weui-grid__label">漏卡</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status8" data-status='8'>
								<span class="status8"><@=item.leaveLateNum@></span>
								<p class="weui-grid__label">迟到/早退</p>
							</a>
							<a href="javascript:;" class="weui-grid js_grid status9" data-status='9'>
								<span class="status9"><@=item.unPunchNum@></span>
								<p class="weui-grid__label">未打卡</p>
							</a>
						</div>
					</div>
				</div>
				<div class="weui-panel__hd">考勤明细</div>
				<!-- 人员明细 -->
				<div id="list_container">
					<@if(attendanceData.users.length==0){@>
						<div class="weui-loadmore weui-loadmore_line">
							<span class="weui-loadmore__tips">暂无打卡明细</span>
						</div>
					<@}else{@>
						<@_.each(attendanceData.users,function(user,index){@>
							<div class="slide-box" data-id="<@=user.id@>">
								<div class="slide-content weui-flex">
									<div class="weui-flex__item name"><@=user.fullName@></div>
									<div class="weui-flex__item time"><@if(user.signTime==null){@>-<@}else{@><@=user.signTime.split(" ")[1]@><@}@></div>
									<div class="weui-flex__item time"><@if(user.logoutTime==null){@>-<@}else{@><@=user.logoutTime.split(" ")[1]@><@}@></div>
									<div class="weui-flex__item <@=statusClazz@>"><@=user.status@></div>
								</div>
							</div>
						<@})@>

					<@}@>
				</div>
		</script>
		
	</div>


	<script>

		var userData;
		
		var meetingId = getParamValue("id");

		// 用于状态过滤
		var statusArr = [];

		$(function() {
			FastClick.attach(document.body);

			$.ajax({
		        url:'${ctx}/webapi/meeting/v1/mobile/group/groups',
		        type : "GET",
		        cache : false,
		        data:{
					'meetingId':meetingId
			    },
			    beforeSend: function(){
				},
		        success : function(data) {

			        var items = [
						{
							title: "全部",
						    value: "",
						}
			 		];

			        for(var i=0;i<data.length;i++){
						var item = {};
						item.title = data[i].groupName;
						item.value = data[i].id;

						items.push(item);
				    }

		        	$("#group").select({
						title: "选择部门",
						items: items
					});

					$("#group").change(function(){
						var groupId = $(this).data('values');

						 //不存在部门id，查全部的
						if(!groupId){
							getAllStat();
						}else{
							getGroupStat();
						}
					});
		        }
			})
			getAllStat();
			
		});


		// 查询会议全部的考勤统计信息
		function getAllStat(){
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

					$('#container').html(_.template($("#template").html()));

					// 状态集合      0       1     2		 3		4		5		8			9			
					var status = ['正常', '迟到', '早退', '缺勤', '漏卡', '请假', '迟到/早退', '未打卡'];
					var seriesData = [];

					// 初始化数组
					for(var i=0; i<8;i++){
						seriesData[i] = ['',0];
					}
					
					$.each(attendanceData.userStatusMap, function(key,value){
						var item = new Array(2);
						var value = isNaN(value) ? 0 : value;
						var key = key.split('status')[1];
						if(key < 5){
							
							item[0] = status[key];
							item[1] = value;

							seriesData[key] = item;

						}else if(key>=5 && key <=7 ){
							seriesData[5][0] = status[key];
							seriesData[5][1] += value;
						}else if(key == 8 || key == 9){
							item[0] = status[key-2];
							item[1] = value;
							seriesData[key-2] = item;

						}
					})
					
					initPic(seriesData);

					$('#list_container').html(_.template($("#list_template").html()));
					selectStatus('all');

					
		        }
			})
		}

		// 获取部门 统计信息
		function getGroupStat(){
			var groupId = $('#group').data('values');
			$.ajax({
		        url:'${ctx}/webapi/meeting/v1/mobile/group/attendances',
		        type : "GET",
		        cache : false,
		        data:{
					'meetingId':meetingId,
					'groupId' :groupId
			    },
			    beforeSend: function(){
			    	$('#container').html(_.template($("#loading").html()));
				},
		        success : function(data) {
		        	attendanceData = data;

					$('#container').html(_.template($("#group_template").html()));

					var seriesData = [];

					// 初始化数组
					for(var i=0; i<8;i++){
						seriesData[i] = ['',0];
					}
					
					$.each(attendanceData.count, function(key,value){
						var value = isNaN(value) ? 0 : value;
						switch (key){
						case 'normalNum':
							seriesData[0][0] = '正常';
							seriesData[0][1] = value;
							break;
						case 'lateNum':
							seriesData[1][0] = '迟到';
							seriesData[1][1] = value;
							break;
						case 'leaveNum':
							seriesData[2][0] = '早退';
							seriesData[2][1] = value;
							break;
						case 'absenceNum':
							seriesData[3][0] = '缺勤';
							seriesData[3][1] = value;
							break;
						case 'holiday':
							seriesData[5][0] = '请假';
							seriesData[5][1] = value;
							break;
						case 'missingNum':
							seriesData[4][0] = '漏卡';
							seriesData[4][1] = value;
							break;
						case 'leaveLateNum':
							seriesData[6][0] = '迟到/早退';
							seriesData[6][1] = value;
							break;
						case 'unPunchNum':
							seriesData[7][0] = '未打卡';
							seriesData[7][1] = value;
							break;
						default:
							break;
								
						}

					})
					
					initPic(seriesData);

					selectStatus('group');
		        }
			})

		}


		function selectStatus(type){
			// 切换考勤状态
			$('#container .weui-grids').on('click','.weui-grid', function(){
				if($(this).hasClass('checked')){
					$(this).removeClass('checked');
					if(type=='all'){
						var index = statusArr.indexOf($(this).data('status'));
					}else{
						var index = statusArr.indexOf($(this).find('.weui-grid__label').text());
					}
					// 删除 这个状态
					if(index > -1){
						statusArr.splice(index,1);
					}
				}else{
					$(this).addClass('checked')
					
					if(type=='all'){
						statusArr.push($(this).data('status'));
					}else{
						statusArr.push($(this).find('.weui-grid__label').text());
					}
				}

				$('#list_container').html(_.template($("#loading").html()));
				setTimeout(function() {
					if(type=='all'){
						$('#list_container').html(_.template($("#list_template").html()));
					}else{
						$('#list_container').html(_.template($("#group_list_template").html()));
					}
					

					if($('#list_container .slide-box').length ==0 ){
						$('#list_container').append('<p style="font-size: 14px;padding: 10px 0;text-align: center;">无匹配数据，请切换统计状态</p>')
					}

                }, 1000);
			})
		}

		// 构造饼图
		function initPic(seriesData){
			var width =  $('body').width();

			$('#pie_container').css({
				'width': '80%',
				'height': width * 0.8,
				'margin': '0 auto'
			})
			
			var zeroNum = 0;
			
			for(var i=0;i<seriesData.length;i++){
				if(seriesData[i][1]==0){
					zeroNum++;
				}
			}

			if(zeroNum == 8){
				$('#pie_container').html('<p style="font-size: 14px;height: inherit;text-align: center;color: #999;line-height:'+ width * 0.8 +'px">暂无饼图统计</p>');
				//$('#pie_container').hide();
				return;
			}
			
			$('#pie_container').highcharts({
		        title: {
		            text: null
		        },
		      	//去除版权信息
				credits : {
					enabled : false
				},
				//空数据时显示
				lang : {
					noData : "暂无统计数据"
				},
		        tooltip: {
		            headerFormat: '{series.name}<br>',
		            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: false
		                },
		                showInLegend: true
		            }
		        },
		        colors : [
					'#4ca8d4',//正常
					'#31abee',//迟到
					'#ff8e01',//早退
					'#ff3e18',//缺勤
					'#12ba49',//请假
					'#bd3eaa',//漏卡
					'#5569d4',//迟到早退
					'#999',//未打卡
				],
				legend: {                                                                    
		            enabled: false                                          
		        },
		        series: [{
		            type: 'pie',
		            name: '考勤统计',
		            data: seriesData
		        }]
		    });
		}
	</script>

</body>

</html>
