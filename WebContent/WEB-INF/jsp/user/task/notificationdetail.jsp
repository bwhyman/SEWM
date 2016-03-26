<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<myTemplate:template>
	<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css" rel="stylesheet">
	</jsp:attribute>
	<jsp:attribute name="footer">
	<script src="resources/js/fileinput.min.js"></script>
	<script>
		$(function() {
			$('#file-1').fileinput({
				showPreview : false,
				browseClass : "btn btn-primary",
				showUpload : false,
				showRemove : false,
				initialCaption : "上传任务文件",
				maxFileSize : 50000,
				disabled : true,
			});

			$('#implbutton').click(function() {
				$('#impldiv').slideToggle('slow');
				$('#closediv').slideUp();
			});
			$('#closebutton').click(function() {
				$('#closediv').slideToggle('slow');
				$('#impldiv').slideUp();
			});
			
		});
	</script>
        
	</jsp:attribute>
	<jsp:body>
		<ol class="breadcrumb">
		<li><a href="">主页</a></li>
		<li><a href="task/listnotification/started">通知信息</a></li>
		<li class="active">通知详细信息</li>
	</ol>
		<c:if test="${exception != null}">
		&nbsp;&nbsp;
		<div class="alert alert-danger alert-dismissable" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<strong>错误！</strong> ${exception }
		</div>
	</c:if>
	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				<th>截止时间</th>
				<th>地点</th>
				<th>开始时间</th>
				<th>状态</th>
				<c:if test="${notification.point != 0 }">
					<th>分值</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${notification.endTime.getTime() }" />
				</td>
				<td>${notification.location }</td>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${notification.insertTime }" />
				</td>
				<td>
					<c:if test="${isExpired == true }">
						<span class="label label-danger checkboxspan">已过期</span>
					</c:if>
					<c:if test="${isExpired == false }">
						<span class="label label-success checkboxspan">未过期</span>
					</c:if>
				</td>
				<c:if test="${notification.point!=0 }">
					<td>
						<span class="badge bg-primary checkboxspan">${notification.point }</span>
					</td>
				</c:if>
				
			</tbody>
	</table>
	</div>
	<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">通知</div>
			<div class="col-sm-2 col-md-4">${notification.comment }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">教师</div>
			<div class="col-sm-2 col-md-8">
				<c:forEach items="${notification.teachers }" var="d">
					${d.user.name }; 
				</c:forEach>
			</div>
		</div>
		
	</div>
	
    </jsp:body>
</myTemplate:template>