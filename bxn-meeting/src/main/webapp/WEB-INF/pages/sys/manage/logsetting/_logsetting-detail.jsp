<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<head>
	<title>日志设置</title>
</head>
<body width="65%">
<div class="data-table">
    <div class="head">Log4J日志配置参数</div>
    <form class='input-layout-form'>
		<div>
			<table width="95%" cellpadding="0" cellspacing="0" border="0">
				<c:forEach items="${log4j}" var="item" varStatus="status">
					<tr>
						<td class="label" align="right" width="60"><label>${status.count }：</label></td>
						<td class="content">
							${item}
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="label"><label></label></td>
					<td class="content">
						<a id="log4jEditBtn" href="javascript:void(0)" class="btn btn-common" >
							<span class="left"></span>
							<span class="right"><span class="ico ico-save"></span>修改</span>
						</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
    <div class="foot"></div>
</div>
<script type="text/javascript">
$(function(){
	//日志设置修改
	$("#log4jEditBtn").click(function(){
		window.location.href="${ctx}/systemadmin/manage/logsetting/form";
	});
});
</script>
</body>
  