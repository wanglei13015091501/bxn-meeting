<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<tr id="${type.id }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
    <td class='d-td'>${rowIndex+1}</td>
    <td class='d-td align-left'>${type.typeName}</td>
    <td class='d-td align-left'>${type.groupName}</td>
    <td class='d-td align-left'>${type.creatorName}</td>
    <td class='d-td align-left'>${type.createTime}</td>
    <td class='d-td'>
        <div class="toolbar-center">
        	
			<c:if test="${type.id ne '1' }">
            <a href="javascript:void(0)" class="btn btn-inline btn-edit">
                <span class="left"></span>
                <span class="right"><span class="ico ico-config"></span>编辑</span>
            </a>
            <a href="javascript:void(0)" class="btn btn-inline btn-remove">
                <span class="left"></span>
                <span class="right"><span class="ico ico-remove"></span>删除</span>
            </a>
            </c:if>
        </div>
    </td>
</tr>
</tr>