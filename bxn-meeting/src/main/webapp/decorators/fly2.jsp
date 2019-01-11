<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 需要调查用到此页面，此页面要删除掉 -->
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
    <div class="page-head-w">
        <div class="header">
             <div class="head-logo">
                 <img src="${ctx }/resources/images/system-logo.png"/>
             </div>
             <div class="user-info">
                <img src="${headImageUrl}/tiny/${currentUser.id }"/>
                <div class="info">
                   <span>欢迎</span>
                   <span>${ currentUser.fullName }</span>
                </div>
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
        北京师范大学附属实验中学 版权所有 ©2014
        <sec:authorize ifAnyGranted="PERM_DISCUSSLEARNING_ADMIN">
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="${ctx}/discusslearning/manager/disgroup/list" target="_blank" style="text-decoration: none;color:#FFF;">后台管理</a> 
		</sec:authorize>
    </div>
    <!--footEnd-->
</body>
</html>