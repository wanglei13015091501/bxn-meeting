<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="platformurl" value='<%=cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.base_platform_url") %>'/>
<c:set var="uicurl" value='<%=cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.base_uic_url") %>'/>
<c:set var="portalurl" value='<%=cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.portal_url") %>'/>
<c:set var="helpsystemurl" value='<%=cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("client.help_system_url") %>'/>
<c:set var="indexuri" value='<%=cn.boxiao.bxn.common.util.PropertiesAccessorUtil.getProperty("system.module.index.uri") %>'/>