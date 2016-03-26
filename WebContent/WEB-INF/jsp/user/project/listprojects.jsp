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
  <li class="active">题目信息</li>
</ol>
	<a id="mytitles" class="btn btn-primary" href="project/listtitles/mytitles" role="button">我的题目</a>
	<a id="all" class="btn btn-primary" href="project/listtitles/all" role="button">全部题目</a>
	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>题目</th>
                  <th>题目性质</th>
                  <th>指导教师</th>
                  <th>论证报告</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${projectFileDetails }" var="p" varStatus="s">
						<tr>
							<td>${s.count }</td>
							<td>${p.title.name }</td>
							<td>${p.title.property }</td>
							<td>${p.title.teacher.user.name }</td>
							<td>
								<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
							</td>
							<td>
								<a href="project/projecttitle/${p.id }">详细</a>
							</td>
						</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</myTemplate:template>