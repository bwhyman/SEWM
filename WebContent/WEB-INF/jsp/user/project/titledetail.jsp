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
	<jsp:body>
		<ol class="breadcrumb">
			<li><a href="">主页</a></li>
			<li><a 
				<c:if test="${user.userAuthority.level>=10 }">
					href="project/listtitles/${title.teacher.id }/1"
				</c:if>
				<c:if test="${user.userAuthority.level==5 }">
					href="student/project/listtitles/-1/1"
				</c:if>
			>题目信息</a></li>
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
	
	<div class="panel panel-default">
		<!-- Table -->
		<table class="table">
		    <tbody>
		    	<tr>
		    		<td class="col-md-1">题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目:</td>
		    		<td>${title.name }</td>
		    	</tr>
		    	<tr>
		    		<td class="col-md-1">题目性质:</td>
		    		<td>${title.property }</td>
		    	</tr>
		    	<tr>
		    		<td class="col-md-1">指导教师:</td>
		    		<td>${title.teacher.user.name }</td>
		    	</tr>
		    	<tr>
		    		<td class="col-md-1">论证报告:</td>
		    		<td>
						<c:forEach items="${title.projectFileDetails }" var="p">
							<c:if test="${p.projectFileType.id == 1 }">
								<a href="download/${p.directory }/${p.fileName}/">${p.fileName }</a>
							</c:if>
						</c:forEach>
				    </td>
		    	</tr>
		    </tbody>
		</table>
	  <!-- Default panel contents -->
	  <div class="panel-heading">题目内容</div>
	  <div class="panel-body">
	    <p>${title.objective }</p>
	  </div>
	</div>
    </jsp:body>
</myTemplate:template>