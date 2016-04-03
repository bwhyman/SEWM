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
						<a class="btn btn-info btn-block" role="button" href="student/project/listtitles/-1">题目信息</a>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<ul>
					<li>查看题目信息，教师后面的数字是教师最多能带学生人数，选择适合自己的毕设题目</li>
					<li>如果多人选择同一题目，导师将根据学生实际情况进行确认哪位学生选题成功</li>
					<li>重新选择题目后，之前选择的自动无效</li>
					<li><span class="label label-danger">第二次开题的同学</span>，确认选题成功后，不能上传任何文档，如果上传将自动视为第一次开题，不能更改</li>
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
						<li><a href="download/${openReport.directory }/${openReport.templeteFile}/">下载开题报告模板</a></li>
						<li><a href="download/${openRecord.directory }/${openRecord.templeteFile}/">下载答辩记录模板</a></li>
						<c:if test="${openedProject == false }">
							<li><a href="student/project/uploadfile/openreport">上传开题报告</a></li>
							<li><a href="student/project/uploadfile/openrecord">上传开题答辩记录</a></li>
						</c:if>
						<c:if test="${openedProject == true }">
							<li><a href="student/project/listguiderecord/${openReport.id }">指导记录</a></li>
						</c:if>
					</ul>
					
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>下载开题报告模板文件，填写相关内容，并按要求上传</li>
				<li>下载开题答辩记录模板文件，填写相关内容，并按要求上传</li>
				<li>再次上传将覆盖之前上传的文件</li>
				<li>查看指导记录后，若需要修改开题报告，修改后请重新上传</li>
				<li class="text-danger">说明: 请在截止时间前完成相关文档上传，截止时间之后将无法再上传文件</li>
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
						<li><a href="download/${interimReport.directory }/${interimReport.templeteFile}/">下载中期检查报告模板</a></li>
						<li><a href="download/${interimRecord.directory }/${interimRecord.templeteFile}/">下载中期答辩记录模板</a></li>
						<c:if test="${openedProject == true && interimReport.opened == true }">
							<li><a href="student/project/uploadfile/interimreport">上传中期检查报告</a></li>
							<li><a href="student/project/uploadfile/interimrecord">上传中期答辩记录</a></li>
							
						</c:if>
						<c:if test="${openedProject == true }">
							<li><a href="student/project/listguiderecord/${interimReport.id }">指导记录</a></li>
						</c:if>
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
					<a class="btn btn-info btn-block" role="button" data-toggle="dropdown" >论文管理
					<span class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-inverse" role="menu">
						<li><a href="download/${paperReport.directory }/${paperReport.templeteFile}/">下载论文模板</a></li>
						<li><a href="download/${paperRecord.directory }/${paperRecord.templeteFile}/">下载论文答辩记录模板</a></li>
						<c:if test="${openedProject == true && paperReport.opened == true }">
							<li><a href="student/project/uploadfile/paperreport">上传论文</a></li>
							<li><a href="student/project/uploadfile/paperrecord">上传论文答辩记录</a></li>
						</c:if>
						<c:if test="${openedProject == true }">
							<li><a href="student/project/listguiderecord/${paperReport.id }">指导记录</a></li>
						</c:if>
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
    </jsp:body>
</myTemplate:template>