<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.lang.*,java.io.*,java.util.*,cn.boxiao.bxn.common.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="org.springframework.context.support.ResourceBundleMessageSource" %>
<%@ page import="cn.boxiao.bxn.common.util.SpringContextUtils" %>
<%@ page import="cn.boxiao.bxn.common.util.ExceptionInfoExtractor" %>
<title>数字校园平台</title>
<head>
    <META name="decorator" content="fly-show">
    <style type="text/css">
    	.erde{
		    width: 60%;
		    margin: auto;
		    word-break: break-all;
    	}
    </style>
</head>
<body>
<div class="error-page error-500">
	<div class="error-page-message">
			出错了
	</div>
    <div class="toolbar-center">
    	<a href="javascript:void(0);"  onclick="$('.error-detail').show()" class="btn-control bct-default show-detail"><span class="ico-search"></span>查看详细</a>
    	<a href="${ pageContext.request.contextPath}" class="btn-control bct-green back"><span class="ico-link"></span>返回首页</a>
    </div>
</div>
<div class="error-detail erde">
    <%
        Throwable ex = (Throwable) request.getAttribute("ex");
        if(ex==null)
            ex=exception;
        String exceptionStr = "";
        if(ex!=null){
            Map<String,Object> infos= ExceptionInfoExtractor.getExceptionInfo(request,ex);
            String code=(String)infos.get("code");
            String message=(String)infos.get("message");
            String originCode=(String)infos.get("originCode");
            List<String> transmitSystems=(List<String>)infos.get("transmitSystems");
            List<String> exceptions=(ArrayList<String>)infos.get("exceptions");
            List<Map<String,Object>> requestParams = (ArrayList<Map<String,Object>>)infos.get("requestParams");

            exceptionStr+="<span class='error-detail-title'><a href='http://bxn-help.boxiao.cn/help?errorCode="+code+"' target='_blank'>"+code+"</a>&nbsp;&nbsp;"+message + "</span><br/>";
            exceptionStr+="originCode:"+originCode+"<br/>";
            exceptionStr+="transmitSystem:";
            for(String ts:transmitSystems){
                exceptionStr+=ts+",";
            }
            exceptionStr+="<br/>";
            exceptionStr+="exceptions:<br/>";
            for(String e:exceptions){
                exceptionStr+=e.replaceAll("\n", "<br/>\n").replaceAll("\r", "&nbsp;").replaceAll("\t", "&nbsp;&nbsp;");
            }
            exceptionStr+="<br/>";

            exceptionStr+="request params:";
            for(Map<String,Object> params:requestParams){
                exceptionStr+="-----------------------------------<br/>";
                Set<String> keySets=params.keySet();
                for(String key:keySets){
                    Object value=params.get(key);

                    String outValue=null;
                    if(value == null){
                        outValue="null";
                    }else if(value instanceof String){
                        outValue=(String)value;
                    }else if(value instanceof  String[]){
                        String[] values=(String[])value;
                        outValue+="[";
                        for(String v:values){
                            outValue+=v+",";
                        }
                        outValue+="]";
                    }else{
                        outValue=value.toString();
                    }
                    exceptionStr+=key+":"+outValue+"<br/>";
                }
            }
        }
    %>
    <%= exceptionStr %>
</div>
</body>