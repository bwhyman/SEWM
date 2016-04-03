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
  <li class="active">毕设信息</li>
</ol>
	
		<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" data-toggle="dropdown" >添加毕设题目
					<span class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
						<c:if test="${demonstration.opened == true }">
							<li><a href="project/addtitle">添加毕设题目</a></li>
						</c:if>
						<li><a href="download/${demonstration.directory }/${demonstration.templeteFile}/">下载论证报告模板</a></li>
						<li><a href="project/uploadfiles">上传论证报告</a></li>
					</ul>
					
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>下载毕业设计论证报告模板文件，并填写相关内容，添加毕业设计题目时要求上传
				<li>添加毕业设计题目，包括题目、专业、题目性质、立体依据、上传论证报告</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" href="project/listtitles/${user.id }">毕设题目</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>个人题目、全部题目</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" data-toggle="dropdown" >选题信息
					<span class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
						<c:if test="${demonstration.opened == true }">
							<li><a href="project/myselecttitles">确认选题</a></li>
						</c:if>
						<li><a href="project/selecttitles/${user.id }">选题信息</a></li>
						<li><a href="project/selectresult/selected">结果信息</a></li>
					</ul>
					
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>确认学生选题、查看选题信息</li>
				<li>结果信息是教师确认学生后的选题信息，已选题表示已被老师确认选题，未选题包括退选、未选题学生</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" href="project/listguiderecord/openreport">开题管理</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>查看学生开题报告，并添加指导记录（重点内容说明、上传修改文件），可查看历史指导记录</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" href="project/listguiderecord/interimreport">中期管理</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>参考开题管理提示说明</li>
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-info btn-block" role="button" href="project/listguiderecord/paperreport">论文管理</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>参考开题管理提示说明</li>
			</ul>
		</div>
	</div>
    </jsp:body>
</myTemplate:template>