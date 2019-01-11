<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@page import="cn.boxiao.bxn.common.util.PropertiesAccessorUtil"%>
<%@ include file="/commons/config.jsp"%>
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/global.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/template.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/formelement.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/font.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/buttons.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/dialog.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/error-page.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/user-picker.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/fly/css/tree.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/script/lib/uploadify/uploadify.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/script/lib/sceditor/minified/themes/default.min.css" />
<!--[if lt IE 8]><!-->
<link rel="stylesheet" href="${staticResRoot }/fly/css/ie7.css">
<!--<![endif]-->

<script type="text/javascript">
	var bxnStaticResRoot = '${staticResRoot}';
	var jsCtx = '${ctx}';
	var jsessonId = '${pageContext.session.id}';
	var jsessionId = '${pageContext.session.id}';
    <%
        String currentAppKey = PropertiesAccessorUtil.getProperty("remote.current_app_key");
    %>
    var currentAppKey='<%=currentAppKey%>';
</script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/jquery-1.10.2.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/json2.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/jquery.bgiframe.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/jquery.form.3.46.0.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/bxn-configs.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/jquery.bxn.tools.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/jquery.bxn.control.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/jquery.bxn.component.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/bxn-messages_zh.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/bxn-domaction.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/sceditor/minified/jquery.sceditor.bbcode.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/sceditor/languages/cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/box/bxn.flybox.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/underscore/underscore-min.js"></script>

<div id="BROWER-IE9-TIP" style="display:none;z-index:9999;position:fixed;top:0;width:100%;height:62px;color:#FFF;background:url('${staticResRoot }/images/brower-tip-bg.png');font-size:18px;text-align:center;">
	<img style="margin-right: 6px;" align="absmiddle" src="${staticResRoot }/images/brower-tip-error.png"/>
	<span style="margin: 18.5px 0px 18.5px;display: inline-block;">
		您的浏览器版本太低，请使用"IE9以上浏览器"或"谷歌浏览器"(
		<a style="color:red;text-decoration:none;" target="_blank" href="/resourcenormal/download/ChromeStandaloneSetup.exe">点击下载 </a>)，
		使用360浏览器<a style="color:yellow;text-decoration:none" target="_blank" href="http://help.boxiao.cn/static/360help.html">点此查看360浏览器说明</a>
	</span>
</div>
<script type="text/javascript">
$(function(){
	var browerinfo = $.bxn.tools.getBrowserInfo();
	if(!browerinfo){
		$("#BROWER-IE9-TIP").show();
	}
});
</script>