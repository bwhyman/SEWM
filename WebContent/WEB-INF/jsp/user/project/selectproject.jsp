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
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="project/projectmanagement">毕设管理</a></li>
  <li class="active">选题信息</li>
</ol>

	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>题目</th>
                  <th>题目性质</th>
                  <th>论证报告</th>
                  <th>已选人数</th>
                  <th>已确认学生/操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${projectFileDetails }" var="p" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${p.title.name }</td>
				<td>${p.title.property }</td>
				<td>${p.title.selectedTitleDetails.size() }</td>
				<td>
					<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
				</td>
				<td>
					<c:if test="${p.student!=null }">
						<span class="label label-success">${p.student.user.name }</span>
					</c:if>
					<c:if test="${p.student==null }">
						<a href="project/findprojectdetail/${p.id }">详细</a>
					</c:if>
					
				</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</myTemplate:template>