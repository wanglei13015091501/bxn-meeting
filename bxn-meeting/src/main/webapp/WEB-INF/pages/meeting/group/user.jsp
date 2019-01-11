<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>部门用户</title>
<head>
</head>
<body width="95%">
	<div class="data-table" id="table_fixed_container">
		<div class="head">
			部门用户【所属部门：${groupName}】
		
			<div style = "float:right">
					<a href="javascript:void(0)" class="btn btn-common  btn-goback">
							<span class="left"></span>
							<span class="right">
								<span class="ico "></span>
								返回
							</span>
						</a>    
			</div>	
		</div>
		<form id="query-form">
			<div class="query-area">
				<table id="query-table" style="width: 100%">
					<tbody>
						<tr>

							<td width="160px">
								<input type="hidden" id="userIds" name="userIds" required style="width: 80%" />
								<a href="javascript:;" class="btn btn-common btn-add" style="margin-right: 27px; float: right" onclick="chooseGroupUser()">
									<span class="left"></span>
									<span class="right">
										<span class="ico ico-add"></span>
										新增用户
									</span>
								</a>
								
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<form id="meetingGroupUserSaveForm">
			<input type="hidden" name="parentId" id="parentId" value="${parentId}"/>
			<table width="95%" class="table" align="center">
				<thead id="groupUserHead">
					<tr id="firstTr">
						<td class='d-td' width="50">序号</td>
						<td class='d-td' width="30%">人员名称</td>
						<td class='d-td' width="10%">编号</td>
						<td class='d-td' width="20%">创建时间</td>
						<td class='d-td' width="200">操作</td>
					</tr>
				</thead>
				<tbody id="groupUserBody">
					<c:forEach items="${groupUsers}" var="groupUser" varStatus="varStatus">
						<c:set var="rowIndex" value="${varStatus.index }" scope="request"></c:set>
						<tr id="${groupUser.userId }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
						    <td class='d-td'>${rowIndex+1}</td>
						    <td class='d-td align-left'>${groupUser.fullName}</td>
						    <td class='d-td align-left'>${groupUser.uniqueNo}</td>
						    <td class='d-td align-left'>${groupUser.createTime}</td>
						    <td class='d-td'>
						        <div class="toolbar-center">
						        	<a href="javascript:void(0)" class="btn btn-inline btn-remove">
						                <span class="left"></span>
						                <span class="right"><span class="ico ico-remove"></span>删除</span>
						            </a>
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
			onRemove : function(e) {
				if ($.bxn.editing) {
					alert("页面有正在编辑的内容！");
				} else {
					$.bxn.confirm({
						msg : "是否删除部门用户？",
						width : "350",
						height : "80"
									},
									function() {
										var groupUserId = $(e.target).closest("tr").attr("id");

										var data = "_method=DELETE";
										$.post('${ctx}/webapi/meeting/v1/groups/${groupId}/users/'+ groupUserId,data,
														function(msg) {
															$.bxn.tools.showRemindInfo(msg);
															window.location.href = "${ctx}/meeting/group/userPage?groupId=${groupId}";
														});
									}, function() {
									});
				}
			}
		};

		$(function() {
			
			//返回
			$("a.btn-goback").click(function() {
								window.location.href = "${ctx}/meeting/group/groupPage?parentId=${parentId}";
							});
			
			//删除
			$(".btn-remove").click($.bxn.lineoperator.onRemove);

		});
		
		// 选人框
		function chooseGroupUser(){
			var picker = new $.bxn.personPicker({
				onFinishPicked : function(users){
					//选择完成回调赋值
					var names = "";
					var ids = "";
					$(users).each(function(item){
						ids += users[item].id+",";
					});
					
					var data = 'groupId=${groupId}&userIds='+ids;
					$.post('${ctx}/webapi/meeting/v1/groups/users/',data,
							function(msg) {
								$.bxn.tools.showRemindInfo(msg);
								window.location.href = "${ctx}/meeting/group/userPage?groupId=${groupId}";
							});
				},
				title:$.bxn.personPicker.messages.defaultTitle,
				rootRuleId:"getOrganizationsByUserCategoryRule",//固定
				personProviderGroups:[{id:"1",name:"教师"},{id:"4",name:"学生"}],//2,3,4可多个
				multi:true,//是否多选
				//checkedIds:[$("#organizerIds").val()]//已选项，打开默认勾选
			});		
		}

	</script>
</body>
