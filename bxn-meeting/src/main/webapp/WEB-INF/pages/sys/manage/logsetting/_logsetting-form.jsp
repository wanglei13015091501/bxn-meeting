<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<head>
	<title>日志设置</title>
</head>
<body width="65%">
<div class="data-table">
    <div class="head">日志设置</div>
   	 <form class='input-layout-form'>
		<div>
			<table width="95%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td class="content" colspan="2">
							<textarea name="logContent" cols="120" rows="40">${log4j }</textarea>
						</td>
					</tr>
				<tr>
					<td class="label"><label></label></td>
					<td class="content">
						
						<a id="log4jSaveBtn" href="javascript:void(0)" class="btn btn-common" >
							<span class="left"></span>
							<span class="right"><span class="ico ico-save"></span>保存</span>
						</a>
						<a id="log4jCancelBtn" href="javascript:void(0)" class="btn btn-common" >
							<span class="left"></span>
							<span class="right"><span class="ico ico-cancel"></span>取消</span>
						</a>
						
						<a id="log4jResetBtn" href="javascript:void(0)" class="btn btn-common" >
							<span class="left"></span>
							<span class="right"><span class="ico ico-reset"></span>RESET</span>
						</a>
					</td>
				</tr>
			</table>
		</div>
		</form>
    <div class="foot"></div>
</div>
<script type="text/javascript">
$(function(){
	//日志设置
	$(".input-layout-form").bxnForm({
		ajaxSubmit:true,
		submitBtn:"log4jSaveBtn",
		url:"${ctx}/systemadmin/manage/logsetting/save",
		type:"post",
	    success:function(form,data){
	        if(data.state == 'success'){
	        	window.location = "detail";
	        	return;
	        }
	        $.bxn.tools.showRemindInfo(data);
	    }
	}); 
	$(".input-layout-form").on("click","#log4jResetBtn",function(){
		$.post('${ctx}/systemadmin/manage/logsetting/reset',null,function(data){
			 if(data.state == 'success'){
		        	window.location = "detail";
		        	return;
		     }
		     $.bxn.tools.showRemindInfo(data);
		});
	});
	$(".input-layout-form").on("click","#log4jSaveBtn",function(){
		$(".input-layout-form").submit();
	});
	
	//日志设置取消
	$("#log4jCancelBtn").click(function(){
		window.location.href="${ctx}/systemadmin/manage/logsetting/detail";
	});
});
</script>
</body>
  