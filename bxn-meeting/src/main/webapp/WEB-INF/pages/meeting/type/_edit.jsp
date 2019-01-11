<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<tr>
    <td class="d-td">
    </td>
    <td class="d-td">
        <input type="hidden" name="id" id="typeId" value="${meetingType.id}">
        <span style="padding-right:2px;color: red">*</span><input type="text" id ="typeName" name="typeName" value="${meetingType.typeName}" required required-tip="会议地点编码必须填写！" maxlength=50 invalidChar style="width: 200px">
    </td>
     <td class="d-td">
    	<select style="width: 100px" id="groupId" >
    		<option value="">请选择</option> 
	        <c:forEach items="${meetingGroupVoList}" var="meetingGroup" varStatus="varStatus">
	        	<option value="${meetingGroup.id }" <c:if test="${meetingType.groupId eq meetingGroup.id }">selected</c:if> >${meetingGroup.groupName}</option> 
	        </c:forEach>
        </select>
    </td>
    <td class="d-td">
    </td>
    <td class="d-td">
    </td>
    <td class="d-td">
        <div class="toolbar-center">
            <a id="saveBtn" href="javascript:void(0)" class="btn btn-inline btn-editSave">
                <span class="left"></span>
                <span class="right"><span class="ico ico-save"></span>保存</span>
            </a>
            <a id="cancelBtn" href="javascript:void(0)" class="btn btn-inline btn-cancel">
                <span class="left"></span>
                <span class="right"><span class="ico ico-cancel"></span>取消</span>
            </a>
        </div>
    </td>
</tr>