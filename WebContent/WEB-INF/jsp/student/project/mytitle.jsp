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
  <li class="active">我的题目</li>
</ol>
	<c:if test="${fileDetail!=null }">
		<div class="panel panel-default">
			<!-- Table -->
			<table class="table">
			    <tbody>
			    	<tr>
			    		<td class="col-md-1">题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目:</td>
			    		<td>${fileDetail.title.name }</td>
			    	</tr>
			    	<tr>
			    		<td class="col-md-1">题目性质:</td>
			    		<td>${fileDetail.title.property }</td>
			    	</tr>
			    	<tr>
			    		<td class="col-md-1">指导教师:</td>
			    		<td>${fileDetail.title.teacher.user.name }</td>
			    	</tr>
			    	<tr>
			    		<td class="col-md-1">论证报告:</td>
			    		<td>
							<a href="download/${fileDetail.directory }/${fileDetail.fileName }/">${fileDetail.fileName }</a>
					    </td>
			    	</tr>
			    </tbody>
			</table>
		  <!-- Default panel contents -->
		  <div class="panel-heading">题目内容</div>
		  <div class="panel-body">
		    <p>${fileDetail.title.objective }</p>
		  </div>
	</div>
	</c:if>
	
    </jsp:body>
</myTemplate:template>