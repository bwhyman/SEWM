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
	  <li class="active">${typeZH }评审结果</li>
	</ol>
	<c:if test="${evaluation!=null }">
		<div class="table-responsive">
			<table class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					 <th>#</th>
	                 <th>学号</th>
	                 <th>姓名</th>
                     <th>导师评审</th>
                     <th>专业评审</th>
				</tr>
				</thead>
				<tbody>
						<tr>
							<td>1</td>
							<td>${evaluation.student.student.studentId }</td>
							<td>${evaluation.student.student.name }</td>
							<td>
								<c:if test="${evaluation.teacherEval == true }">
									<span class="label label-success">是</span>
								</c:if>
								<c:if test="${evaluation.teacherEval == false }">
									<span class="label label-danger">否</span>
								</c:if>
							</td>
							<td>
								<c:if test="${evaluation.managerEval == true }">
									<span class="label label-success">是</span>
								</c:if>
								<c:if test="${evaluation.managerEval == false }">
									<span class="label label-danger">否</span>
								</c:if>
							</td>
						</tr>
				</tbody>
		</table>
		</div>
	</c:if>
    </jsp:body>
</myTemplate:template>