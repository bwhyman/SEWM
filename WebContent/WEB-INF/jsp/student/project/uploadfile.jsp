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
<script>
	$(function() {
		$('#file-1').fileinput({
			showPreview : false,
	        browseClass: "btn btn-primary",
			initialCaption: "上传" + '${typeCH}',
		});
	})
	
	
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="student/project/projectmanagement/${stageType }">${stageTypeZH }信息</a></li>
  <li class="active">上传${typeZH }</li>
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
	<c:if test="${openReport.opened }">
		<form class="form-horizontal" enctype="multipart/form-data" action="student/project/uploadfile/${type }" method="post">
			<div class="form-group" id="myfileinput">
				<div class="col-sm-10 col-md-8">
					<input id="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
				</div>
			</div>
			<input type="hidden" name="typeId" value="${typeId }">
			<input type="hidden" name="stageType" value="${stageType }">
		</form>
	</c:if>
	<c:if test="${openRecord.opened }">
		<form class="form-horizontal" enctype="multipart/form-data" action="student/project/uploadfile/${type }" method="post">
			<div class="form-group" id="myfileinput">
				<div class="col-sm-10 col-md-8">
					<input id="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
				</div>
			</div>
			<input type="hidden" name="typeId" value="${typeId }">
			<input type="hidden" name="stageType" value="${stageType }">
		</form>
	</c:if>
	<c:if test="${interimReport.opened }">
		<form class="form-horizontal" enctype="multipart/form-data" action="student/project/uploadfile/${type }" method="post">
			<div class="form-group" id="myfileinput">
				<div class="col-sm-10 col-md-8">
					<input id="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
				</div>
			</div>
			<input type="hidden" name="typeId" value="${typeId }">
			<input type="hidden" name="stageType" value="${stageType }">
		</form>
	</c:if>
	<c:if test="${interimRecord.opened }">
		<form class="form-horizontal" enctype="multipart/form-data" action="student/project/uploadfile/${type }" method="post">
			<div class="form-group" id="myfileinput">
				<div class="col-sm-10 col-md-8">
					<input id="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
				</div>
			</div>
			<input type="hidden" name="typeId" value="${typeId }">
			<input type="hidden" name="stageType" value="${stageType }">
		</form>
	</c:if>
	<c:if test="${paperReport.opened }">
		<form class="form-horizontal" enctype="multipart/form-data" action="student/project/uploadfile/${type }" method="post">
			<div class="form-group" id="myfileinput">
				<div class="col-sm-10 col-md-8">
					<input id="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
				</div>
			</div>
			<input type="hidden" name="typeId" value="${typeId }">
			<input type="hidden" name="stageType" value="${stageType }">
		</form>
	</c:if>
	<c:if test="${paperRecord.opened }">
		<form class="form-horizontal" enctype="multipart/form-data" action="student/project/uploadfile/${type }" method="post">
			<div class="form-group" id="myfileinput">
				<div class="col-sm-10 col-md-8">
					<input id="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
				</div>
			</div>
			<input type="hidden" name="typeId" value="${typeId }">
			<input type="hidden" name="stageType" value="${stageType }">
		</form>
	</c:if>
	<c:if test="${!openReport.opened&&!openRecord.opened&&!interimReport.opened&&!interimRecord.opened&&!paperReport.opened&&!paperRecord.opened }">
		<div class="alert alert-warning" role="alert"><strong>该功能未开启或已关闭！</strong></div>
	</c:if>
 	
    </jsp:body>
</myTemplate:template>