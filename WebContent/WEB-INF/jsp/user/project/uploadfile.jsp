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
		$('.file-1').fileinput({
			showPreview : false,
	        browseClass: "btn btn-primary",
			initialCaption: "上传论证报告",
		});
	})
	
	
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="project/projectmanagement">毕设管理</a></li>
  <li class="active">上传论证报告</li>
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
	<c:forEach items="${demonFileDetails }" var="p">
		<div class="form-horizontal">
			<div class="form-group">
				<div class="col-sm-2 col-md-2 control-label">题目</div>
				<div class="col-sm-2 col-md-10">${p.title.name }</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-md-2 control-label">上次更新时间</div>
				<div class="col-sm-2 col-md-10"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${p.insertTime }"/></div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-md-2 control-label">当前论证报告</div>
				<div class="col-sm-2 col-md-10"><a href="download/${p.directory }/${p.fileName}/">${p.fileName}</a></div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-md-2 control-label">上传文件</div>
				<div class="col-sm-2 col-md-10">
					<form class="form-horizontal" enctype="multipart/form-data" action="project/uploadfiles" method="post">
						<div class="form-group myfileinput">
							<div class="col-sm-10 col-md-8">
								<input class="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
							</div>
						</div>
						<input type="hidden" name="fileDetailId" value="${p.id }">
					</form>
				</div>
			</div>
			
		</div>
		<hr/>
	</c:forEach>    
 	
    </jsp:body>
</myTemplate:template>