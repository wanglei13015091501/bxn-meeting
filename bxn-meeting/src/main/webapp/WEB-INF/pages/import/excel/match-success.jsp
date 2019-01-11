<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>	
<div class="data-table">
	<c:if test="${mainColumnType=='EXCEL' }">
		<table width="800" class="table" align="center" id="realDTtable">
			<thead>
				<tr>
					<td class='d-td' width="60"></td>
					<td class='d-td' width="50%">Excel文件列头</td>
					<td class='d-td' width="50%">数据库模板列头</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${headData }" var="head">
					<c:set var="isMatched" value="${!empty propertyDataMap[head] }"></c:set>
					<tr
						class="hoverable <c:if test="${ isMatched }">colMatched</c:if><c:if test="${ !isMatched }">colUnMatched</c:if>">
						<td class='d-td'><span
							class="ico <c:if test="${ isMatched }">ico-lamp-on</c:if><c:if test="${ !isMatched }">ico-lamp-closed</c:if>"></span></td>
						<td class='d-td align-left' data-key='${ head }'>${
							head }</td>
						<td class='d-td align-left'><select
							class="property-slct">
								<option value="">未匹配</option>
								<c:forEach items="${ propertyDataMap }" var="p">
									<option value="${p.value.name }"
										<c:if test="${ p.value.text == head }">selected</c:if>
										<c:if test="${ p.value.required }">required</c:if>>${p.value.formatText}</option>
								</c:forEach>
						</select></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${mainColumnType=='DB' }">
		<table width="90%" class="table" align="center" id="realDTtable">
			<thead>
				<tr>
					<td class='d-td' width="60"></td>
					<td class='d-td' width="50%">数据库模板列头</td>
					<td class='d-td' width="50%">Excel文件列头</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${propertyDataMap }" var="map">
					<c:set var="isMatched" value="false"></c:set>
					<c:forEach items="${headData }" var="head">
						<c:if test="${map.value.text == head}">
							<c:set var="isMatched" value="true"></c:set>
						</c:if>
					</c:forEach>
					<tr
						class="hoverable <c:if test="${ isMatched }">colMatched</c:if><c:if test="${ !isMatched }">colUnMatched</c:if>">
						<td class='d-td'><span
							class="ico <c:if test="${ isMatched }">ico-lamp-on</c:if><c:if test="${ !isMatched }">ico-lamp-closed</c:if>"></span></td>
						<td class='d-td align-left' data-key='${ map.value.name }'>${
							map.value.text }</td>
						<td class='d-td align-left'><select
							class="property-slct">
								<option value="">未匹配</option>
								<c:forEach items="${ headData }" var="head1">
									<option value="${head1 }"
										<c:if test="${ map.value.text == head1 }">selected</c:if>>${head1
										}</option>
								</c:forEach>
						</select></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>
<div class="toolbar-center" style="margin-top: 15px;">
	<a href="javascript:;" class="btn btn-common"
		onclick="submitForm();"> <span class="left"></span> <span
		class="right"><span class="ico ico-next"></span>下一步</span>
	</a> <a href="javascript:;" class="btn btn-common cancel"> <span
		class="left"></span><span class="right"><span
			class="ico ico-cancel"></span>取消</span>
	</a>
</div>