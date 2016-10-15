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
<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css"  rel="stylesheet">
</jsp:attribute>
<jsp:attribute name="footer">
<script>
	$(function() {
		$('#file-1').fileinput({
			showPreview : false,
			showUpload: false,
	        showRemove: false,
	        browseClass: "btn btn-primary",
			initialCaption: "上传监考文件",
			maxFileSize: 50000,
		});
	})
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">导入监考信息</li>
</ol>
	
	<c:if test="${exception != null}">
		&nbsp&nbsp
		<div class="alert alert-danger alert-dismissable" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</button><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <strong>错误！</strong> ${exception }
</div>
</c:if>     
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/invi/importinviinfos" method="post">
 	<c:forEach items="${infos }" var="i">
		<input type="hidden" name="inviids" value="${i.id }">
		</c:forEach>
		<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="1" accept=".xls,.xlsx">
			</div>
		</div>
		<div class="form-group">
		<label for="name" class="col-sm-2 col-md-2 control-label">阶段考试</label>
	<div class="col-sm-10 col-md-4">
		<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default" class="myswitch" 
		name="checked" value="true" />
	</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2 col-md-2 control-label"></div>
		<div class="col-sm-10 col-md-10">
			<p class="text-danger">
			阶段考试，自动在备注/课程后追加"阶段"字样</p>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2 col-md-2 control-label"></div>
		<div class="col-sm-10 col-md-4">
			<button type="submit" class="btn btn-primary btn-wide">提交</button>
		</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2col-md-12">
				<p class="text-danger">说明: 监考表格太过随意，字段规则模糊，
				因此默认将<span class="label label-danger checkboxspan">表格第一列设为考试课程</span>，请修改表格后加载；<br>
				导入监考文件不会对数据库中已包含的监考信息做出修改<br>
				判定规则：与系统原数据比对，考试时间地点相同，即判定为同一考试，因同一地点不可能同时有2个考试<br>
				因此，如果同一课程监考信息发生重大改变，如修改了考试地点或时间，则系统无法检测，将生成一个新监考信息；因此，需在监考编辑中修改信息<br>
				如导入的监考时间地点信息与原信息相同，但是监考人数不同，则系统将更新该监考的监考人数，同时将该监考置为未分配状态，便于操作者重新分配<br>
				测试期内建议与监考文件比对，防止出错
			</div>
		</div>
	</form>
     <c:if test="${infos != null }">
		 <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
				 <th>课程/备注</th>
                  <th>日期</th>
                  <th>时间</th>
                  <th>地点</th>
                  <th>人数</th>
                  <th>状态</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${infos }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${i.comment }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${i.startTime.getTime() }"/></td>
				<td><fmt:formatDate pattern="HH:mm" value="${i.startTime.getTime() }"/>
					- <fmt:formatDate pattern="HH:mm" value="${i.endTime.getTime() }"/></td>
				<td>${i.location }</td>
				<td>${i.requiredNumber }</td>
				<td>
				<c:if test="${i.currentStatusType != null }">
					<c:if test="${i.currentStatusType.id == 1 }">
						<span class="label label-danger checkboxspan">
						未分配
						</span>
				</c:if>
				<c:if test="${i.currentStatusType.id == 2 }">
						<span class="label label-success checkboxspan">
						已分配
						</span>
				</c:if>
				<c:if test="${i.currentStatusType.id == 3 }">
						<span class="label label-info checkboxspan">
						已完成
						</span>
				</c:if>
			
				</c:if>
				</td>
				
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	<div>
		<form class="form-horizontal" action="admin/invi/saveinviinfos" method="post">
		<div class="form-group">
		<div class="col-sm-2 col-md-2 control-label"></div>
		<div class="col-sm-10 col-md-4">
			<button type="submit" class="btn btn-primary btn-wide">提交</button>
		</div>
		</div>
		</form>
	</div>
	</c:if>
    </jsp:body>
</myTemplate:template>