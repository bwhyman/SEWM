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
					<a class="btn btn-info btn-block" role="button" href="admin/invi/importinvi">导入监考信息</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>导入监考信息</li>
			</ul>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<div class="btn-group btn-block">
						<a class="btn btn-info dropdown-toggle  btn-block" role="button" data-toggle="dropdown">
							导入课表信息
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
							<li><a href="admin/invi/importtimetable">导入课表文件</a></li>
							<li><a href="admin/invi/importtimetabletask">导入课表任务</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>导入课表文件，单独导入教师的课表文件</li>
				<li>导入课表任务，已关闭的上传课表文件任务，系统自动导入任务下所有教师的课表</li>
			</ul>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" href="invi/listinviinfo/unassinvi/1">监考编辑/分配</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>对未分配监考完成监考分配。</li>
				<li>对已分配、已完成监考完成监考分配更新；当教师临时代替监考后，即使监考已完成也可强制重新声明监考安排，用于工作量的精确统计。</li>
				<li>编辑，修改监考信息，如时间、地点、监考人数等</li>
			</ul>
		</div>
	</div>



	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<div class="btn-group btn-block">
						<a class="btn btn-info dropdown-toggle  btn-block" role="button" data-toggle="dropdown">
							添加监考信息
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
							<li><a href="admin/invi/addinviinfo">添加监考信息</a></li>
							<li><a href="admin/invi/addspecinviinfo">添加特殊监考</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>添加普通/特殊监考</li>

			</ul>
		</div>
	</div>
    </jsp:body>
</myTemplate:template>