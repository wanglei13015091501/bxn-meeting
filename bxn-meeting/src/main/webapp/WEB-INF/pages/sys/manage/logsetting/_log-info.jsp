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
					<c:when test="${cell.type eq 'text-left' }">
						<td class='d-td align-left'>${cell.value}</td>
					</c:when>
					<c:when test="${cell.type eq 'operator' }">
						<td class='d-td'>
							<input type="hidden" name="filePath" value="${cell.name}"/>
							<input type="hidden" name="type" value="${type}"/>
							<c:if test="${cell.value eq 'in' }">
								<div class="toolbar-center">
									<a id="logInBtn" href="javascript:void(0);" class="btn btn-inline in">
										<span class="left"></span>
										<span class="right"><span class="ico ico-modify"></span>进入</span>
									</a>
								</div>
							</c:if>
							<c:if test="${cell.value eq 'download' }">
								<a id="downloadBtn" href="javascript:void(0);" class="btn btn-inline download">
									<span class="left"></span>
									<span class="right"><span class="ico ico-modify"></span>下载</span>
								</a>
							</c:if>
						</td>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</tr>