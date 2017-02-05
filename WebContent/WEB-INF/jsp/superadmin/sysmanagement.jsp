<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:body>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="superadmin/initusers">初始化用户</a>
			</div>
			</div>
		</div>
		</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="superadmin/addgroup">添加组</a>
			</div>
			</div>
		</div>
		</div>
		<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="superadmin/updatebasedate">学期基点日期</a>
			</div>
			</div>
		</div>
		</div>
	</jsp:body>
</mybase:base>