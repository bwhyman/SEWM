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
			initialCaption: "上传学生信息文件",
		});
	})
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
	  <li><a href="">主页</a></li>
	  <li><a href="admin/project/projectmanagement">毕设管理</a></li>
	  <li class="active">导入学生信息</li>
	</ol>
	
	<c:if test="${exception != null}">
		&nbsp&nbsp
		<div class="alert alert-danger alert-dismissable" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</button><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <strong>错误！</strong> ${exception }
</div>
</c:if>     
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/project/importstu" method="post">
		<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="1" accept=".xls,.xlsx">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-12">
				<p class="text-danger">说明: 
				表格第一行列名称学号、姓名，第二行开始为数据<br>
				<span class="label label-danger checkboxspan">
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
						测试期内建议与上传文件比对，防止出错</span>
				</p>
				
			</div>
		</div>
	</form>
     <c:if test="${users != null }">
		 <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>学号</th>
                  <th>姓名</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${users }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${i.employeeNumber }</td>
				<td>${i.name }</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	</c:if>
	  
    </jsp:body>
</myTemplate:template>