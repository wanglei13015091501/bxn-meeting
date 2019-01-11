<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<tr id="${place.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
    <td class='d-td'>${rowIndex+1}</td>
    <td class='d-td align-left'>${place.placeNo}</td>
    <td class='d-td align-left'>${place.placeName}</td>
    <td class='d-td align-left'>${place.description}</td>
    <td class='d-td'>
        <div class="toolbar-center">
            <a href="javascript:void(0)" class="btn btn-inline btn-edit">
                <span class="left"></span>
                <span class="right"><span class="ico ico-config"></span>编辑</span>
            </a>
            <a href="javascript:void(0)" class="btn btn-inline btn-remove">
                <span class="left"></span>
                <span class="right"><span class="ico ico-remove"></span>删除</span>
            </a>
        </div>
    </td>
</tr>
</tr>