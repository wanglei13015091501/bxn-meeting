<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" href="${customResourceUri }/favicon.ico" />
    <%@ include file="/commons/resource.jsp"%>
    <title><decorator:title default="${platFormName }" />_${schoolName }</title>
    <meta charset="utf-8">
    <meta http-equiv="Cache-Control" content="no-store">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <decorator:head />
    <script type="text/javascript" src="${staticResRoot }/skin/${skinType}/layout-items.js"></script>
    <script>
        $(function(){
            var licenseData=$.getJSON('${platformurl}/api/base-platform/validate-license?moduleName='+currentAppKey,function(licenseData){
                if(licenseData.status=='expired') {
                    $('#LICENSE-WARN-CONTENT').html('尊敬的客户您好，本模块的授权已到期。剩余使用日期' + licenseData.expired + '天');
                    $("#LICENSE-WARN").show();
                }else if(licenseData.status=='outservice'){
                    $('#LICENSE-WARN-CONTENT').html('尊敬的客户您好，本模块的授权已到期,服务已停止');
                    $("#LICENSE-WARN").show();
                }
            });

        });
    </script>
</head>
<body>
<div id="LICENSE-WARN" style="display:none;z-index:9999;position:fixed;top:0;width:100%;height:62px;color:red;background:url('${staticResRoot }/images/brower-tip-bg.png');font-size:18px;text-align:center;">
    <img style="margin-right: 6px;" align="absmiddle" src="${staticResRoot }/images/brower-tip-error.png"/>
    <span style="margin: 18.5px 0px 18.5px;display: inline-block;" id="LICENSE-WARN-CONTENT"></span>
</div>
<div class="index-view-area">
    <div class="index-page-head">
        <div class="head-left">
            <span>${schoolName }</span>
            <span class="product-name">${platFormName }</span>
        </div>
        <div class="head-right">
            <span class="system-name"><decorator:getProperty property="body.title" writeEntireProperty="false"/></span>
            <span class="system-desc"><decorator:getProperty property="body.text" writeEntireProperty="false"/></span>
        </div>
    </div>
    <div class="index-page-main">
        <decorator:body></decorator:body>
    </div>
</div>
<div class="index-page-bg">
    <img alt="" src="${staticResRoot }/skin/${skinType}/images/index-page-bg.jpg" width="100%" height="100%">
    <c:if test="${version ne null}">
    	<div style="position: absolute; bottom: 25px; right: 20px; color: #0071F0; font-style: italic;">版本:${version}</div>
    </c:if>
</div>
</body>
</html>