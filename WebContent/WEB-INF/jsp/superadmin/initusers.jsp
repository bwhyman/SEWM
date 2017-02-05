<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css"  rel="stylesheet">
</jsp:attribute>
<jsp:attribute name="footer">
<script>
	$(function() {
		$('#file-1').fileinput({
			showPreview : false,
			showUpload: false,
	        showRemove: false,
	        browseClass: "btn btn-primary",
			initialCaption: "导入初始化用户表格",
			maxFileSize: 50000,
		});
	})
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>
	<jsp:body>
	
	<form class="form-horizontal" action="superadmin/initusers" method="POST">
					<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="1" accept=".xls,.xlsx">
			</div>
		</div>
						
					<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label"></div>
						<div class="col-sm-10 col-md-4">
							<button type="submit" class="btn btn-primary btn-wide">提交</button>
							<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>	
						</div>
					</div>
					<div class="form-group">
			<div class="col-sm-2col-md-12">
				<p class="text-danger">说明: 基于用户姓名搜索
			</div>
		</div>
				</form>	
    </jsp:body>
</mybase:base>