<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>     
<title>数字校园平台-文件导入-导入结果</title>
<head>
	<META name="decorator" content="blank">
	<style type="text/css">
		#dataPreview{
			width: 940px;
			overflow: auto;
			margin: auto;
			height: 400px;
		}
	</style>
	<script type="text/javascript">
	$(function() {
		$(".cancel").click(function(){
			$.ajax({
				url : '${ctx}/data/import/excel/clear',
				type : 'POST'
			});
			//var href = opener.location.href;
			//href = href.indexOf("?") == -1 ? href + "?" : href + "&";
			//var currentDate=new Date();
			//href += currentDate.getTime();
			//opener.location.href = href;
			self.close();
			
			if(opener['${callBack}']){
				opener['${callBack}']();
			}
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
					<div class="i-title"><span class="ico ico-guide"></span>操作流程</div>
					<div class="import-flow-img4"></div>
				</div>
				<div class="import-operator">
					<div class="i-title"><span class="ico ico-import"></span>导入结果</div>
					<form id="form1" action="${ctx }/data/import/excel/match"
						method="post" enctype="multipart/form-data" class="form">
						<div style="text-align: left;font-size: 14px;">
                            <c:if test="${statInfo.errored==0 }">
                                数据导入成功，共计导入了${statInfo.successed }条数据。
                            </c:if>
                            <c:if test="${statInfo.errored!=0 }">
                                成功导入${statInfo.successed }条数据，失败 ${statInfo.errored } 条。失败原因如下：
                            </c:if>
						</div>
						<ul style="text-align: left;list-style: inside;">
							<c:forEach items="${statInfo.errorMsg }" var="var">
								<li>${var }</li>
							</c:forEach>
						</ul>
						<div class="toolbar-center" style="margin-top: 15px;">
							<a href="javascript:;" class="btn btn-common cancel"> <span
								class="left"></span><span class="right"><span
									class="ico ico-cancel"></span>关闭</span>
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