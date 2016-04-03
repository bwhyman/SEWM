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
<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css"  rel="stylesheet">
</jsp:attribute>
<jsp:attribute name="footer">
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/project/projectmanagement">毕设管理</a></li>
  <li class="active">人数分配</li>
</ol>
	
	<c:if test="${exception != null}">
		&nbsp;&nbsp;
		<div class="alert alert-danger alert-dismissable" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</button><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <strong>错误！</strong> ${exception }
</div>
</c:if>     
	
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/project/divide" method="post">
 		<c:forEach items="${teachers }" var="t">
 			<div class="form-group">
				<label for="opened" class="col-sm-2 col-md-1 control-label">${t.user.name }</label>
				<div class="col-md-1">
					<input type="number" name="leadNum" min="0" value="${t.leadNum }">
				</div>
			</div>
 		</c:forEach>
 		
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label"></div>
			<div class="col-sm-10 col-md-3">
				<button type="submit" class="btn btn-primary btn-wide">提交</button>
			</div>
		</div>
	</form>
     
	  
    </jsp:body>
</myTemplate:template>