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
<jsp:attribute name="footer">
	<script>
		$(function() {
			$('#pwdsubmit').click(function() {
				if($('#pwd1').val() != $('#pwd2').val()) {
					alert('密码不一致');
					return false;
				}
			});
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li class="active">个人设置</li>
</ol>
	<form class="form-horizontal"  action="updatepassword" method="POST">
		<fieldset><legend>修改密码</legend> 
		<div class="form-group">
		<label for="name" class="col-sm-2 col-md-1 control-label">密码</label>
		<div class="col-sm-10 col-md-3">
			<input type="password" class="form-control" placeholder="密码" required name="pwd" id="pwd1">
		</div>
		<div class="col-sm-10 col-md-3">
			<p class="text-danger">密码为MD5加密的16位字符</p>
		</div>
	</div>
	<div class="form-group">
		<label for="name" class="col-sm-2 col-md-1 control-label">确认密码</label>
		<div class="col-sm-10 col-md-3">
			<input type="password" class="form-control" placeholder="姓名" required id="pwd2">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2 col-md-1 control-label"></div>
		<div class="col-sm-10 col-md-3">
			<input type="hidden" value="${user.id}" name="id">
			<button type="submit" class="btn btn-primary btn-wide" id="pwdsubmit">提交</button>
		</div>
	</div>
	</fieldset>
	</form>
	
		<form class="form-horizontal"  action="updateusersetting" method="POST">
		<fieldset><legend>修改基本信息</legend> 
	<div class="form-group">
		<label for="name" class="col-sm-2 col-md-1 control-label">姓名</label>
		<div class="col-sm-10 col-md-3">
			<input type="text" class="form-control" placeholder="姓名" required value="${user.name }" name="name">
		</div>
	</div>
	<div class="form-group">
		<label for="employeeNumber" class="col-sm-2 col-md-1 control-label">员工号</label>
		<div class="col-sm-10 col-md-3">
			<input type="text" class="form-control" placeholder="员工号" required value="${user.employeeNumber }" name="employeeNumber">
		</div>
	</div>

	<div class="form-group">
		<label for="phoneNumber" class="col-sm-2 col-md-1 control-label">手机号</label>
		<div class="col-sm-10 col-md-3">
			<input type="text" class="form-control" placeholder="手机号" required value="${user.phoneNumber }" name="phoneNumber">
		</div>
	</div>

	<div class="form-group">
		<label for="title" class="col-sm-2 col-md-1 control-label">职称</label>
		<div class="col-sm-10 col-md-3">
			<select data-toggle="select" class="select select-primary mrs mbm" name="titleId">
				<c:forEach items="${titles }" var="t">
					<option value="${t.id }" <c:if test="${user.title.id == t.id}"> selected='selected'</c:if>>${t.name }
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label for="introduction" class="col-sm-2 col-md-1 control-label">简介</label>
		<div class="col-sm-10 col-md-3">
			<textarea class="form-control" rows="5" placeholder="简介" name="introduction">${user.introduction }</textarea>
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-2 col-md-1 control-label"></div>
		<div class="col-sm-10 col-md-3">
			<input type="hidden" value="${user.id}" name="id">
			<button type="submit" class="btn btn-primary btn-wide">提交</button>
		</div>
	</div>
	</fieldset>
</form>				
						
    </jsp:body>
</myTemplate:template>