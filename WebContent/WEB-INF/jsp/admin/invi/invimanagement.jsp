<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:body>
	<ol class="breadcrumb">
		<li><a href="">主页</a></li>
		<li class="active">监考管理</li>
	</ol>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-sm-4 col-md-3 col-lg-2">
				<a class="btn btn-info btn-block" role="button" href="admin/invi/addinviinfo">添加监考信息</a>
					<!-- <div class="btn-group btn-block">
						<a class="btn btn-info dropdown-toggle  btn-block" role="button" data-toggle="dropdown">
							添加监考信息
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
							<li><a href="admin/invi/addinviinfo">添加监考信息</a></li>
							<li><a href="admin/invi/addspecinviinfo">添加特殊监考</a></li>
						</ul>
					</div> -->
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>添加普通/特殊监考</li>
				<!-- <li><a href="admin/invi/addinviinfo">添加监考信息</a></li> -->
				<li>
					<%-- <form action="admin/invi/sendinviremind" method="POST">
						<button type="submit" class="btn btn-primary btn-wide">发送明日监考提醒</button>
					</form> --%>
					<form action="admin/invi/setinviinfodone" method="POST">
						<button type="submit" class="btn btn-primary btn-wide">设置完成监考</button>
					</form>
				</li>
			</ul>
		</div>
	</div>

	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-sm-4 col-md-3 col-lg-2">
					<a class="btn btn-info btn-block" role="button" href="admin/invi/importinviinfos">导入监考信息</a>
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
				<div class="col-sm-4 col-md-3 col-lg-2">
				<a class="btn btn-info btn-block" role="button" href="admin/invi/importtimetable">导入课表文件</a>
					<!-- <div class="btn-group btn-block">
						<a class="btn btn-info dropdown-toggle  btn-block" role="button" data-toggle="dropdown">
							导入课表信息
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
							<li><a href="admin/invi/importtimetable">导入课表文件</a></li>
							<li><a href="admin/invi/importtimetabletask">导入课表任务</a></li>
						</ul>
					</div> -->
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
			<!-- <li><a href="admin/invi/importtimetable">导入课表文件</a></li> -->
				<li>导入课表文件，单独导入教师的课表文件</li>
				<!-- <li>导入课表任务，已关闭的上传课表文件任务，系统自动导入任务下所有教师的课表</li> -->
			</ul>
		</div>
	</div>


    </jsp:body>
</mybase:base>