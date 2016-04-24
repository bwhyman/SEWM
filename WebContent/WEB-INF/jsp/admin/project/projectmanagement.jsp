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
	  <li class="active">毕设管理</li>
	</ol>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" data-toggle="dropdown" >学生信息管理
					<span class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
						<li><a href="admin/project/importstuinfo">导入学生信息</a></li>
						<li><a href="admin/project/studentmanagement/students/1">学生信息管理</a></li>
					</ul>
					
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>导入学生信息（学号、姓名）</li>
				<li>学生信息管理包括密码重置、删除</li>
				<li>清除学生信息，主要用于信息导入错误，如已开始选题，<span class="label label-danger">禁止</span>该操作</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="row">
					<div class="col-md-2">
						<a class="btn btn-info btn-block" role="button" href="admin/project/divide">人数分配管理</a>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<ul>
					<li>分配教师所带毕业设计学生人数</li>
				</ul>
			</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="row">
					<div class="col-md-2">
						<a class="btn btn-info btn-block" role="button" href="admin/project/startproject">毕设题目管理</a>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<ul>
					<li>开启添加毕业设计题目功能</li>
					<li>上传论证报告模板</li>
				</ul>
			</div>
		</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" data-toggle="dropdown" >开题管理
					<span class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
						<li><a href="admin/project/uploadfile/openreport">开始/关闭开题</a></li>
						<li><a href="admin/project/listevaluation/opening/1">开题评审</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>开启选题功能</li>
				<li>上传开题报告模板</li>
				<li>上传开题记录模板</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" data-toggle="dropdown" >中期管理
					<span class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
						<li><a href="admin/project/uploadfile/interimreport">开始/关闭中期</a></li>
						<li><a href="admin/project/listevaluation/interim/1">中期评审</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>参考开题管理提示信息</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" data-toggle="dropdown" >终期管理
					<span class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
						<li><a href="admin/project/uploadfile/paperreport">开始/关闭终期</a></li>
						<li><a href="admin/project/listevaluation/paper/1">终期评审</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>参考开题管理提示信息</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" href="admin/project/downloadzip">下载Zip打包文件</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>分别下载Zip打包文件，包括论证报告、开题报告、开题答辩记录、中期报告、中期答辩记录、论文、论文答辩记录</li>
			</ul>
		</div>
	</div>
    </jsp:body>
</myTemplate:template>