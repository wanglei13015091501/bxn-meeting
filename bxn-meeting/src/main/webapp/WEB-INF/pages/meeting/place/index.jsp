<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>会议地点管理</title>
<head>
</head>
<body width="95%">
<div class="data-table" id="table_fixed_container">
    <div class="head">会议地点列表</div>
    <form id="query-form">
        <div class="query-area">
            <table id="query-table" style="width: 100%">
                <tbody>
                <tr>
                    <td style="width: 350px">
                        <div style="float: left;margin-left: 27px">
                            <b>会议地点查询：</b>
                            <input id="queryInput"  style="width: 200px;" type="text" value="${queryContent}" placeholder="请输入会议地点名称或编码">
                        </div>
                    </td>
                    <td style="width: 100px">
                        <a id="queryBtn" href="javascript:;" class="btn btn-common btn-query" style="margin-left:5px">
                            <span class="left"></span>
                            <span class="right">
                                <span class="ico ico-search"></span>
                                查询
                            </span>
                        </a>
                    </td>
                    <td width="100%">

                    </td>

                    <td width="160px">
                        <a id="addBtn" href="javascript:;" class="btn btn-common btn-add" style="margin-right:27px;float: right">
                            <span class="left"></span>
                                <span class="right">
                                    <span class="ico ico-add"></span>
                                    新建会议地点
                            </span>
                        </a>
                    </td>
                    <td width="160px">
                        <a id="importBtn" href="javascript:;" class="btn btn-common btn-enter" style="margin-right:27px;float: right">
                            <span class="left"></span>
                                <span class="right">
                                    <span class="ico ico-import"></span>
                                    导入会议地点
                            </span>
                        </a>
                    </td>
                    <td width="160px">
                        <a id="exportBtn" href="javascript:;" class="btn btn-common btn-export" style="margin-right:27px;float: right">
                            <span class="left"></span>
                                <span class="right">
                                    <span class="ico ico-export"></span>
                                    导出会议地点
                            </span>
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </form>
    <form id="placeSaveForm">
        <table width="95%" class="table" align="center">
            <thead id="placeHead">
                <tr id="firstTr">
                    <td class='d-td' width="50">序号</td>
                    <td class='d-td' width="30%">会议地点编码</td>
                    <td class='d-td' width="30%">会议地点名称</td>
                    <td class='d-td' width="40%">会议地点描述</td>
                    <td class='d-td' width="200">操作</td>
                </tr>
            </thead>
            <tbody id="placeTBody">
            <c:forEach items="${places}" var="place" varStatus="varStatus">
                <c:set var="place" value="${place }" scope="request"></c:set>
                <c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
                <%@ include file="_info.jsp"%>
            </c:forEach>
            </tbody>
        </table>
    </form>
    <div class="foot"></div>
</div>
<script type="text/javascript">

	/**
	 * 从url中获取某一个参数的值
	 * @param name(参数名)
	 * @return result(参数值)
	 */
	function getParamValue(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = decodeURI(window.location.search).substr(1).match(reg); 
		if (r != null) return r[2]; 
		return null; 
	}
	//提取查询参数，初始化搜索框
	var queryContent = getParamValue("queryContent");
	if(queryContent == null ){
		queryContent = '';
	}
	$("#queryInput").val(queryContent);
	
    $.bxn.lineoperator = {
        onEdit: function (e) {
            if ($.bxn.editing) {
                alert("页面有正在编辑的内容!");
            } else {
                var $tr = $(e.target).closest("tr");
                var index = $tr.children("td:first-child").text();
                var placeId = $tr.attr("id");
                currentTR = $tr.clone();
                url = "${ctx}/meeting/place/editPage?placeId="+placeId+"&unwrap=true";
                $.get(url).done(
                        function (formTR) {
                            console.log($(formTR));
                            var heads = $("#placeHead td");
                            var $formTR = $(formTR);
                            $formTR.children("td:first-child").text(index);
                            $formTR.find('td').each(function (i, o) {
                                $(this).find("input[type='text'],select").css("width", $(heads[i]).width() - 20);
                            });
                            $formTR.attr("class", $tr.attr("class"));
                            $tr.replaceWith($formTR);
                        }
                );
                $.bxn.editing = true;//编辑状态
                $.bxn.ifNew = false;//编辑
            }
        },
        onRemove:function(e){
            if ($.bxn.editing){
                alert("页面有正在编辑的内容！");
            }else{
                $.bxn.confirm({msg:"是否删除会议地点，删除之后在考勤时刻表中将无法选择该会议地点？",width:"350",height:"80"},function(){
                    var placeId = $(e.target).closest("tr").attr("id");
                    
                    var data = "_method=DELETE";
            		$.post('${ctx}/webapi/meeting/v1/places/'+ placeId,data,
            		function(msg) {
            			$.bxn.tools.showRemindInfo(msg);
            			 window.location.href="${ctx}/meeting/place/indexPage";
            		});
            		
                },function(){
                });
            }
        }
    };
    
    $(function(){
        $(".clear").click(function(e){
            if(confirm("确认要清除吗？")){
                $(e.target).closest("form").submit();
            }
        });
        //查询
        $("#queryBtn").click(function(){
            var value = $("#queryInput").val();
            window.location.href="${ctx}/meeting/place/indexPage?queryContent="+value;
        });
        //创建
        $("#addBtn").click(function(){
            if($.bxn.editing){
                alert("页面有正在编辑的内容！");
                return;
            }
            var $tr = $("#firstTr");
            currentTR = $tr.clone();
            url = "${ctx}/meeting/place/createPage?unwrap=true";
            $.get(url).done(function(formTR){
                var heads = $("#placeHead td");
                var $formTR = $(formTR);
                $formTR.find("td").each(function(i,o){
                   $(this).find("input[type='text']").css("width",$(heads[i]).width() - 20);
                });
                $("#placeTBody").prepend($formTR);

            });
            $.bxn.editing = true;
            $.bxn.ifNew = true;
        });
        //修改
        $(".btn-edit").click($.bxn.lineoperator.onEdit);
        //删除
        $(".btn-remove").click($.bxn.lineoperator.onRemove);
        
        // 导入
        $("#importBtn").click(function() {
            window.location.href="${ctx}/meeting/place/importPage";
        });

        //导出
        $("#exportBtn").click(function() {
            window.location = '${ctx}/webapi/meeting/v1/places-export';
            window.event.returnValue = false;
        });
    });
    //保存
    $("#placeSaveForm").bxnForm({
        ajaxSubmit:true,
        submitBtn:"saveBtn",
        url:"${ctx}/webapi/meeting/v1/places",
        type:"post",
        success:function(form,data){
			window.location.href = "${ctx}/meeting/place/indexPage";	
        }
    });
    
    	//修改保存
		$("#placeSaveForm").on("click","a.btn-editSave",function(e){
			var placeId = $("#placeId").val();
			var placeNo = $("#placeNo").val();
			if(placeNo.trim()==''){
				alert("会议地点编码不能为空！");
				return false;
			}
			if(placeNo.trim().length > 20){
				alert("会议地点编码字数不能超过20！");
				return false;
			}
			var placeName = $("#placeName").val();
			if(placeName.trim()==''){
				alert("会议地点名称不能为空！");
				return false;
			}
			if(placeName.trim().length > 20){
				alert("会议地点名称字数不能超过20！");
				return false;
			}
			
			var description = $("#description").val();
			url = "${ctx}/webapi/meeting/v1/places/" + placeId ;
			var data = "placeName=" + placeName +"&placeNo=" + placeNo + "&description=" + description + "&_method=PUT";
			$.ajax({
				  cache: true,
		            type: "POST",
		            url:url,
		            data:data,
		            async: true,
		            error: function(request) {
		            },
		            success: function(msg) {
		            	$.bxn.tools.showRemindInfo(msg);
						window.location.href = "${ctx}/meeting/place/indexPage";	
		            }
	        });
    })
    
    $("#placeSaveForm").on("click",".btn-save",function(){
        var placeId = $(this).closest("tr").attr("id");
        $("input[name='placeId']").val(placeId);
        $("#placeSaveForm").submit();
    });

    //取消
    $("#placeSaveForm").on("click", ".btn-cancel", function (e) {
        var $tr = $(e.target).closest("tr");
        var isnew = $.bxn.ifNew;
        if (isnew) {
            $tr.remove();
            $("tr").each(function () {
                var trClass = $(this).attr("class");
                if (trClass == "d-td dark") {
                    $(this).attr("class", "d-td");
                } else if (trClass == "d-td") {
                    $(this).attr("class", "d-td dark");
                }
            });
        } else {
            $tr.replaceWith(currentTR);
            currentTR.find(".toolbar-center .btn-edit").click($.bxn.lineoperator.onEdit);
            currentTR.find(".toolbar-center .btn-remove").click($.bxn.lineoperator.onRemove);
        }
        $.bxn.ifNew = false;
        $.bxn.editing = false;
    });

</script>
</body>
