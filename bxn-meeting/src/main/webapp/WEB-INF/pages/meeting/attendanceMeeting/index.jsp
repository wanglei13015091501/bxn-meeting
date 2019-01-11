<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>会议考勤管理</title>
<head>
</head>
<body width="95%">
<div class="data-table" id="table_fixed_container">
    <div class="head">会议考勤管理</div>
    <form id="query-form">
        <div class="query-area">
            <table id="query-table" style="width: 100%">
                <tbody>
                <tr>

                    <td width="130px">
                        <a id="addBtn" href="javascript:;" class="btn btn-common btn-add" style="margin-right:27px;float: right">
                            <span class="left"></span>
                                <span class="right">
                                    <span class="ico ico-add"></span>
                                    新建会议
                            </span>
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </form>
    <form id="meetingForm">
        <table width="95%" class="table" align="center">
            <thead id="placeHead">
                <tr id="firstTr">
                    <td class='d-td' width="30">序号</td>
                    <td class='d-td' width="250">会议名称</td>
                    <td class='d-td' width="100">会议地点</td>
                    <td class='d-td' width="220">会议时间</td>
                    <td class='d-td' width="50">参会人员</td>
                    <td class='d-td' width="80">操作</td>
                </tr>
            </thead>
            <tbody id="placeTBody">
            <c:forEach items="${meetingVoPage.content}" var="meeting" varStatus="varStatus">
                <c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
                <tr id="${meeting.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
			    <td class='d-td'>${rowIndex+1}</td>
			    <td class='d-td '>${meeting.meetingName}</td>
			    <td class='d-td '>${meeting.placeName}</td>
			    <td class='d-td '>${meeting.beginTime}--${meeting.endTime}</td>
			    <td class='d-td '>${meeting.userNum}</td>
			    <td class='d-td'>
			        <div class="toolbar-center">
			         <a href="javascript:void(0)" class="btn btn-inline btn-detail">
			                <span class="left"></span>
			                <span class="right"><span class="ico ico-user-group"></span>考勤情况</span>
			            </a>
			        </div>
			    </td>
				</tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
   				<div style="text-align: center;">
						<c:set var="total" value="${meetingVoPage.totalElements}"></c:set>
						<c:set var="pageSize" value="${meetingVoPage.size}"></c:set>
						<c:set var="pageNumber" value="${meetingVoPage.number+1 }"></c:set>

						<c:if test="${total % pageSize == 0 }">
							<c:set var="totalPage" value="${ total / pageSize }"></c:set>
						</c:if>
						<c:if test="${total % pageSize > 0 }">
							<c:set var="totalPage" value="${ (total - total % pageSize) / pageSize + 1 }"></c:set>
						</c:if>
						<ul class="bxn-pagination" id="bxn-pagination1" style="margin: 20px 0px;">
							<li style="margin-top: 2px;">
								共
								<span class="count">
									<fmt:formatNumber value="${ totalPage }" pattern="#"></fmt:formatNumber>
								</span>
								页
							</li>
							<li class="first">
								<c:if test="${ pageNumber > 1 }">
									<a href="javascript:;">
										<span class="ico ico-first"></span>
									</a>
								</c:if>
								<c:if test="${ pageNumber <= 1 }">
									<span class="disable">
										<span class="ico ico-first-disable"></span>
									</span>
								</c:if>
							</li>
							<li class="previous">
								<c:if test="${ pageNumber > 1 }">
									<a href="javascript:;">
										<span class="ico ico-previous"></span>
									</a>
								</c:if>
								<c:if test="${ pageNumber <= 1 }">
									<span class="disable">
										<span class="ico ico-previous-disable"></span>
									</span>
								</c:if>
							</li>
							<li class="currentPage">
								<input type="text" value="<fmt:formatNumber value="${ pageNumber }" pattern="#"></fmt:formatNumber>" />
							</li>
							<li class="next">
								<c:if test="${ pageNumber < totalPage }">
									<a href="javascript:;">
										<span class="ico ico-next"></span>
									</a>
								</c:if>
								<c:if test="${ pageNumber >= totalPage }">
									<span class="disable">
										<span class="ico ico-next-disable"></span>
									</span>
								</c:if>
							</li>
							<li class="last">
								<c:if test="${ pageNumber < totalPage }">
									<a href="javascript:;">
										<span class="ico ico-last"></span>
									</a>
								</c:if>
								<c:if test="${ pageNumber >= totalPage }">
									<span class="disable">
										<span class="ico ico-last-disable"></span>
									</span>
								</c:if>
							</li>
							<li class="goto">
								<c:if test="${(total - total % pageSize) / pageSize > 0}">
									<a href="javascript:;" name='myAnchor'>跳转</a>
								</c:if>
								<c:if test="${(total - total % pageSize) / pageSize == 0}">
									<span class="disable">跳转</span>
								</c:if>
							</li>
						</ul>
					</div>
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
                $.bxn.confirm({msg:"是否删除会议？",width:"350",height:"80"},function(){
                    var meetingId = $(e.target).closest("tr").attr("id");
                    
                    var data = "_method=DELETE";
            		$.post('${ctx}/webapi/meeting/v1/meetings/'+ meetingId,data,
            		function(msg) {
            			$.bxn.tools.showRemindInfo(msg);
            			 window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
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
        //创建
        $("#addBtn").click(function(){
            window.location.href="${ctx}/meeting/attendance/meeting/createPage";
        });
        //修改
        $(".btn-edit").click(function(e){
        	var meetingId = $(e.target).closest("tr").attr("id");
            window.location.href="${ctx}/meeting/attendance/meeting/editPage/"+meetingId;
        });
        //删除
        $(".btn-remove").click($.bxn.lineoperator.onRemove);
        
      // 考勤情况
        $(".btn-detail").click(function(e){
        	var meetingId = $(e.target).closest("tr").attr("id");
            window.location.href="${ctx}/meeting/attendance/indexPage?meetingId="+meetingId;
        });
        //复制
        $(".btn-copy").click(function(e){
        	var meetingId = $(e.target).closest("tr").attr("id");
        	  if ($.bxn.editing){
                  alert("页面有正在编辑的内容！");
              }else{
                  $.bxn.confirm({msg:"是否要复制会议？",width:"350",height:"80"},function(){
                      var data = "_method=PUT";
              		$.post('${ctx}/webapi/meeting/v1/meeting-copy/'+meetingId,data,
              		function(msg) {
              			$.bxn.tools.showRemindInfo(msg);
              			 window.location.href="${ctx}/meeting/attendance/meeting/indexPage";
              		});
                  },function(){
                  });
              }
        });
        
     // 分页
		var p1 = $("#bxn-pagination1")
				.bxnPagination(
						{
							totalPage : '<fmt:formatNumber value="${totalPage}" pattern="#"></fmt:formatNumber>',
							url : '${ctx}/meeting/attendance/meeting/indexPage',
							pageNumber : '${pageNumber}',
							anchor : "",//锚点，用于翻页后页面锚定
							paramName : "page.page" //"currentPage"
						});
    });
</script>
</body>
