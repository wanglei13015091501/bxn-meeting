<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<tr>
    <td class="d-td">
    </td>
    <td class="d-td">
        <input type="hidden" name="id" value="">
        <span style="padding-right:2px;color: red">*</span><input type="text" name="placeNo" value="" required required-tip="会议地点编码必须填写！" maxlength=20 invalidChar>
    </td>
    <td class="d-td">
        <span style="padding-right:2px;color: red">*</span><input type="text" name="placeName" value="" required required-tip="会议地点名称必须填写！" maxlength=30 invalidChar>
    </td>
    <td class="d-td">
        <input type="text" name="description" value="" invalidChar>
    </td>
    <td class="d-td">
        <div class="toolbar-center">
            <a id="saveBtn" href="javascript:void(0)" class="btn btn-inline btn-save">
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