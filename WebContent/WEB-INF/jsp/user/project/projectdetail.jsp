<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<myTemplate:template>
	<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css" rel="stylesheet">
	</jsp:attribute>
	<jsp:attribute name="footer">
        
	</jsp:attribute>
	<jsp:body>
		<ol class="breadcrumb">
			<li><a href="">主页</a></li>
			<li><a href="project/projectmanagement">毕设管理</a></li>
	  		<li class="active">题目详细信息</li>
		</ol>
		<c:if test="${exception != null}">
		&nbsp&nbsp
		<div class="alert alert-danger alert-dismissable" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<strong>错误！</strong> ${exception }
		</div>
	</c:if>
	
	<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">题目</div>
			<div class="col-sm-2 col-md-4">${projectFileDetail.title.name }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">题目性质</div>
			<div class="col-sm-2 col-md-4">${projectFileDetail.title.property }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">指导教师</div>
			<div class="col-sm-2 col-md-4">${projectFileDetail.title.teacher.user.name }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">论证报告</div>
			<div class="col-sm-10 col-md-10">
				<a href="download/${projectFileDetail.directory }/${projectFileDetail.fileName }/">${projectFileDetail.fileName }</a>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">题目内容</div>
			<div class="col-sm-10 col-md-10">${projectFileDetail.title.objective }</div>
		</div>
	</div>	
    </jsp:body>
</myTemplate:template>