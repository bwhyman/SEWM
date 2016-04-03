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
<jsp:attribute name="footer">
	<script>
		$(function() {
			$('#'+'${type}').attr('class','btn btn-danger');
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
	  <li><a href="">主页</a></li>
	  <li><a href="project/projectmanagement">毕设管理</a></li>
	  <li class="active">${typeCH }</li>
	</ol>
	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>题目</th>
                  <th>${typeCH }</th>
                  <th>指导次数</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${fileDetails }" var="p" varStatus="s">
						<tr>
							<td>${s.count }</td>
							<td>${p.title.name }</td>
							<td><a href="download/${p.directory }/${p.fileName}/">${p.fileName }</a></td>
							<td>${p.guideRecords.size() }</td>
							<td>
								<a href="project/listguiderecord/${p.projectFileType.id }/${p.title.id}">指导记录</a>
								<a href="project/addguiderecord/${p.projectFileType.id }/${p.title.id}">添加指导记录</a>
							</td>
						</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</myTemplate:template>