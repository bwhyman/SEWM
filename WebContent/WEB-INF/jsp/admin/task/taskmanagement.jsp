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
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li class="active">监考管理</li>
</ol>
	
		<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="admin/task/addfiletask">创建文件任务</a>
			</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>创建文件任务，支持基于版本控制的单文件任务</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<div class="btn-group btn-block">
						<a class="btn btn-info dropdown-toggle  btn-block" role="button" data-toggle="dropdown">
							创建通知
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
							<li><a href="admin/task/addnotification">创建普通通知</a></li>
							<li><a href="admin/invi/addspecinviinfo">创建任务通知</a></li>
						</ul>
					</div>
			</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>普通通知，没有分值、无需关闭的通知</li>
				<li>任务通知，虽然无需上传文件，当仍有任务分值，需手动关闭并决定教师完成状态</li>
				<li>例如，会议通知，如果需要基于教师出勤或会议表现，如前期工作是否完成等决定教师分数，则创建任务通知，关闭时决定教师完成状态；</li>
				<li>如仅发送简单的告知通知，则创建普通通知即可</li>
			</ul>
		</div>
	</div>
	
    </jsp:body>
</myTemplate:template>