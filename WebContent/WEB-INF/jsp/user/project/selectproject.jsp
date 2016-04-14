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
	  <li class="active">选题信息</li>
	</ol>
	<c:forEach items="${teachers }" var="t">
		<a id="${t.id }" class="btn btn-primary" href="project/selecttitles/${t.id }" role="button" style="margin-bottom: 2px;">${t.user.name }(${t.leadNum })</a>
	</c:forEach>
	<a id="-1" class="btn btn-primary" href="project/selecttitles/-1" role="button">全部题目</a>
	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>题目</th>
                  <th>题目性质</th>
                  <th>论证报告</th>
                  <th>已选人数</th>
                  <th>已确认学生</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${fileDetails }" var="p" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td><a href="project/projecttitle/${p.id }">${p.title.name }</a></td>
				<td>${p.title.property }</td>
				
				<td>
					<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
				</td>
				<td>${p.title.selectedTitleDetails.size() }</td>
				<td>
					<c:forEach items="${p.title.selectedTitleDetails }" var="st">
						<c:if test="${st.confirmed == true }">
							<span class="label label-success">${st.student.student.name }</span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</myTemplate:template>