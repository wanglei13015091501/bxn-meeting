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
            $(".no-print").hide();
            window.print();
		});
	</script>
	<!--[if IE 6]>
	<script type="text/javascript" src="${ctx}/resources/skin/${skinType}/fixIe6Bug.js"></script>
	<![endif]-->
</head>
<body>
	<div class="print-area">
		<decorator:body />
	</div>
</body>
</html>