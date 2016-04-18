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
					<a class="btn btn-info btn-block" role="button" href="admin/project/uploadfile/openreport">开题管理</a>
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
					<a class="btn btn-info btn-block" role="button" href="admin/project/uploadfile/interimreport">中期管理</a>
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
					<a class="btn btn-info btn-block" role="button" href="admin/project/uploadfile/paperreport">终期管理</a>
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