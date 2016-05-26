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
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">发送监考短信通知</li>
</ol>

<c:if test="${exception != null}">
		&nbsp&nbsp
		<div class="alert alert-danger alert-dismissable" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</button><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <strong>错误！</strong> ${exception }
</div>
</c:if> 

	<c:if test="${results != null}">
		&nbsp&nbsp
		<div class="alert alert-success alert-dismissable" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</button>
  <c:forEach var="r" items="${results }" varStatus="s">
  	<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>${r } <c:if test="${s.last != true}"><br></c:if>
  </c:forEach>
</div>
</c:if>
     	<form class="form-horizontal" action="admin/invi/sendinvimessage" method="POST">
     	<input type="hidden" value="${inviinfoid }" name="inviinfoid">
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">通知</label>
			<div class="col-sm-10 col-md-5">
				<select data-toggle="select" multiple="multiple" class="form-control multiselect multiselect-info" name="inviids">
					<c:forEach items="${invis }" var="i">
						<option value="${i.id }" selected>${i.teacher.user.name }	
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
						<div class="col-sm-2 col-md-1 control-label"></div>
						<div class="col-sm-10 col-md-3">
							
							<button type="submit" class="btn btn-primary btn-wide">提交</button>			
						</div>
					</div>
	</form>
    </jsp:body>
</myTemplate:template>