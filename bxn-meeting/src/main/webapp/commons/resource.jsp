<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@page import="cn.boxiao.bxn.common.util.PropertiesAccessorUtil"%>
<%@ include file="/commons/config.jsp"%>
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/global.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/template.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/buttons.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/icons.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/form-layout.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/data-table.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/dialog.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/tab.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/tree.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/panel.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/pagination.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/component.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/reminder.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/tip-box.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/file-uploadify.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/skin/${skinType}/css/error-page.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot}/skin/${skinType}/css/excel-import.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot}/skin/${skinType}/css/loading.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/script/lib/uploadify/uploadify.css" />
<link type="text/css" rel="stylesheet" href="${staticResRoot }/script/lib/sceditor/minified/themes/default.min.css" />
<script type="text/javascript">
	var version ='${version}';
	var bxnStaticResRoot = '${staticResRoot}';
	var jsCtx = '${ctx}';
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
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/sceditor/minified/jquery.sceditor.bbcode.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResRoot }/script/lib/sceditor/languages/cn.js"></script>
<div id="BROWER-IE9-TIP" style="display:none;z-index:9999;position:fixed;top:0;width:100%;height:62px;color:#FFF;background:url('${staticResRoot }/images/brower-tip-bg.png');font-size:18px;text-align:center;">
	<img style="margin-right: 6px;" align="absmiddle" src="${staticResRoot }/images/brower-tip-error.png"/>
	<span style="margin: 18.5px 0px 18.5px;display: inline-block;">
		您的浏览器版本太低，请使用"IE9以上浏览器"或"谷歌浏览器"(<a style="color:red;text-decoration:none;" target="_blank" href="/resourcenormal/download/ChromeStandaloneSetup.exe">点击下载 </a>)，
		使用360浏览器<a style="color:yellow;text-decoration:none" target="_blank" href="http://help.boxiao.cn/static/360help.html">点此查看360浏览器说明</a></span>
</div>
<script type="text/javascript">
$(function(){
	var browerinfo = $.bxn.tools.getBrowserInfo();
	if(!browerinfo){
		$("#BROWER-IE9-TIP").show();
	}
	var userId='${pageScope.currentUser.id}';
	var userName='${pageScope.currentUser.username}';
	var fullName='${pageScope.currentUser.fullName}';
	if(userId!=''){
		var path=encodeURIComponent(document.location.href);
		fullName=encodeURIComponent(fullName);
		var title=encodeURIComponent(document.title);
		var objMetaArray=document.getElementsByTagName("meta");
		var metaInfos='{';
		var first=true;
		for(var i=0;i<objMetaArray.length;i++)
		{
			var metaName=objMetaArray[i].name;
			var metaContent=objMetaArray[i].content;
			if(metaName.indexOf("bxn")==0){
				if(!first){
					metaInfos+=',';
				}
				metaInfos+='\"'+metaName+'\":\"'+metaContent+'\"';
				first=false;
			}
		}
		metaInfos+='}';
		metaInfos=encodeURIComponent(metaInfos);
		var stateContent=$.ajax({
			type: 'GET',
			dataType: 'script',
			url: '${platformurl}/dynamic/resources/statusgather.js?userId='+userId+'&path='+path+'&userName='+userName+'&fullName='+fullName+'&title='+title+'&metaInfos='+metaInfos,
			cache: true
		});
	}

	//unified help system
	$(".top-btn-help").click(function(){
		var uniqueSign = window.location.href.substring($.bxn.tools.getProtocolAndHostName().length);
		if(uniqueSign.indexOf('?')!=-1){
			uniqueSign = uniqueSign.substring(0,uniqueSign.indexOf('?'));
		}
		var help_url = "${helpsystemurl}";
		if(typeof(currentAppKey)!="undefined" && currentAppKey.length>0){
			help_url += "/"+currentAppKey+"/";
		}
		var help_width = document.body.offsetWidth-126;
		var help_height = $(window).height()-126;
		$("<iframe class='help-iframe' src='"+help_url+"'></iframe>").bxnDialog({
			title:"系统帮助",
			width:help_width,
			height:help_height,
			ico:"ico-help",
			onClosed:function(jq){
				$(jq).bxnDialog("destroy");
			}
		}).bxnDialog("show");
	});

	//unified quick links system
	if(userId!=''){
		$(".top-btn-quick").click(function(){
			var title=$.trim($('title:eq(0)').html());
			var uniqueSign = encodeURIComponent(window.location.href);
			$("<iframe class='quick-iframe' src='${portalurl}/user/commonusedshortcut/form?unwrap=true&title="+title+"&path="+uniqueSign+"'></iframe>").bxnDialog({
				title:"添加常用链接",
				width:500,
				height:250,
				ico:"ico-query1",
				onClosed:function(jq){
					$(jq).bxnDialog("destroy");
				}
			}).bxnDialog("show");
		});
	}
});
function closeQuickLinkWindow(){
	$(".quick-iframe").bxnDialog("close");
}
</script>