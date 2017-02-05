<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css" rel="stylesheet">
</jsp:attribute>
	
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">导入课表任务</li>
</ol>

 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/invi/importtimetabletask" method="post">
			<div class="form-group">
			<label for="title" class="col-sm-2 col-md-2 control-label">课表任务</label>
			<div class="col-sm-10 col-md-4">
				<select data-toggle="select" class="select select-primary mrs mbm" name="filetaskId">
					<option>课表任务
					<c:forEach items="${tasks }" var="t">
						<option value="${t.id }" >${t.name }
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
		<div class="col-sm-2 col-md-2 control-label"></div>
		<div class="col-sm-10 col-md-4">
			<button type="submit" class="btn btn-primary btn-wide">提交</button>
		</div>
	</div>
		<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<p class="text-danger">说明: 
				必须是已关闭的任务<br>
				自动判断是否为课表格式文件；仅专业教师课表可以导入；导入课表自动清空以前课程信息；</p>
			</div>
		</div>
	</form>
	
		<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
				 <th>教师</th>
                  <th>课程</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${teachers }" var="c" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${c.user.name }</td>
				<td>${c.courses.size() }</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	
    </jsp:body>
</mybase:base>