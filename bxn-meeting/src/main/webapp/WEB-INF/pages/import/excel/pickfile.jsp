<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>数字校园平台-文件导入-文件选择</title>
<head>
<META name="decorator" content="blank">
<style type="text/css">
</style>
<script type="text/javascript">
	$(function() {
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
		$("#form1").bxnForm();
		$(".cancel").click(function(){
			$.ajax({
				url : '${ctx}/data/import/excel/clear',
				type : 'POST'
			});
			self.close();
		});
	});
	function fileTypeValidate(value, element) {
		if (value != "" && value.indexOf(".") != -1) {
			var subFix = value.substr(value.lastIndexOf(".")).toLocaleLowerCase();
			if (subFix == ".xls" || subFix == ".xlsx") {
				return true;
			}
		}
		return false;
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
					<div class="i-title"><span class="ico ico-guide"></span>操作流程</div>
					<div class="import-flow-img1"></div>
				</div>
				<div class="import-operator">
					<div class="i-title"><span class="ico ico-import"></span>导入文件</div>
					<form id="form1" action="${ctx }/data/import/excel/match"
						method="post" enctype="multipart/form-data" class="form">
						<div>
							<label>选择导入文件：</label><input type="file" name="excelFile"
								selfV="fileTypeValidate" selfV-tip="文件必须是Excel。" required
								required-tip="请选择要上传的文件。" />
						</div>
						<div class="toolbar-center" style="margin-top: 15px;">
							<a href="javascript:;" class="btn btn-common"
								onclick="$('#form1').submit();"> <span class="left"></span>
								<span class="right"><span class="ico ico-next"></span>下一步</span>
							</a> <a href="javascript:;" class="btn btn-common cancel"> <span
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