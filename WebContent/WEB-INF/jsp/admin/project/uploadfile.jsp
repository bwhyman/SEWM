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
			initialCaption: '上传' + '${typeReportCH}',
		});
		$('#file-2').fileinput({
			showPreview : false,
			showUpload: false,
	        browseClass: "btn btn-primary",
			initialCaption: '上传' + '${typeRecodeCH}',
		});
		
		if('${reportType.opened }' == 'true'){
			$('#myfileinput').show();
			$('#myfileinput1').show();
		}else{
			$('#myfileinput').hide();
			$('#myfileinput1').hide();
		}
		
		$('#opened').on('switchChange.bootstrapSwitch',function(event,state) {
        	$('#myfileinput').slideToggle('slow');
        	$('#myfileinput1').slideToggle('slow');
		});
	})
	
	
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/project/projectmanagement">毕设管理</a></li>
  <li class="active">${typeCH }</li>
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
	
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/project/uploadfile/${type }" method="post">
 	
 		<div class="form-group">
			<label for="opened" class="col-sm-2 col-md-1 control-label">${typeCH }</label>
			<div class="col-md-1">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
				<c:if test="${reportType.opened=='true' }">checked='checked'</c:if> name="opened"  value="1" id="opened"/>
			</div>
		</div>
		<input type="hidden" name="reportid" value="${reportType.id }">
		<input type="hidden" name="recordid" value="${recordType.id }">
		<div class="form-group" id="myfileinput">
			<label for="uploadFile" class="col-sm-2 col-md-1 control-label">${typeReportCH }</label>
			<div class="col-sm-10 col-md-8">
				<input id="file-1" type="file" name="uploadFiles" multiple data-min-file-count="0" accept=".doc,.docx">
			</div>
		</div>
		<div class="form-group" id="myfileinput1">
			<label for="uploadFile" class="col-sm-2 col-md-1 control-label">${typeRecodeCH }</label>
			<div class="col-sm-10 col-md-8">
				<input id="file-2" type="file" name="uploadFiles" multiple data-min-file-count="0" accept=".doc,.docx">
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