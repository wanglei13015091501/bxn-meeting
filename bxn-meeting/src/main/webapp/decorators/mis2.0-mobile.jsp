<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/config.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/meta-m.jsp"%>
		<title><decorator:title default="数字校园6.0" /></title>
		<%@ include file="/commons/resource-m-css.jsp"%>
		<style type="text/css">
			.app-nav-bar{
				position:absolute;
				left:0;right:0;top:0;
				height: 40px;width: 100%;
				font-weight: bold;
				font-size: 1.6rem;
				background-color: #000;
				color: #fff;
				text-align: center;
				padding: 0px 50px;
				line-height: 40px;
				box-shadow: 0 0 4px 0px rgba(0,0,0,.8);
			}
			.app-nav-bar .menu-left{
				width: 50px;
				position: absolute;
				left: 0;right: auto;top: 0;bottom: 0;
			}
			.app-nav-bar .menu-right{
				width: 50px;
				position: absolute;
				left: auto;right: 0;top: 0;bottom: 0;
			}
		</style>
	</head>
	<body class="ducument-root">
		<div class="app">
			<!--in iboxiao hide this  -->
<!-- 			<div class="app-nav-bar">
				<div class="btn-group menu-left"><span class="icon-arrow-left"></span></div>
				<span id='page-title'></span>
				<div class="btn-group menu-right goto-btn" app-target="index.jsp"><span class="icon-home"></span></div>
			</div> -->
			<div class="app-body">
				<decorator:body />
			</div>
		</div>
		<%@ include file="/commons/resource-m-js.jsp"%>
		<decorator:head />
		<script type="text/javascript">
			//全局返回接口，app调用
            function bxm_back(){
            	if(window.pageBack){
            		window.pageBack();
            	}else{
            		window.history.go(-1);
            	}
            }
			//处理系统菜单
			$(function(){
				var appBody = $("div.app-body"),
					bottomMenu = appBody.find("ul.bottom-menu"),
					menuItem = bottomMenu.find("li.menu-item");
				if(bottomMenu.length>0){
					if(menuItem.length>1){
						appBody.children(".app-content").addClass("bottom-space-in").children(".scrollable").addClass("s-44");
						menuItem.each(function(){
							if(window.location.href.indexOf(this.getAttribute("app-target"))>0 && this.className.indexOf("active")==-1){
								this.className+=" active";
							}
						});
					}else{
						appBody.children(".app-content").removeClass("bottom-space-in").children(".scrollable").removeClass("s-44");
						bottomMenu.remove();
					}
				}
				
			});
			if(!window.getPageTitle){
				window.getPageTitle = function(){
					return document.title;
				};
			}
			//android设置标题
			window.pageReady = function(){
				window.BxNativeBridge.setBxTitle(window.getPageTitle());
			};
			//IOS设置标题
			document.addEventListener('WebViewJavascriptBridgeReady', function(event) {
				WebViewJavascriptBridge.send(window.getPageTitle());
            }, false);
		</script>
	</body>
</html>