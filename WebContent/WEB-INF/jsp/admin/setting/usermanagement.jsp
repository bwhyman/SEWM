<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>

	<jsp:body>
	<ol class="breadcrumb">
		<li><a href="">主页</a></li>
		<li class="active">用户管理</li>
	</ol>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="admin/setting/adduser">添加用户</a>
			</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>添加用户</li>
			</ul>
		</div>
	</div>
	
		<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="admin/setting/updateuser">更新用户基本信息</a>
			</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>更新用户基本信息如，默认密码重置、姓名、员工号、手机号等</li>
			</ul>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="admin/setting/updateauth">用户权限管理</a>
			</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li><span class="label label-info">增加/删除</span>系统管理员</li>
			</ul>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="admin/setting/updateinvi">用户监考设置</a>
			</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li><span class="label label-info">开启/关闭</span>用户监考推荐功能，修改特殊监考次数。</li>
				<li>为了保证数据一致性，<span class="label label-warning">没有提供</span>修改普通监考次数功能。</li>
				<li>任务通知功能关闭的用户自动不被推荐。</li>
			</ul>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
			<div class="col-md-2">
			<a class="btn btn-info btn-block" role="button" href="admin/setting/updatenotif">用户通知设置</a>
			</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li><span class="label label-info">开启/关闭</span>用户通知功能，不会向关闭用户发送通知或推荐监考。</li>
				<li>如教师出国无法接收通知时，建议<span class="label label-warning">关闭</span>。</li>
				<li>如教师长期在外学习，但仍需按时完成专业任务时，建议保持<span class="label label-info">开启</span>。</li>
			</ul>
		</div>
	</div>

	</jsp:body>
</mybase:base>