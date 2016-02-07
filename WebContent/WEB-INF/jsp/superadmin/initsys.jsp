<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<myTemplate:template>

	<jsp:body>
	<ol class="breadcrumb">
		<li><a href="">主页</a></li>
		<li class="active">用户管理</li>
	</ol>
	
	
		<form class="form-horizontal"  action="superadmin/initteachertitle" method="POST">
			<button type="submit" class="btn btn-primary btn-wide">初始化职称</button>
		</form>
		
		<form class="form-horizontal"  action="superadmin/inituserauthority" method="POST">
			<button type="submit" class="btn btn-primary btn-wide">初始化权限</button>
		</form>
		<form class="form-horizontal"  action="superadmin/initinvistatustype" method="POST">
			<button type="submit" class="btn btn-primary btn-wide">初始化监考状态</button>
		</form>
		<form class="form-horizontal"  action="superadmin/inituser" method="POST">
			<button type="submit" class="btn btn-primary btn-wide">初始化用户</button>
		</form>

	</jsp:body>
</myTemplate:template>