<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
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
		<li><a href="task/list/started">任务信息</a></li>
		<li class="active">任务详细信息</li>
	</ol>
		<c:if test="${exception != null}">
		&nbsp&nbsp
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
				<th>发布</th>
				<th>状态</th>
				<th>分值</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${task.endTime.getTime() }" />
				</td>
				<td>${task.createUser.user.name }</td>
				<td>
					<c:if test="${task.currentStatus.id == 3 }">
						<span class="label label-danger checkboxspan">
					
						</c:if>
					<c:if test="${task.currentStatus.id == 2 }">
						<span class="label label-warning checkboxspan">
					
						</c:if>
					<c:if test="${task.currentStatus.id == 1 }">
						<span class="label label-success checkboxspan">	
						</c:if>
					${task.currentStatus.name }
					</span>
				</td>
				<td>
					<span class="badge bg-primary checkboxspan">${task.point }</span>
				</td>
			</tbody>
	</table>
	</div>
	<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">任务</div>
			<div class="col-sm-2 col-md-4">${task.name }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">说明</div>
			<div class="col-sm-2 col-md-4">${task.comment }</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">教师</div>
			<div class="col-sm-2 col-md-8">
				<c:forEach items="${task.fileTaskDetails }" var="d">
					${d.teacher.user.name }; 
				</c:forEach>
			</div>
		</div>
		<c:if test="${task.singleFile == true }">
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label"><span class="label label-danger">任务类型</span></div>
			<div class="col-sm-2 col-md-4"><span class="label label-danger">单一文件</span></div>
		</div>
		</c:if>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label">模板</div>
			<div class="col-sm-2 col-md-4">
				<c:if test="${task.templeteFile == null }">
					无
				</c:if>
				<c:if test="${task.templeteFile != null }">
					<a class="btn btn-primary" role="button" href="download/${task.directory}/${task.templeteFile}/">下载任务模板文件</a>
				</c:if>
			</div>
		</div>
	</div>
	<div class="form-horizontal">
		<c:if test="${task.currentStatus.id < 3}">
			<div class="form-group">
				<div class="col-sm-2 col-md-1 control-label"></div>
				<div class="col-sm-10 col-md-5">
					<a class="btn btn-primary btn-wide" role="button" id="implbutton">实现</a>
					<c:if test="${user.userAuthority.level >=15 }">
						<a class="btn btn-primary btn-wide" href="admin/task/updatefiletask/${task.id }" role="button">编辑</a>
						<a class="btn btn-primary btn-wide" role="button" id="closebutton">关闭</a>
					</c:if>
				</div>
			</div>
		</c:if>
	</div>

	<div id="impldiv" hidden>
		<c:if test="${task.currentStatus.id == 2}">
		&nbsp&nbsp
		<div class="alert alert-danger alert-dismissable" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				<strong>任务已过期！</strong> 减少
				<span class="badge bg-primary checkboxspan">${task.point }</span>
				分
			</div>
		</c:if>
		<form class="form-horizontal" action="task/implementfiletask" enctype="multipart/form-data" method="POST">
			<input type="hidden" value="${task.id }" name="taskid">
			<div class="form-group">
				<label for="name" class="col-sm-2 col-md-1 control-label">任务文件</label>
				<div class="col-sm-10 col-md-5">
					<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="0" accept="${task.fileType.type }">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-md-1 control-label"></div>
				<div class="col-sm-10 col-md-5">
					<button type="submit" class="btn btn-primary btn-wide">提交</button>
					<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>
				</div>
			</div>
		</form>
		<p class="text-danger">说明: 
	模板：教师需填写的模板文件，或发布者提供的参考文件，没有则为空<br>
	单一文件：所有任务教师在同一模板文件中添加数据信息；
	基于文档名称中的时间完成版本校验；<span class="label label-danger">勿修改文件名称</span><br>
	任务文件：上传的任务文件，可以为空<br>
	系统无法判断教师是没有完成任务还是不需要完成任务。<br>
	例如，每位教师都需要上传课表文件，而没有上传的教师系统无法判断是其没有按时完成任务还是本学期没有课而无需完成上传任务。<br>
	因此，教师可不选择上传文件而直接提交，或关闭上传文件功能后提交，则系统判定其任务完成<br>
	重复上传自动覆盖原文件；上传文件后，再提交空文件，则删除原上传文件，但任务状态仍为完成。相当于删除文件。
	</p> 
	</div>
	
	<div id="closediv" hidden>
	<form class="form-horizontal" action="admin/task/closefiletask" method="POST">
		<input type="hidden" value="${task.id }" name="filetaskid">
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">
				<span class="label label-danger">未完成</span>
			</label>
			<div class="col-sm-10 col-md-3">
				<select data-toggle="select" multiple="multiple" class="form-control multiselect multiselect-info"
							name="undoneusers" id="select">
					<c:forEach items="${task.fileTaskDetails }" var="f">
						<option value="${f.teacher.id }" <c:if test="${f.done ==  false}">selected</c:if>>${f.teacher.user.name }
							</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label"></div>
			<div class="col-sm-10 col-md-5">
				<button type="submit" class="btn btn-primary btn-wide">提交</button>
			</div>
		</div>
	</form>
	<p class="text-danger">
		说明: 关闭任务：正式结束此任务，教师无法再上传任务文件，完成任务情况统计<br> 
	</p>
	</div>
	
	<br>
	

	<a class="btn btn-primary" role="button" href="task/downloadzip/${task.directory}/">ZIP打包下载</a>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>#</th>
				<th>教师</th>
				<th>状态</th>
				<th>完成时间</th>
				<th>文件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${task.fileTaskDetails }" var="f" varStatus="s">
				<tr>
					<td>${s.count }</td>
					<td>${f.teacher.user.name }</td>
					<td>
						<c:if test="${f.done == true }">
							<span class="label label-success checkboxspan">已完成</span>
						</c:if>
						<c:if test="${f.done == false }">
							<span class="label label-danger checkboxspan">未完成</span>
						</c:if>
					</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${f.completeTime.getTime() }" />
					</td>
					<td>
						<c:if test="${f.file != null }">
							<a class="btn btn-primary" role="button" href="download/${task.directory}/${f.file}/">${f.file }</a>
						</c:if>
					</td>
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
    </jsp:body>
</mybase:base>