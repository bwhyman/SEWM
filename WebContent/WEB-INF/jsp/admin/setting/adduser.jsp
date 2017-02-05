<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/setting/usersetting">用户管理</a></li>
  <li class="active">添加用户</li>
</ol>
	<form class="form-horizontal" action="admin/setting/adduser" method="POST">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">姓名</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="姓名" required name="name">
						</div>
					</div>
					<div class="form-group">
						<label for="employeeNumber" class="col-sm-2 col-md-2 control-label">员工号</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="员工号" required name="employeeNumber">
						</div>
					</div>
					
					<div class="form-group">
						<label for="phoneNumber" class="col-sm-2 col-md-2 control-label">手机号</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="手机号" required name="phoneNumber">
						</div>
					</div>
					
					<div class="form-group">
						<label for="title" class="col-sm-2 col-md-2 control-label">职称</label>
						<div class="col-sm-10 col-md-4">
						<select data-toggle="select" class="select select-primary mrs mbm" name="titleId">
					<c:forEach items="${titles }" var="t">
						<option value="${t.id }">${t.name }
					</c:forEach>
				</select>
						</div>
					</div>
					<div class="form-group">
						<label for="introduction" class="col-sm-2 col-md-2 control-label">简介</label>
						<div class="col-sm-10 col-md-4">
							<textarea class="form-control" rows="5" placeholder="简介" name="introduction"></textarea>
						</div>
					</div>
						
					<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label"></div>
						<div class="col-sm-10 col-md-4">
							<button type="submit" class="btn btn-primary btn-wide">提交</button>
							<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>	
						</div>
					</div>
					
				</form>	
    </jsp:body>
</mybase:base>