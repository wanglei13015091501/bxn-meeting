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
<script src="${ctx }/resources/js/daterangepicker/moment.min.js"></script>
<script src="${ctx }/resources/js/daterangepicker/jquery.daterangepicker.js"></script>

<link rel="stylesheet" href="${ctx }/resources/js/daterangepicker/daterangepicker.css">
<script type="text/javascript" charset="utf-8"
	src="${staticResRoot }/script/lib/underscore/underscore-min.js"></script>
<script src="${ctx }/resources/js/jquery-weui/lib/fastclick.js"></script>
<script src="${ctx }/resources/js/jquery-weui/js/jquery-weui.js"></script>

<!-- <script
	src="http://wechatfe.github.io/vconsole/lib/vconsole.min.js?v=2.5.2"></script> -->
<link rel="stylesheet" href="${ctx }/resources/css/meeting.css">
<script type="text/javascript" src="${ctx }/resources/js/meeting.js"></script>
</head>

<body ontouchstart>
	<div class="page-body">
		<div class="weui-tab">
			<div class="weui-tab__bd container" id="container"></div>
		</div>

		<div class="weui-tabbar tabbar">
			<a href="indexPage" class="weui-tabbar__item weui-bar__item--on"> <span
				class="icon attendance"></span>
				<p class="weui-tabbar__label">考勤</p>
			</a> <a href="statisticsPage" class="weui-tabbar__item"> <span
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

		<!-- 考勤列表 -->
		<script type="text/template" id="template">
			<@if(attendanceData.length==0){@>
				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__hd" id="week-picker" style="height:20px">
						<span class="icon calendar open-popup"></span>
					</div>
				<div class="weui-panel__bd">
					<div class="weui-loadmore weui-loadmore_line">
  						<span class="weui-loadmore__tips">暂无会议</span>
					</div>
				</div>
			<@}else{@>
				<@ var dateArr = []@>
				<@_.each(attendanceData,function(item,index){@>
					<!-- 会议考勤列表 -->
					<div class="weui-panel weui-panel_access">
						<div class="weui-panel__hd" <@if(index==0){@>id="week-picker"<@}@>>
							<@if(index==0){@>
								<span class="icon calendar open-popup"></span>
							<@}@>

							<@ var meetingDate = item.beginTime.split(" ")[0]@>
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
								<@
									var organizerIds = item.organizerIds.split(',');
								@>
								<@if("${perm}"=="admin" || ("${perm}"=="owner" && $.inArray('${currentUser.id}', organizerIds)!=-1)){@>
									<a class="weui-cells" href="detailPage?id=<@=item.meetingId@>">
								<@}else{@>
									<a class="weui-cells" href="javascript:;">
								<@}@>
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
											<p style="float: left;"><@=item.beginTime.split(" ")[1]@> － <@=item.endTime.split(" ")[1]@></p>
											<a href="groupStatisticsPage?id=<@=item.meetingId@>" style="float: right;color: #66CCFF;margin-right: -10px;">部门统计</a>
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<img src="${ctx }/resources/images/icon/place.png">
										</div>
										<div class="weui-cell__bd">
											<p><@=item.placeName@></p>
										</div>
										<div class="weui-cell__ft"><@=item.userNum@>人参加</div>
									</div>
								</a>
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

			init(beginDate, endDate);
		})

		function init(beginDate, endDate) {
			$.ajax({
				url : '${ctx}/webapi/meeting/v1/mobile/attendances',
				type : "GET",
				cache : false,
				data : {
					'beginDate' : beginDate,
					'endDate' : endDate
				},
				beforeSend : function() {
					$('#container').html(_.template($("#loading").html()));
				},
				success : function(data) {

					attendanceData = data;
					$('#container').html(_.template($("#template").html()));

					$('.date-picker-wrapper').remove();

					$('#container #week-picker').dateRangePicker({
						separator : ' ~ ',
						startOfWeek: 'monday',
						batchMode: 'week', 
						showShortcuts:false,
						autoClose: true
					}).bind('datepicker-change',function(e,r){//日期变化
						try{
							init(moment(r.date1).format('YYYY-MM-DD'),moment(r.date2).format('YYYY-MM-DD'))
						}catch(e){

						}
					});
					$('#container #week-picker').data('dateRangePicker').setDateRange(beginDate,endDate);


				}
			})
		}
	</script>

</body>

</html>
