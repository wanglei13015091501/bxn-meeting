<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>数字校园平台-文件导入-数据导入</title>
<head>
<META name="decorator" content="blank">
<style type="text/css">
#dataPreview {
	width: 800px;
	overflow: auto;
	margin: auto;
	height: 400px;
}
</style>
<script type="text/javascript">
	function submitForm() {
		var loading = $.bxn.tools.showFullPageLoading("正在导入Excel数据内容，请稍候...");
		$.ajax({
			url : '${ctx}/data/import/excel/importData',
			dataType : 'json',
			type : 'POST',
			success : function(d) {
				$("#form1")[0].submit();
			},
			complete : function() {
				$.bxn.tools.closeFullPageLoading(loading);
			}
		});
	}
	$(function() {
		$(".cancel").click(function(){
			$.ajax({
				url : '${ctx}/data/import/excel/clear',
				type : 'POST'
			});
			self.close();
		});
	});
</script>

</head>
<body>

	<table class="bxn-panel" style='margin: 5px auto 38px; width: 880px;'
		align="center" cellpadding='0' cellspacing='0' border='0'>
		<tr>
			<td class="panel-top-left"></td>
			<td class="panel-top-center"></td>
			<td class="panel-top-right"></td>
		</tr>
		<tr>
			<td class="panel-left"></td>
			<td id="bxn-main-panel-content" class="panel-content" valign="top">
				<div class="import-flow">
					<div class="i-title">
						<span class="ico ico-guide"></span>操作流程
					</div>
					<div class="import-flow-img3"></div>
				</div>
				<div class="import-operator">
					<div class="i-title">
						<span class="ico ico-import"></span>导入文件
					</div>
					<form id="form1" action="${ctx }/data/import/excel/success"
						class="form" method="post"
						enctype="application/x-www-form-urlencoded">
						<div id="dataPreview" class="data-table">
							<table width="100%" class="table" align="center" id="realDTtable">
								<thead>
									<tr>
										<td class='d-td' width="60">序号</td>
										<c:forEach
											items="${ _excel_import_container_key_.colPropertyMapping }"
											var="var">
											<td class='d-td' width="220">${var.key }</td>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${ _excel_import_container_key_.bodyDatas }"
										var="cell_datas" varStatus="vs">
										<tr
											class="hoverable <c:if test="${ vs.index % 2 == 1 }">dark</c:if> ">
											<td class='d-td'>${vs.index + 1 }</td>
											<c:forEach
												items="${ _excel_import_container_key_.colPropertyMapping }"
												var="m">
												<td class='d-td'>${cell_datas[m.value] }</td>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="toolbar-center" style="margin-top: 15px;">
							<a href="javascript:;" class="btn btn-common"
								onclick="submitForm();"> <span class="left"></span> <span
								class="right"><span class="ico ico-next"></span>下一步</span>
							</a><a href="javascript:;" class="btn btn-common cancel"> <span
								class="left"></span><span class="right"><span
									class="ico ico-cancel"></span>取消</span>
							</a>
						</div>
					</form>
				</div>
				<c:if test="${ !empty msg }">
					<div class="import-msg">
						<div class="i-title">操作提示：</div>
						<c:set var="cn.boxiao.bxn.common.web.EscapeXmlELResolver.escapeXml" value="false"/> 
						${ msg }
						<c:set var="cn.boxiao.bxn.common.web.EscapeXmlELResolver.escapeXml" value="true"/>
					</div>
				</c:if>
			</td>
			<td class="panel-right"></td>
		</tr>
		<tr>
			<td class="panel-bottom-left"></td>
			<td class="panel-bottom-center"></td>
			<td class="panel-bottom-right"></td>
		</tr>
	</table>

</body>