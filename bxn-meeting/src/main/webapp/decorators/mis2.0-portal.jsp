<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
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
  <link type="text/css" rel="stylesheet" href="<c:url value='/resources/managerPublish/css/base.css'/>" />
  <link rel="stylesheet" type="text/css" href="<c:url value='/resources/managerPublish/css/manager.css'/>"/>
  <script type="text/javascript" src="<c:url value='/resources/managerPublish/js/manager.js'/>"></script>
  
  
  <decorator:head/>
  	<script type="text/javascript">
  	var flag= true;
    $(function() {
        //error info message
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

        $("#myDevices").click(function(e){
            var date = new Date();
            var sDate = date.format("yyyy-MM-dd");
            var url = "${ctx}/publish/deviceMgr/deviceInfo?deviceId=board001&date="+sDate;
            console.log(url);
            window.location.href = url;
        });

    });
	</script>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
   	<div id="mainframe"  style='z-index:1;margin:0 auto;width:<decorator:getProperty property="body.width" default="100%"/>;height:100%;'>   
    	<div class="page-head">
        <div class="header">
             <div class="head-logo">
                 <img src="${ctx }/resources/managerPublish/images/img/logo.png"/>
                 信息发布系统
             </div>
             <div class="user-info">
                <img src="${ctx }/resources/managerPublish/images/img/img1.png"/>
                <div class="info">
                   <span>欢迎</span>
                   <span>初始化用户</span>
                </div>
             </div>            
             <div class="clear"></div>
        </div>
    </div>
    <!--headerEnd-->

    <!--tabStart-->
    <div class="page-tab">
      <ul>
        <li class="on">发布管理</li>
        <li id="myDevices">我的设备</li>
        <li>插播管理</li>
        <li>后台管理</li>
        <div class="clear"></div>
      </ul>
    </div>
    <!--footStart-->
    <div class="page-foot">
        北京博校文达科技有限公司 版权所有 ©2014
    </div>
    <!--footEnd-->
	<decorator:body/>
	</div> 	   
</body>
</html>