<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://www.boxiao.cn/bxn/taglib" prefix="bxn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<%
{ 
	org.springframework.security.core.Authentication auth=org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication(); 
	cn.boxiao.bxn.base.client.rest.security.LoggedUser loginUser = null;
	if(auth!=null){
		Object principal=auth.getPrincipal(); 
		if(principal instanceof cn.boxiao.bxn.base.client.rest.security.LoggedUser){ 
			loginUser=(cn.boxiao.bxn.base.client.rest.security.LoggedUser)principal; 
		}else{ 
			principal=auth.getDetails(); 
			if(principal instanceof cn.boxiao.bxn.base.client.rest.security.LoggedUser){ 
				loginUser=(cn.boxiao.bxn.base.client.rest.security.LoggedUser)principal; 
			} 
		}	
	}
	pageContext.setAttribute("currentUser", loginUser);
}
%>