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
  <li><a href="student/project/projectmanagement">毕设管理</a></li>
  <li class="active">题目信息</li>
</ol>

	<c:if test="${projectFileDetail!=null }">
		<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">题目</div>
			<div class="col-sm-2 col-md-4">${projectFileDetail.title.name }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">题目性质</div>
			<div class="col-sm-2 col-md-4">${projectFileDetail.title.property }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">指导教师</div>
			<div class="col-sm-2 col-md-4">${projectFileDetail.title.teacher.user.name }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">论证报告</div>
			<div class="col-sm-10 col-md-10">
				<a href="download/${projectFileDetail.directory }/${projectFileDetail.fileName }/">${projectFileDetail.fileName }</a>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">题目内容</div>
			<div class="col-sm-10 col-md-10">${projectFileDetail.title.objective }</div>
		</div>
	</div>	
	</c:if>
	<c:if test="${projectFileDetail==null }">
		<c:if test="${selectedTitleDetail!=null }">
			<div class="form-horizontal">
				<div class="form-group">
					<div class="col-sm-2 col-md-1 control-label">当前题目</div>
					<div class="col-sm-2 col-md-5">${selectedTitleDetail.title.name }</div>
				</div>
			</div>
		</c:if>
		<div class="table-responsive">
			<table class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					 <th>#</th>
	                  <th>题目</th>
	                  <th>题目性质</th>
	                  <th>指导教师</th>
	                  <th>论证报告</th>
	                  <th>已选人数</th>
	                  <th>操作</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${projectFileDetails }" var="p" varStatus="s">
						<c:if test="${p.student==null}">
							<tr>
								<td>${s.count }</td>
								<td>${p.title.name }</td>
								<td>${p.title.property }</td>
								<td>${p.title.teacher.user.name }</td>
								<td>
									<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
								</td>
								<td>${p.title.selectedTitleDetails.size() }</td>
								<td>
									<div class="form-horizontal">
									<div class="form-group">
										<div class="col-sm-2 col-md-1 control-label"><a class="btn btn-primary" href="project/projecttitle/${p.id }">详细</a></div>
										<div class="col-sm-2 col-md-1 col-md-offset-2 control-label">
											<form action="student/project/selecttitle" method="post">
												<input type="hidden" name="id" value="${p.title.id }">
												<button class="btn btn-primary" type="submit">选择</button>
											</form>
									</div>
									</div>
									
									</div>
								</td>
							</tr>
						</c:if>
					
				</c:forEach>
				</tbody>
		</table>
		</div>
	</c:if>
    </jsp:body>
</myTemplate:template>