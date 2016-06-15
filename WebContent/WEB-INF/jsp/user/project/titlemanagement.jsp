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
					<a class="btn btn-info btn-block" role="button" href="project/listtitles/${user.id }/1">毕设题目</a>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<ul>
				<li>个人题目、全部题目</li>
			</ul>
		</div>
	</div>

    </jsp:body>
</myTemplate:template>