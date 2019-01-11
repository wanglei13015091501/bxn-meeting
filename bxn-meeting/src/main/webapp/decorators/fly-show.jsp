<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="icon" href="${customResourceUri }/favicon.ico" />
	<%@ include file="/commons/resource-fly.jsp"%>
	<title><decorator:title default="${platFormName }" />_${schoolName }</title>
	<meta charset="utf-8">
	<meta http-equiv="Cache-Control" content="no-store">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<decorator:head />
</head>

<body
	<decorator:getProperty property="body.class" writeEntireProperty="true"/>>
	<!--headerStart-->
    <div class="page-head">
        <div class="header">
             <div class="head-logo">
                 <img class="sys-logo" src="${ctx }/resources/images/system-logo.png"/>
             </div>
             <div class="clear"></div>
        </div>
    </div>
    <!--headerEnd-->
    <!--mainAreaStart-->
    <div class="view-area">
    <!-- class:page-main-sec 为960px宽度的内容区域，如果有大于此宽度的区域，请自定义css，并使用媒介查询适应ipad横竖屏 -->
        <div <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
        	<decorator:body />
        </div>
    </div>
    <!--mainAreaEnd-->
    <!--footStart-->
    <div class="page-foot">
        北京博校文达科技有限公司 版权所有 ©2014
    </div>
    <!--footEnd-->
</body>
</html>