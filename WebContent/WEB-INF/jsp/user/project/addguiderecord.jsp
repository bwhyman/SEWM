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
			initialCaption: '上传修改文件',
		});
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
  <li><a href="project/projectmanagement">毕设管理</a></li>
  <li><a href="project/listguiderecord/openreport">${typeCH }</a></li>
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
	
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="project/addguiderecord" method="post">
	 	<div class="form-group">
			<label for="comment" class="col-sm-2 col-md-2 control-label">指导内容</label>
				<div class="col-sm-10 col-md-3">
					<textarea class="form-control" rows="10" placeholder="指导内容" name="comment" id="editor_id"></textarea>
				</div>
		</div>
		<input type="hidden" name="fileTypeId" value="${fileTypeId }">
		<input type="hidden" name="titleId" value="${titleId }">
		
		<div class="form-group">
			<label for="opened" class="col-sm-2 col-md-2 control-label">上传文件</label>
			<div class="col-md-1">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
				 name="opened"  value="1" id="opened"/>
			</div>
		</div>
		
		<div class="form-group" id="myfileinput" hidden>
			<label for="uploadfile" class="col-sm-2 col-md-2 control-label">修改文件</label>
			<div class="col-sm-10 col-md-8">
				<input id="file-1" type="file" name="uploadfile" multiple data-min-file-count="0" accept=".doc,.docx">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label"></div>
				<div class="col-sm-10 col-md-3">
					<button type="submit" class="btn btn-primary btn-wide">提交</button>
				</div>
			</div>
	</form>
     
	<script charset="utf-8" src="resources/editor/kindeditor-all-min.js"></script>
	<script charset="utf-8" src="resources/editor/lang/zh-CN.js"></script>
	<script>

		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('#editor_id', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [ 'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist','|', 
				          'formatblock', 'fontname', 'fontsize', '|',
				          'forecolor', 'hilitecolor', 'bold','italic', 'underline']
			});
		});
	</script>  
    </jsp:body>
</myTemplate:template>