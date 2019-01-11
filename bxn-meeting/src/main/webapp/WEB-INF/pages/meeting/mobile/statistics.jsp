<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html>
<html>

<head>
<title>考勤列表</title>
<META name="decorator" content="wxmobile">
<!-- 防止浏览器把长数字识别成电话号码，这行不会影响真正电话号码的识别 -->
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet"
	href="${ctx }/resources/js/jquery-weui/lib/weui.min.css">
<link rel="stylesheet"
	href="${ctx }/resources/js/jquery-weui/css/jquery-weui.css">

<script src="${ctx }/resources/js/jquery-weui/lib/jquery-2.1.4.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${staticResRoot }/script/lib/underscore/underscore-min.js"></script>
<script src="${ctx }/resources/js/jquery-weui/lib/fastclick.js"></script>
<script src="${ctx }/resources/js/jquery-weui/js/jquery-weui.js"></script>

<script src="${ctx }/resources/js/daterangepicker/moment.min.js"></script>
<script src="${ctx }/resources/js/daterangepicker/jquery.daterangepicker.js"></script>

<link rel="stylesheet" href="${ctx }/resources/js/daterangepicker/daterangepicker.css">

<!-- <script
	src="http://wechatfe.github.io/vconsole/lib/vconsole.min.js?v=2.5.2"></script> -->
<link rel="stylesheet" href="${ctx }/resources/css/meeting.css">
<script type="text/javascript" src="${ctx }/resources/js/meeting.js"></script>
</head>

<body ontouchstart>
	
	<div class="page-body">
		<div class="header weui-navbar">
			<div class="weui-panel weui-panel_access">
				<div class="weui-panel__hd date-picker caret">
					<input class="weui-input" id="date-picker" readonly value=""/>
					<span class="icon calendar"></span>
				</div>
				<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_small-appmsg cells stat">
						<!-- 考勤状态 -->
						<div class="weui-grids" id="stat_container">
						
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-tab">
			<div class="weui-tab__bd container" id="container" style="padding-top: 150px;"></div>
		</div>

		<div class="weui-tabbar tabbar">
			<a href="indexPage" class="weui-tabbar__item"> <span
				class="icon attendance"></span>
				<p class="weui-tabbar__label">考勤</p>
			</a> <a href="statisticsPage" class="weui-tabbar__item weui-bar__item--on"> <span
				class="icon statistics"></span>
				<p class="weui-tabbar__label">统计</p>
			</a>
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
		<!-- 考勤状态 -->
		<script type="text/template" id="stat_template">
			<a class="weui-grid js_grid">
				<span class="status0"><@=attendanceData.userStat.normalNum@></span>
				<p class="weui-grid__label">正常</p>
			</a>
			<a class="weui-grid js_grid">
				<span class="status1"><@=attendanceData.userStat.lateNum@></span>
				<p class="weui-grid__label">迟到</p>
			</a>
			<a class="weui-grid js_grid">
				<span class="status2"><@=attendanceData.userStat.leaveNum@></span>
				<p class="weui-grid__label">早退</p>
			</a>
			<a class="weui-grid js_grid">
				<span class="status3"><@=attendanceData.userStat.absenceNum@></span>
				<p class="weui-grid__label">缺勤</p>
			</a>
			<a class="weui-grid js_grid">
				<span class="status5"><@=attendanceData.userStat.holidayNum@></span>
				<p class="weui-grid__label">请假</p>
			</a>
			<a class="weui-grid js_grid">
				<span class="status4"><@=attendanceData.userStat.missingNum@></span>
				<p class="weui-grid__label">漏卡</p>
			</a>
		</script>
		
		
		<!-- 考勤列表 -->
		<script type="text/template" id="template">
			<@if(attendanceData.attendanceUserListVo.length==0){@>
				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__bd">
						<div class="weui-loadmore weui-loadmore_line">
  							<span class="weui-loadmore__tips">暂无会议</span>
						</div>
					</div>
				</div>
			<@}else{@>
				<@ var dateArr = []@>
				<@_.each(attendanceData.attendanceUserListVo,function(item,index){@>
					<@
						getStatusMsg(Number(item.status));
					@>
					<!-- 会议考勤列表 -->
					<div class="weui-panel weui-panel_access">
						<div class="weui-panel__hd">
							<@ var meetingDate = item.meetingBeginTime.split(" ")[0]@>
							<@if($.inArray(meetingDate, dateArr)==-1){@>
								<@dateArr.push(meetingDate)@>
								<@if(meetingDate == beginDate){@>
									 今天
								<@}else{@>
									<@=meetingDate@>
								<@}@>
							<@}else{@>
								
							<@}@>
						</div>
						<div class="weui-panel__bd">
							<div class="weui-media-box weui-media-box_small-appmsg cells">
								<!-- 会议信息 -->
								<div class="weui-cells">
									<div class="weui-cells__title">
										<p><@=item.meetingName@></p>
										<span class="status 
											<@if(item.meetingStatus==0){@>init">未开始<@}@>
											<@if(item.meetingStatus==1){@>going">进行中<@}@>
											<@if(item.meetingStatus==2){@>end">已结束<@}@>
										</span>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<img src="${ctx }/resources/images/icon/place.png">
										</div>
										<div class="weui-cell__bd">
											<p><@=item.placeName@></p>
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd" style="position: relative;top: -8px;">
											<img src="${ctx }/resources/images/icon/time.png">
										</div>
										<div class="weui-cell__bd">
											<div class="weui-flex">
												<div class="weui-flex__item">开始：<@=item.meetingBeginTime.split(" ")[1]@></div>
  												<div class="weui-flex__item">结束：<@=item.meetingEndTime.split(" ")[1]@></div>
											</div>
											<div class="weui-flex">
												<div class="weui-flex__item">签到：<@if(item.modifySignTime==null){@>--<@}else{@><@=item.modifySignTime.split(" ")[1]@><@}@></div>
  												<div class="weui-flex__item">签退：<@if(item.modifyLogoutTime==null){@>--<@}else{@><@=item.modifyLogoutTime.split(" ")[1]@><@}@></div>
											</div>
										</div>
										<div class="weui-cell__ft <@=statusClazz@>"><@=statusMsg@></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				<@})@>
			<@}@>
		</script>


	</div>


	<script>
		$(function() {
			FastClick.attach(document.body);

			init(getDateStr('-30'), beginDate);

			// 点击日历图标时，计算周数
			$('#date-picker').val(getDateStr('-30')+' ~ ' + beginDate);
			$('#date-picker').dateRangePicker({
				separator : ' ~ ',
				showShortcuts:false,
				autoClose: true
			}).bind('datepicker-change',function(e,r){//日期变化
				try{
					$('#date-picker').val(r.value);
					init(moment(r.date1).format('YYYY-MM-DD'),moment(r.date2).format('YYYY-MM-DD'))
				}catch(e){

				}
			});
			
		})

		function init(beginDate, endDate) {
			$.ajax({
				url : '${ctx}/webapi/meeting/v1/mobile/attendances-stat',
				type : "GET",
				cache : false,
				data : {
					'userId':'${currentUser.id}',
					'beginDate' : beginDate,
					'endDate' : endDate
				},
				beforeSend : function() {
					$('#container').html(_.template($("#loading").html()));
					$('#stat_container').html(_.template($("#loading").html()));
				},
				success : function(data) {
					attendanceData = data;
					$('#stat_container').html(_.template($("#stat_template").html()));
					$('#container').html(_.template($("#template").html()));

				}
			})
		}
	</script>

</body>

</html>
