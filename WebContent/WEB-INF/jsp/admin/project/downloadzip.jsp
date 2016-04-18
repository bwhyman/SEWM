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
		  <li class="active">Zip下载</li>
		</ol>
		<c:if test="${exception != null}">
			&nbsp;&nbsp;
			<div class="alert alert-danger alert-dismissable" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				<strong>错误！</strong> ${exception }
			</div>
		</c:if>
		<c:forEach items="${fileTypes }" var="f">
			<p><a href="admin/project/downloadzip/${f.directory }/" class="btn btn-primary btn-wide">${f.directory }</a></p>
		</c:forEach>
    </jsp:body>
</myTemplate:template>