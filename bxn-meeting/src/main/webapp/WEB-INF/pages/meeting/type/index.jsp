<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>会议类型</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">会议类型</div>
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
										新增类型
									</span>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form id="meetingTypeSaveForm">
			<table width="95%" class="table" align="center">
				<thead id="placeHead">
					<tr id="firstTr">
						<td class='d-td' width="50">序号</td>
						<td class='d-td' width="30%">会议类型</td>
						<td class='d-td' width="20%">关联分组</td>
						<td class='d-td' width="30%">创建人</td>
						<td class='d-td' width="20%">创建时间</td>
						<td class='d-td' width="200">操作</td>
					</tr>
				</thead>
				<tbody id="placeTBody">
					<c:forEach items="${meetingTypes}" var="type" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<%@ include file="_info.jsp"%>
					</c:forEach>
				</tbody>
			</table>
		</form>
		<div class="foot"></div>
	</div>
	<script type="text/javascript">

		$.bxn.lineoperator = {
			onEdit : function(e) {
				if ($.bxn.editing) {
					alert("页面有正在编辑的内容!");
				} else {
					var $tr = $(e.target).closest("tr");
					var index = $tr.children("td:first-child").text();
					var typeId = $tr.attr("id");
					currentTR = $tr.clone();
					url = "${ctx}/meeting/type/editPage?typeId=" + typeId
							+ "&unwrap=true";
					$.get(url).done(function(formTR) {
										console.log($(formTR));
										var heads = $("#placeHead td");
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
						msg : "是否删除会议类型，删除之后在考勤时刻表中将无法选择该会议类型？",
						width : "350",
						height : "80"
									},
									function() {
										var typeId = $(e.target).closest("tr").attr("id");

										var data = "_method=DELETE";
										$.post('${ctx}/webapi/meeting/v1/types/'+ typeId,data,
														function(msg) {
															$.bxn.tools.showRemindInfo(msg);
															window.location.href = "${ctx}/meeting/type/indexPage";
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
								url = "${ctx}/meeting/type/createPage?unwrap=true";
								$.get(url).done(
												function(formTR) {
													var heads = $("#placeHead td");
													var $formTR = $(formTR);
													$formTR.find("td").each(function(i,o) {
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

		});
		//保存
		$("#meetingTypeSaveForm").bxnForm({
			ajaxSubmit : true,
			submitBtn : "saveBtn",
			url : "${ctx}/webapi/meeting/v1/types",
			type : "post",
			success : function(form, data) {
				window.location.href = "${ctx}/meeting/type/indexPage";
			}
		});

		//修改保存
		$("#meetingTypeSaveForm").on("click","a.btn-editSave",
						function(e) {
							var typeId = $("#typeId").val();
							var typeName = $("#typeName").val();
							if(typeName.trim()==''){
								alert("会议类型不能为空！");
								return false;
							}
							if(typeName.trim().length > 20){
								alert("会议类型字数不能超过20！");
								return false;
							}
							var groupId = $("#groupId option:selected").val();
							url = "${ctx}/webapi/meeting/v1/types/" + typeId;
							var data = "typeName=" + typeName + "&groupId=" + groupId + "&_method=PUT";
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
											window.location.href = "${ctx}/meeting/type/indexPage";
										}
									});
						})

		$("#meetingTypeSaveForm").on("click", ".btn-save", function() {
			var typeId = $(this).closest("tr").attr("id");
			$("input[name='typeId']").val(typeId);
			$("#meetingTypeSaveForm").submit();
		});

		//取消
		$("#meetingTypeSaveForm").on("click",".btn-cancel",
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
						currentTR.find(".toolbar-center .btn-edit").click($.bxn.lineoperator.onEdit);
						currentTR.find(".toolbar-center .btn-remove").click($.bxn.lineoperator.onRemove);
					}
					$.bxn.ifNew = false;
					$.bxn.editing = false;
				});
	</script>
</body>
