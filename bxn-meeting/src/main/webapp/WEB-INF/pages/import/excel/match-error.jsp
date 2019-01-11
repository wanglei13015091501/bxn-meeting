<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div style="text-align: left;font-size: 14px;color: red;">
	Excel格式或数据不正确。请修正后重试。具体原因如下：
</div>
<ul style="text-align: left;list-style: inside;color: red;">
	<c:forEach items="${vInfo }" var="var">
		<li>${var }</li>
	</c:forEach>
</ul>
<div class="toolbar-center" style="margin-top: 15px;">
	<a href="javascript:;" class="btn btn-common cancel"> <span
		class="left"></span><span class="right"><span
			class="ico ico-cancel"></span>关闭</span>
	</a>
</div>