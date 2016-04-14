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
			$('.selecttitle').click(function(){
				var current = $(this);
				var strs= new Array();
				strs = current.attr('href').split(",");
				$.post('student/project/selecttitle',{
					'titleId':strs[0],
					'teacherId':strs[1]},function(){
					location.href = 'student/project/listtitles/' + '${type}';
				}) 
				return false;
			})
			
			$('.myspan').each(function(){
				$(this).parent('td').children('a').hide()
			})
			$('.selecttitle').next('a').hide();
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="student/project/projectmanagement">毕设管理</a></li>
  <li class="active">题目信息</li>
</ol>

	<c:if test="${fileDetail!=null }">
		<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label">题目</div>
			<div class="col-sm-2 col-md-4">${fileDetail.title.name }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label">题目性质</div>
			<div class="col-sm-2 col-md-4">${fileDetail.title.property }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label">指导教师</div>
			<div class="col-sm-2 col-md-4">${fileDetail.title.teacher.user.name }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label">论证报告</div>
			<div class="col-sm-10 col-md-9">
				<a href="download/${fileDetail.directory }/${fileDetail.fileName }/">${fileDetail.fileName }</a>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label">题目内容</div>
			<div class="col-sm-10 col-md-10">${fileDetail.title.objective }</div>
		</div>
	</div>	
	</c:if>
	<c:if test="${fileDetail==null }">
		<c:if test="${selectedTitleDetail != null}">
			<div class="alert alert-success alert-dismissable" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				<strong>已选题目：</strong> ${selectedTitleDetail.title.name }
			</div>
		</c:if>
		<c:forEach items="${teachers }" var="t">
			<a id="${t.id }" class="btn btn-primary" href="student/project/listtitles/${t.id }" role="button" style="margin-bottom: 2px;">${t.user.name }(${t.leadNum })</a>
		</c:forEach>
		<a id="-1" class="btn btn-primary" href="student/project/listtitles/-1" role="button">全部题目</a>
		
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
	                  <th>已确认学生/操作</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${fileDetails }" var="p" varStatus="s">
							<tr>
								<td>${s.count }</td>
								<td><a href="project/projecttitle/${p.id }">${p.title.name }</a></td>
								<td>${p.title.property }</td>
								<td>${p.title.teacher.user.name }</td>
								<td>
									<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
								</td>
								<td>${p.title.selectedTitleDetails.size() }</td>
								<td>
										<c:forEach items="${p.title.selectedTitleDetails }" var="st">
											<c:if test="${st.confirmed == true }">
												<span class="label label-success myspan">${st.student.student.name }</span>
											</c:if>
											<c:if test="${st.confirmed == false }">
												<a href="${p.title.id },${p.title.teacher.user.id}" class="selecttitle" id="${p.title.teacher.user.id}">选择</a>
											</c:if>
										</c:forEach>
										<c:if test="${p.title.selectedTitleDetails.size()==0 }">
											<a href="${p.title.id },${p.title.teacher.user.id}" class="selecttitle" id="${p.title.teacher.user.id}">选择</a>
										</c:if>
								</td>
							</tr>
				</c:forEach>
				</tbody>
		</table>
		</div>
	</c:if>
    </jsp:body>
</myTemplate:template>