<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tr id="${tBodyData.key }" class="<c:if test="${rowIndex%2 eq 1 }">dark</c:if> hoverable">
	<c:forEach items="${tBodyData.value }" var="cell">
		<c:choose>
			<c:when test="${cell.emptyValue}">
				<td class='d-td'>--</td>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${cell.type eq 'long' }">
						<td class='d-td'>${rowIndex + 1 }</td>
					</c:when>
					<c:when test="${cell.type eq 'text' }">
						<td class='d-td'>${cell.value}</td>
					</c:when>
					<c:when test="${cell.type eq 'text-right' }">
						<td class='d-td align-right'>${cell.value}</td>
					</c:when>
					<c:when test="${cell.type eq 'boolean' }">
						<td class='d-td'>
							<c:if test="${cell.value}">
								<span class="ico ico-yes"></span>
							</c:if>
							<c:if test="${!cell.value}">
								<span class="ico ico-no"></span>
							</c:if>
						</td>
					</c:when>
					<c:when test="${cell.type eq 'operator' }">
						<td class='d-td'>
							<c:if test="${cell.value eq 'clear' }">
								<form class='input-layout-form' action='${ctx}/systemadmin/manage/cachemanager/remove' method='post'>
								<input type="hidden" name="cacheName" value="${cell.name}"/>
								<div class="toolbar-center">
									<a id="cacheEditBtn" href="javascript:void(0)" class="btn btn-inline clear">
										<span class="left"></span>
										<span class="right"><span class="ico ico-remove"></span>REFRESH</span>
									</a>
								</div>
								</form>
							</c:if>
						</td>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</tr>