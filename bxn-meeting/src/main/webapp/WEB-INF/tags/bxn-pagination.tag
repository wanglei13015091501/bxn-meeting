<%@ tag pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="pageObj" required="true" type="cn.boxiao.bxn.base.model.Page" %>
<%@ attribute name="pageUrl" required ="false" type="java.lang.String" %>
<%@ attribute name="currentPageParamName" required="false" type="java.lang.String" %>
<c:set var="total" value="${pageObj.totalElements}"></c:set>
<c:set var="pageSize" value="${pageObj.size}"></c:set>
<c:set var="pageNumber" value="${pageObj.number+1 }"></c:set>

<c:if test="${total % pageSize == 0 }">
    <c:set var="totalPage" value="${ total / pageSize }"></c:set>
</c:if>
<c:if test="${total % pageSize > 0 }">
    <c:set var="totalPage" value="${ (total - total % pageSize) / pageSize + 1 }"></c:set>
</c:if>
<ul class="bxn-pagination" id="bxn-pagination1" style="margin:20px 0px;">
    <li style="margin-top:2px;">
        共<span class="count"><fmt:formatNumber value="${ totalPage }" pattern="#"></fmt:formatNumber></span>页
    </li>
    <li class="first">
        <c:if test="${ pageNumber > 1 }">
            <a href="javascript:;"><span class="ico ico-first"></span></a>
        </c:if>
        <c:if test="${ pageNumber <= 1 }">
            <span class="disable"><span class="ico ico-first-disable"></span></span>
        </c:if>
    </li>
    <li class="previous">
        <c:if test="${ pageNumber > 1 }">
            <a href="javascript:;"><span class="ico ico-previous"></span></a>
        </c:if>
        <c:if test="${ pageNumber <= 1 }">
            <span class="disable"><span class="ico ico-previous-disable"></span></span>
        </c:if>
    </li>
    <li class="currentPage">
        <input type="text" value="<fmt:formatNumber value="${ pageNumber }" pattern="#"></fmt:formatNumber>"/>
    </li>
    <li class="next">
        <c:if test="${ pageNumber < totalPage }">
            <a href="javascript:;"><span class="ico ico-next"></span></a>
        </c:if>
        <c:if test="${ pageNumber >= totalPage }">
            <span class="disable"><span class="ico ico-next-disable"></span></span>
        </c:if>
    </li>
    <li class="last">
        <c:if test="${ pageNumber < totalPage }">
            <a href="javascript:;"><span class="ico ico-last"></span></a>
        </c:if>
        <c:if test="${ pageNumber >= totalPage }">
            <span class="disable"><span class="ico ico-last-disable"></span></span>
        </c:if>
    </li>
    <li class="goto">
        <c:if test="${(total - total % pageSize) / pageSize > 0}">
            <a href="javascript:;" name='myAnchor'>跳转</a>
        </c:if>
        <c:if test="${(total - total % pageSize) / pageSize == 0}">
            <span class="disable">跳转</span>
        </c:if>
    </li>
</ul>
<c:set var="cn.boxiao.bxn.common.web.EscapeXmlELResolver.escapeXml" value="false"/>
<script type="text/javascript">
    $(function() {
        var p1 = $("#bxn-pagination1").bxnPagination({
            totalPage:'<fmt:formatNumber value="${totalPage}" pattern="#"></fmt:formatNumber>',
            url:'${pageUrl}',
            pageNumber:'${pageNumber}',
            anchor:"",//锚点，用于翻页后页面锚定
            paramName:"${currentPageParamName}" //"currentPage"
        });
    });
</script>
<c:set var="cn.boxiao.bxn.common.web.EscapeXmlELResolver.escapeXml" value="true"/>