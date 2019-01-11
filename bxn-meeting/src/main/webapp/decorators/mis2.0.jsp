<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="icon" href="${customResourceUri }/favicon.ico" />
	<%@ include file="/commons/resource.jsp"%>
	<title><decorator:title default="${platFormName }" />_${schoolName }</title>
	<meta charset="utf-8">
	<meta http-equiv="Cache-Control" content="no-store">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript" charset="utf-8" src="${staticResRoot }/skin/${skinType}/auto-page-width.js"></script>
	<decorator:head />
	<script type="text/javascript">
		$(function() {
			$(".page-nav .menu-container .dropdown-menu").dropdownMenu();
			var contentPanel = $("#bxn-main-panel-content");
			if(contentPanel.height() < 200) {
				contentPanel.height(200);
			}
			//error info message
			var errorMsg = '<bxn:feedback type="1"/>';
			if(typeof errorMsg == "string" && ""!=errorMsg){
				$.bxn.tools.showRemindInfo({message:errorMsg,state:"error"});
			}else{
				//success info message
				var successMsg = '<bxn:feedback type="0"/>';
				if(typeof successMsg == "string" && ""!=successMsg){
					$.bxn.tools.showRemindInfo({message:successMsg,state:"success"});
				}
			}
		});
	</script>
	<!--[if IE 6]>
	<script type="text/javascript" src="${staticResRoot }/script/lib/jquery.pngFix.pack.js"></script>
	<script type="text/javascript" src="${staticResRoot }/skin/${skinType}/fixIe6Bug.js"></script>
	<![endif]-->
</head>

<body
	<decorator:getProperty property="body.class" writeEntireProperty="true"/>>
	<div class="page-head">
		<div class="head-bg">
			<img alt="" src="${staticResRoot }/skin/${skinType}/images/head-bg.png"
				style="width: 100%; heihgt: 65px;" width="100%" height="65">
		</div>
		<div class="head-logo-bg">
			<img alt="" src="${staticResRoot }/skin/${skinType}/images/logo-bg.png"
				style="width: 673px; heihgt: 65px;" width="673" height="62">
		</div>
		<div class="head-logo">
			<img alt="" src="${customResourceUri }/logo.png"
				style="width: 437px; heihgt: 65px;" width="437" height="65"><img alt="" src="${ctx }/resources/images/system-title.png" 
				style="width: 236px; heihgt: 65px;" width="236" height="65">
		</div>
		<div class="user-info">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td class="head-image" rowspan="2">
						<img src="${headImageUrl}/tiny/${currentUser.id }"/>
					</td>
					<td class="label-td">用户：</td>
					<td class="content-td">${ currentUser.username }</td>
				</tr>
				<tr>
					<td class="label-td" style="text-align: right;padding-left: 10px">姓名：</td>
					<td class="content-td">${ currentUser.fullName }</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="page-nav">
			<div class="page-nav-bg">
				<img alt="" src="${staticResRoot }/skin/${skinType}/images/nav-bg.png"
					style="width: 100%; heihgt: 33px;" width="100%" height="33">
			</div>
			<div class="nav-container">
				<div class="nav-container-bg">
					<img alt="" src="${staticResRoot }/skin/${skinType}/images/nav-container-bg.png"
						style="width: 100%; heihgt: 33px;" width="100%" height="33">
				</div>
				<div class="menus">
					<c:if test="${hasMenu }">
						<div class="top-menu">
							<span class="top-arrow"></span>
							<ul>
								<li class="top"></li>
								<c:forEach items="${current_menu }" var="menu">
									<c:if test="${menu.selected }">
										<c:set var="current_display_menu" value="${menu}"></c:set>
									</c:if>
									<li>
										<a href="${ctx}${menu.url}">${menu.text}</a>
									</li>
								</c:forEach>
								<li class="bottom"></li>
							</ul>
						</div>
						<ul class="menu-container">
							<li class="super-menu">
								<a href="javascript:;" class="dropdown-menu">${current_display_menu.text }<span class="super-menu-ico"></span></a>
							</li>
							<c:forEach items="${current_display_menu.children }" var="v">
								<li class="common-menu<c:if test="${v.selected }"> active</c:if>" >
									<a href="${ctx}${v.url}">${v.text }</a>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<div class="back-buttons">
					<a href="javascript:;" class="top-btn-help" title="帮助"></a>
					<a href="javascript:;" class="top-btn-quick" title="快捷方式"></a>
					<a href="${ctx }${indexuri}" class="top-btn-back" title="返回"></a>
				</div>
			</div>
		</div>
	<div class="view-area">
		<div class="page-main">
			<table class="bxn-panel" style='margin:5px auto;width:<decorator:getProperty property="body.width" default="880px"/>;min-width:<decorator:getProperty property="body.minwidth" default="80%"/>;' align="center" cellpadding='0' cellspacing='0' border='0' >
				<tr>
					<td class="panel-top-left"></td>
					<td class="panel-top-center"></td>
					<td class="panel-top-right"></td>
				</tr>
				<tr>
					<td class="panel-left"></td>
					<td id="bxn-main-panel-content" class="panel-content" <decorator:getProperty property="body.style" writeEntireProperty="true"/>>
						<decorator:body />
					</td>
					<td class="panel-right"></td>
				</tr>
				<tr>
					<td class="panel-bottom-left"></td>
					<td class="panel-bottom-center"></td>
					<td class="panel-bottom-right"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="page-foot">
		<img alt="" src="${customResourceUri }/copy-right.png"
			style="width: 391px;heihgt:42px;left:40%;position: absolute;top: -3px" width="391" height="42">
	</div>
	<div class="page-bg">
		<img alt="" src="${staticResRoot }/skin/${skinType}/images/page-bg.jpg"
			style="width: 100%" width="100%">
	</div>
</body>
</html>