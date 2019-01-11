<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<title>登录</title>
<body>
<div style="position: absolute;margin-left: 150px;">
    <form method="POST" class="form-signin"  name="f" action="<spring:url value="/j_security_check"/>">
    <h2 class="form-signin-heading">登录</h2>
    <p><input type="text" class="input-block-level" placeholder="用户名" name="j_username" value="888888"></p>
    <p><input type="password" class="input-block-level" placeholder="密码"  name="j_password" value="changenow"></p>
    <button class="btn btn-large btn-primary" type="submit">登录</button>
    </form>
</div>
</body>