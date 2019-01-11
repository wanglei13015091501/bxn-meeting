<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<head>
    <title>批量导入会议地点</title>
    <style type="text/css">
    </style>
    <script type="text/javascript">
        $(function() {
            $(".btn-enter").click(function() {
                $.bxn.tools.openNewWindow('${ctx}/data/import/excel/init/excelImportPlaceService');
                window.event.returnValue = false;
            });

        	 // 下载模板
    		$(".download-template").click(function() {
    			window.location.href="${ctx}/webapi/meeting/v1/place-templates";
    			window.event.returnValue = false;
    	    });

            //返回
            $(".btn-back").on("click",function(){
                window.location.href="${ctx}/meeting/place/indexPage";
                window.event.returnValue = false;
            });
        });
    </script>
</head>
<body width="100%">
<div class="data-table">
    <div class="head">批量导入会议地点</div>
    <div style="padding-left:5%;padding-bottom:20px;">
        <div>
            <p><strong>操作提示：</strong></p>
            <p>1. 请先下载Excel模板文件（点击选择框下的“下载模板”），并将其另存为一个新文件。</p>
            <p>2. 在该文件中输入会议地点信息，注意遵守以下限制条件：</p>
            <p style="text-indent:15px;color: red;">a. 表头的第一列和第二列，即“会议地点编码”、“会议地点名称”不能为空；</p>
            <p style="text-indent:15px;color: red;">b. 请勿修改第一行表头中的文字，但是不需要更新导入的列可以删除。</p>
            <p style="text-indent:15px;color: red;">c. excel中的所有内容都需要用加上边框。</p>
            <p>3. 信息整理完毕后，保存该文件。</p>
            <p>4. 选择文件上传，因为数据量较大，处理时间可能较长，请耐心等待，如果出错，请按照提示进行修改。</p>
            <p style="color: red;">5. 不支持更新操作，会议地点编码和名称必须唯一。</p>
        </div>
        <h5><strong>第一步：<a class="common-link download-template" href="javascript:void(0)"><span style="font-size: 16px;">下载模板</span></a></strong></h5>
        <h5><strong>第二步：进入导入管理页面</strong></h5>
            <span class="toolbr">
                <a href="javascript:void(0)" class="btn btn-common btn-enter">
                    <span class="left"></span>
                    <span class="right"><span class="ico ico-import"></span>进入</span>
                </a>
            </span>
            <span class="toolbr">
                <a href="javascript:void(0)" class="btn btn-common btn-back">
                    <span class="left"></span>
                    <span class="right"><span class="ico ico-back"></span>返回</span>
                </a>
            </span>
    </div>
    <div class="foot"></div>
</div>
</body>
  