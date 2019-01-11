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
	</div>
	<div class="view-area">
		<div class="page-main">
			<table class="bxn-panel" style='margin:5px auto;width:<decorator:getProperty property="body.width" default="880px"/>' align="center" cellpadding='0' cellspacing='0' border='0' >
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