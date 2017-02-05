<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<form class="form-horizontal" action="admin/setting/setdefaultpwd" method="POST">
<div class="form-group">
		<label for="name" class="col-sm-2 col-md-2 control-label">重置密码</label>
		<div class="form-group">
		<div class="col-sm-10 col-md-4">
			<input type="hidden" value="${user.id}" name="userId">
			<button type="submit" class="btn btn-primary btn-block">重置密码为员工号</button>
		</div>
	</div>
	</div>
</form>
<form class="form-horizontal" action="admin/setting/updateuser" method="POST">
	<div class="form-group">
		<label for="name" class="col-sm-2 col-md-2 control-label">姓名</label>
		<div class="col-sm-10 col-md-4">
			<input type="text" class="form-control" placeholder="姓名" required value="${user.name }" name="name">
		</div>
	</div>
	<div class="form-group">
		<label for="employeeNumber" class="col-sm-2 col-md-2 control-label">员工号</label>
		<div class="col-sm-10 col-md-4">
			<input type="text" class="form-control" placeholder="员工号" required value="${user.employeeNumber }" name="employeeNumber">
		</div>
	</div>

	<div class="form-group">
		<label for="phoneNumber" class="col-sm-2 col-md-2 control-label">手机号</label>
		<div class="col-sm-10 col-md-4">
			<input type="text" class="form-control" placeholder="手机号" required value="${user.phoneNumber }" name="phoneNumber">
		</div>
	</div>

	<div class="form-group">
			<label for="title" class="col-sm-2 col-md-2 control-label">职称</label>
			<div class="col-sm-10 col-md-4">
				<select data-toggle="select" class="select select-primary mrs mbm" name="titleId">
					<c:forEach items="${titles }" var="t">
						<option value="${t }" <c:if test="${user.title == t}"> selected='selected'</c:if>>${t.name }
					</c:forEach>
				</select>
			</div>
		</div>
	<div class="form-group">
		<label for="introduction" class="col-sm-2 col-md-2 control-label">简介</label>
		<div class="col-sm-10 col-md-4">
			<textarea class="form-control" rows="5" placeholder="简介" name="introduction">${user.introduction }</textarea>
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-2 col-md-2 control-label"></div>
		<div class="col-sm-10 col-md-4">
			<input type="hidden" value="${user.id}" name="id">
			<button type="submit" class="btn btn-primary btn-wide">提交</button>
		</div>
	</div>
</form>

	<script src="resources/js/flat-ui.min.js"></script>
	<script src="resources/js/application.js"></script>
