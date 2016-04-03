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
		$(function(){
			$('#'+'${type}').attr('class','btn btn-danger');
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
	  <li><a href="">主页</a></li>
	  <li><a href="project/projectmanagement">毕设管理</a></li>
	  <li class="active">结果信息</li>
	</ol>
	<a id="selected" class="btn btn-primary" href="project/selectresult/selected" role="button">已选题</a>
	<a id="unselect" class="btn btn-primary" href="project/selectresult/unselect" role="button">未选题</a>
	<a class="btn btn-primary" href="project/exportSelectResult" role="button">导出选题信息</a>
	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>学号</th>
                  <th>学生</th>
                  <th>题目</th>
                  <th>指导老师</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${students }" var="st" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${st.user.employeeNumber }</td>
				<td>${st.user.name }</td>
				<td>${st.selectedTitleDetail.title.name }</td>
				<td>${st.selectedTitleDetail.title.teacher.user.name }</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</myTemplate:template>