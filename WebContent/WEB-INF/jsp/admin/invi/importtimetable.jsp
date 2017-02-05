<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css" rel="stylesheet">
</jsp:attribute>
	<jsp:attribute name="footer">
<script>
	$(function() {
		$('input[name=uploadFile]').fileinput({
			showPreview : false,
			/* browseClass: "btn btn-primary", */
			initialCaption : "上传课表文件",
		});
	})
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">导入课表</li>
</ol>
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/invi/importtimetable" method="post">
		<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<input type="file" name="uploadFile" multiple data-min-file-count="1" accept=".xls,.xlsx">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<p class="text-danger">说明: 自动判断是否为课表格式文件；仅专业教师课表可以导入；导入课表自动清空以前课程信息；</p>
			</div>
		</div>
	</form>
		
	<c:if test="${courses != null }">
		 <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>课程</th>
                  <th>地点</th>
                  <th>班级</th>
                  <th>课次</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${courses }" var="c" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${c.name }</td>
				<td>${c.location }</td>
				<td>${c.teachingClass }</td>
				<td>${c.courseSections.size() }</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	</c:if>
    </jsp:body>
</mybase:base>