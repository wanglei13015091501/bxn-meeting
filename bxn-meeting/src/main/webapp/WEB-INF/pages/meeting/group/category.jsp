<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>部门分类</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">部门分类</div>
		<form id="query-form">
			<div class="query-area">
				<table id="query-table" style="width: 100%">
					<tbody>
						<tr>

							<td width="160px">
								<a id="addBtn" href="javascript:;" class="btn btn-common btn-add" style="margin-right: 27px; float: right">
									<span class="left"></span>
									<span class="right">
										<span class="ico ico-add"></span>
										新增分类
									</span>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form id="meetingGroupSaveForm">
			<input type="hidden" name="parentId" id="parentId" value="0"/>
			<table width="95%" class="table" align="center">
				<thead id="groupHead">
					<tr id="firstTr">
						<td class='d-td' width="50">序号</td>
						<td class='d-td' width="200">分类名称</td>
						<td class='d-td' width="50">排序</td>
						<td class='d-td' width="20%">创建人</td>
						<td class='d-td' width="30%">创建时间</td>
						<td class='d-td' width="250">操作</td>
					</tr>
				</thead>
				<tbody id="groupBody">
					<c:forEach items="${meetingGroups}" var="group" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<tr id="${group.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
						    <td class='d-td'>${rowIndex+1}</td>
						    <td class='d-td align-left'>${group.groupName}</td>
						    <td class='d-td align-left'>${group.orderNo}</td>
						    <td class='d-td align-left'>${group.creatorName}</td>
						    <td class='d-td align-left'>${group.createTime}</td>
						    <td class='d-td'>
						        <div class="toolbar-center">
						        	<a href="javascript:void(0)" class="btn btn-inline btn-view">
						                <span class="left"></span>
						                <span class="right"><span class="ico ico-modify"></span>查看部门</span>
						            </a>
						            <a href="javascript:void(0)" class="btn btn-inline btn-edit">
						                <span class="left"></span>
						                <span class="right"><span class="ico ico-config"></span>编辑</span>
						            </a>
						            
						            <c:if test="${group.id ne '1' }">
						            <a href="javascript:void(0)" class="btn btn-inline btn-remove">
						                <span class="left"></span>
						                <span class="right"><span class="ico ico-remove"></span>删除</span>
						            </a>
						            </c:if>
						        </div>
						    </td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</form>
		<div class="foot"></div>
	</div>
	<script type="text/javascript">

		$.bxn.lineoperator = {
			onView : function(e) {
				if ($.bxn.editing) {
					alert("页面有正在编辑的内容!");
				} else {
					var $tr = $(e.target).closest("tr");
					var groupId = $tr.attr("id");
					window.location.href = "${ctx}/meeting/group/groupPage?parentId="+groupId;	
				}
			},
			onEdit : function(e) {
				if ($.bxn.editing) {
					alert("页面有正在编辑的内容!");
				} else {
					var $tr = $(e.target).closest("tr");
					var index = $tr.children("td:first-child").text();
					var groupId = $tr.attr("id");
					currentTR = $tr.clone();
					url = "${ctx}/meeting/group/editPage?groupId=" + groupId
							+ "&unwrap=true";
					$.get(url).done(function(formTR) {
										console.log($(formTR));
										var heads = $("#groupHead td");
										var $formTR = $(formTR);
										$formTR.children("td:first-child").text(index);
										$formTR.find('td').each(function(i, o) {
															$(this).find("input[type='text'],select").css("width",$(heads[i]).width() - 20);
														});
										$formTR.attr("class", $tr.attr("class"));
										$tr.replaceWith($formTR);
									});
					$.bxn.editing = true;//编辑状态
					$.bxn.ifNew = false;//编辑
				}
			},
			onRemove : function(e) {
				if ($.bxn.editing) {
					alert("页面有正在编辑的内容！");
				} else {
					$.bxn.confirm({
						msg : "是否删除部门分类？",
						width : "350",
						height : "80"
									},
									function() {
										var groupId = $(e.target).closest("tr").attr("id");

										var data = "_method=DELETE";
										$.post('${ctx}/webapi/meeting/v1/groups/'+ groupId,data,
														function(msg) {
															$.bxn.tools.showRemindInfo(msg);
															window.location.href = "${ctx}/meeting/group/categoryPage";
														});
									}, function() {
									});
				}
			}
		};

		$(function() {
			$(".clear").click(function(e) {
				if (confirm("确认要清除吗？")) {
					$(e.target).closest("form").submit();
				}
			});
			//创建
			$("#addBtn").click(
							function() {
								if ($.bxn.editing) {
									alert("页面有正在编辑的内容！");
									return;
								}
								var $tr = $("#firstTr");
								currentTR = $tr.clone();
								url = "${ctx}/meeting/group/createPage?unwrap=true";
								$.get(url).done(
												function(formTR) {
													var heads = $("#groupHead td");
													var $formTR = $(formTR);
													$formTR.find("td").each(function(i,o) {
																		$(this).find("input[type='text']").css("width",$(heads[i]).width() - 20);
																	});
													$("#groupBody").prepend($formTR);
												});
								$.bxn.editing = true;
								$.bxn.ifNew = true;
							});
			//查看部门
			$(".btn-view").click($.bxn.lineoperator.onView);
			//修改
			$(".btn-edit").click($.bxn.lineoperator.onEdit);
			//删除
			$(".btn-remove").click($.bxn.lineoperator.onRemove);

		});
		
		//保存
		$("#meetingGroupSaveForm").bxnForm({
			ajaxSubmit : true,
			submitBtn : "saveBtn",
			url : "${ctx}/webapi/meeting/v1/groups",
			type : "post",
			success : function(form, data) {
				window.location.href = "${ctx}/meeting/group/categoryPage";
			}
		});

		//修改保存
		$("#meetingGroupSaveForm").on("click","a.btn-editSave",
						function(e) {
							var groupId = $("#groupId").val();
							var groupName = $("#groupName").val();
							if(groupName.trim()==''){
								alert("分类名称不能为空！");
								return false;
							}
							if(groupName.trim().length > 32){
								alert("分类名称字数不能超过32！");
								return false;
							}
							var orderNo = $("#orderNo").val();
							if(orderNo.trim()==''){
								alert("排序不能为空！");
								return false;
							}
							if(orderNo.trim().length > 3){
								alert("排序字数不能超过3！");
								return false;
							}
							var reg1 = new RegExp("^[0-9]*$");
							if(!reg1.test(orderNo.substring(0,1))){
								alert("排序号必须以数字开头");
								return false;
							}
							var reg2 =  /^[0-9a-zA-Z]*$/g;
							if(!reg2.test(orderNo)){
								alert("排序号只能输入数字和字母组合");
								return false;
							}
							
							url = "${ctx}/webapi/meeting/v1/groups/" + groupId;
							var data = "groupName=" + groupName + "&orderNo="+ orderNo + "&_method=PUT";
							$.ajax({
										cache : true,
										type : "POST",
										url : url,
										data : data,
										async : true,
										error : function(request) {
										},
										success : function(msg) {
											$.bxn.tools.showRemindInfo(msg);
											window.location.href = "${ctx}/meeting/group/categoryPage";
										}
									});
						})

		$("#meetingGroupSaveForm").on("click", ".btn-save", function() {
			var orderNo = $("input[name='orderNo']").val();
			if(orderNo.trim()==''){
				alert("排序不能为空！");
				return false;
			}
			if(orderNo.trim().length > 3){
				alert("排序字数不能超过3！");
				return false;
			}
			var reg1 = new RegExp("^[0-9]*$");
			if(!reg1.test(orderNo.substring(0,1))){
				alert("排序号必须以数字开头");
				return false;
			}
			var reg2 =  /^[0-9a-zA-Z]*$/g;
			if(!reg2.test(orderNo)){
				alert("排序号只能输入数字和字母组合");
				return false;
			}
			var groupId = $(this).closest("tr").attr("id");
			$("input[name='groupId']").val(groupId);
			$("#meetingGroupSaveForm").submit();
		});

		//取消
		$("#meetingGroupSaveForm").on("click",".btn-cancel",
				function(e) {
					var $tr = $(e.target).closest("tr");
					var isnew = $.bxn.ifNew;
					if (isnew) {
						$tr.remove();
						$("tr").each(function() {
							var trClass = $(this).attr("class");
							if (trClass == "d-td dark") {
								$(this).attr("class", "d-td");
							} else if (trClass == "d-td") {
								$(this).attr("class", "d-td dark");
							}
						});
					} else {
						$tr.replaceWith(currentTR);
						currentTR.find(".toolbar-center .btn-view").click($.bxn.lineoperator.onView);
						currentTR.find(".toolbar-center .btn-edit").click($.bxn.lineoperator.onEdit);
						currentTR.find(".toolbar-center .btn-remove").click($.bxn.lineoperator.onRemove);
					}
					$.bxn.ifNew = false;
					$.bxn.editing = false;
				});
	</script>
</body>
