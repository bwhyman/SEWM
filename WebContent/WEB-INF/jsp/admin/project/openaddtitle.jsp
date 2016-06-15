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
			showUpload: false,
	        browseClass: "btn btn-primary",
			initialCaption: "上传论证报告模板",
		});
		
		if('${startProject.opened }' == 'true'){
			$('#myfileinput').show();
		}else{
			$('#myfileinput').hide();
		}
		
		$('#opened').on('switchChange.bootstrapSwitch',function(event,state) {
        	$('#myfileinput').slideToggle('slow');
		});
	})
	
	
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/project/projectmanagement">毕设管理</a></li>
  <li class="active">开启毕设</li>
</ol>
	
	<c:if test="${exception != null}">
		&nbsp;&nbsp;
		<div class="alert alert-danger alert-dismissable col-md-9 col-md-offset-1" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</button><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		  <strong>错误！</strong> ${exception }
		</div>
<br><br><br>
</c:if>     
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/project/openaddtitle" method="post">
 	
 		<div class="form-group">
			<label for="opened" class="col-sm-2 col-md-2 control-label">开启题目管理</label>
			<div class="col-md-1">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
				<c:if test="${startProject.opened=='true' }">checked='checked'</c:if> name="opened"  value="1" id="opened"/>
			</div>
		</div>
		<input type="hidden" name="id" value="${startProject.id }">
		<div class="form-group" id="myfileinput">
			<label for="uploadFile" class="col-sm-2 col-md-2 control-label">论证报告模板</label>
			<div class="col-sm-10 col-md-8">
				<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="0" accept=".doc,.docx">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label"></div>
				<div class="col-sm-10 col-md-3">
					<button type="submit" class="btn btn-primary btn-wide">提交</button>
				</div>
			</div>
	</form>
     
	  
    </jsp:body>
</myTemplate:template>