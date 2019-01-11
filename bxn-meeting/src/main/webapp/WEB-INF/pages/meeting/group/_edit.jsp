<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<tr>
    <td class="d-td">
    </td>
    <td class="d-td">
        <input type="hidden" name="groupId" id="groupId" value="${meetingGroup.id}">
        <span style="padding-right:2px;color: red">*</span><input type="text" id ="groupName" name="groupName" value="${meetingGroup.groupName}" required required-tip="名称必须填写！" maxlength=32 invalidChar>
    </td>													
     <td class="d-td">
        <span style="padding-right:2px;color: red">*</span><input type="text" id="orderNo" name="orderNo"  value="${meetingGroup.orderNo}" required required-tip="排序号必须填写!" maxlength=3 invalidChar/>
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