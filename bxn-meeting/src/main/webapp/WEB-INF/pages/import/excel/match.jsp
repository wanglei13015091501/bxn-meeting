<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>数字校园平台-文件导入-数据匹配</title>
<head>
<META name="decorator" content="blank">
<style type="text/css">
</style>
<script type="text/javascript">
	$(function() {
		$(".import-operator").on("click", ".colMatched .ico-lamp-on", function() {
			var parent = $(this).removeClass("ico-lamp-on").addClass("ico-lamp-closed").closest("tr");
			parent.removeClass("colMatched").addClass("colUnMatched").find("select.property-slct").val("");
		});
		$(".import-operator").on(
				"change",
				".property-slct",
				function() {
					if ($(this).val() == "") {
						var parent = $(this).closest("tr");
						parent.removeClass("colMatched").addClass("colUnMatched").find(".ico-lamp-on").removeClass(
								"ico-lamp-on").addClass("ico-lamp-closed");
					} else {
						var parent = $(this).closest("tr");
						parent.removeClass("colUnMatched").addClass("colMatched").find(".ico-lamp-closed").removeClass(
								"ico-lamp-closed").addClass("ico-lamp-on");
					}
				});
		$(".cancel").click(function(){
			$.ajax({
				url : '${ctx}/data/import/excel/clear',
				type : 'POST'
			});
			self.close();
		});
	});
	function submitForm() {
		if (validatePageData()) {
			var loading = $.bxn.tools.showFullPageLoading("正在解析匹配Excel数据内容，请稍候...");
			var sdata = buildSubmitData();
			$.ajax({
				url : '${ctx}/data/import/excel/deal',
				data : sdata,
				dataType : 'json',
				type : 'POST',
				success : function(d) {
					$("#form1")[0].submit();
				},
				error : function(e) {
					$.bxn.tools.showRemindInfo({
						state : "error",
						message : "Excel解析异常，请检查Excel格式和数据后重试。"
					}, {
						width : 350
					});
				},
				complete : function() {
					$.bxn.tools.closeFullPageLoading(loading);
				}
			});
		}
	}
	function buildSubmitData() {//以数据库字段name为key,excel列头为value
		var result = [];
		$(".import-operator tr.colMatched").each(function() {
			var tds = $(this).find("td");
			if ('${mainColumnType}' == 'EXCEL') {
				result.push(decodeStr($(tds[2]).find("select.property-slct").val() + "=" + $(tds[1]).attr("data-key")));
			}else{
				result.push(decodeStr($(tds[1]).attr("data-key") + "=" + $(tds[2]).find("select.property-slct").val()));
			}
		});
		return result.join("&");
	}
	function decodeStr(str){
		return str ? str.replace(/%/g, "%25").replace(/\&/g, "%26").replace(/\+/g, "%2B"):str;
	}
	function validatePageData() {
		var mainType = '${mainColumnType}';
		var unMathed = $(".import-operator .colUnMatched");
		var errorMsg = [];
		if (unMathed.length > 0) {
			$.each(unMathed, function() {
				var text = $($(this).find("td")[1]).text();
				errorMsg.push("【" + text + "】 未匹配。");
			});
		}
		if ('${mainColumnType}' == 'EXCEL') {
			var required = findRequiredOptionsValue();
			if (required.length > 0) {
				var requiredText = findRequiredOptionsText();
				var matchedValue = findMatchedSelectedValues();
				for ( var i = 0; i < required.length; i++) {
					var added = false;
					checkMatched: for ( var j = 0; j < matchedValue.length; j++) {
						if (required[i] == matchedValue[j].val()) {
							added = true;
							break checkMatched;
						}
					}
					if (!added) {
						errorMsg.push("【" + requiredText[i] + "】 未被选择。");
					}
				}
				
				for ( var i = 0; i < matchedValue.length; i++) {
					
					if(matchedValue[i].val()!=""){
						var repeat = false;
						var x = 0;
						checkRepeat : for ( var j = 0; j < matchedValue.length; j++) {
							if (matchedValue[i].val() == matchedValue[j].val() && x++ > 0) {
								repeat = true;
								break checkRepeat;
							}
						}
						if (repeat) {
							errorMsg.push("【" + matchedValue[i].find("option:selected").text() + "】 被选择了多次。");
						}
					}
				}
			}
		}
		if (errorMsg.length == 0) {
			return true;
		} else {
			$.bxn.tools.showRemindInfo({
				state : "error",
				messageMap : errorMsg
			}, {
				width : 350
			});
			return false;
		}
		function findMatchedSelectedValues() {
			var values = [];
			$(".property-slct").each(function() {
				//values.push($(this).val());
				values.push($(this));
			});
			return values;
		}
		function findRequiredOptionsValue() {
			var required = [];
			var select = $(".property-slct");
			if (select.length > 0) {
				$(select[0]).find("option[required]").each(function() {
					required.push($(this).attr("value"));
				});
			}
			return required;
		}
		function findRequiredOptionsText() {
			var required = [];
			var select = $(".property-slct");
			if (select.length > 0) {
				$(select[0]).find("option[required]").each(function() {
					required.push($(this).text());
				});
			}
			return required;
		}
	}
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
					<div class="import-flow-img2"></div>
				</div>
				<div class="import-operator">
					<div class="i-title">
						<span class="ico ico-import"></span>数据匹配
					</div>
					<form id="form1" action="${ctx }/data/import/excel/imports" class="form"
						method="post" enctype="application/x-www-form-urlencoded">
						<c:if test="${empty vInfo }">
							<jsp:include page="match-success.jsp"></jsp:include>
						</c:if>
						<c:if test="${!empty vInfo }">
							<jsp:include page="match-error.jsp"></jsp:include>
						</c:if>
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