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
	        browseClass: "btn btn-primary",
			initialCaption: "上传监考文件",
		});
	})
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/usersetting">用户管理</a></li>
  <li class="active">导入监考信息</li>
</ol>
	
 	<form class="form-horizontal" enctype="multipart/form-data" action="admin/importinvi" method="post">
		<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="1" accept=".xls,.xlsx">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-10 col-md-8">
				<p class="text-danger">说明: 
				监考表格太过随意，字段没有规则可循；<br>
				课程名称可能包含中文字符、英文字符、数字(高等数学A1)，虽然可以区分年级字段，例如，软件工程导论与软件工程2013-01-2，
				但是，目前在没有课程名称数据的情况下无法完成监考课程的模糊匹配；<br>
				例如，无法区分会计学与阎为学那个字段是课程名称那个字段是主考教师；<br>
				自动过滤已包含监考信息，如监考信息有改变，如人数发生修改，在编辑中完成修改操作。<br>
				过滤规则：同一时间在同一地点不可能有多于1个考试。<br>
				<span class="label label-danger checkboxspan"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				测试期内建议与监考文件比对，防止出错</span>
				</p>
				
			</div>
		</div>
	</form>
     <c:if test="${infos != null }">
		    <table class="table table-striped">
		<thead>
			<tr>
				 <th>#</th>
                  <th>日期</th>
                  <th>时间</th>
                  <th>地点</th>
                  <th>人数</th>
                  <th>状态</th>
                  <th>导入时间</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${infos }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${i.startTime.getTime() }"/></td>
				<td><fmt:formatDate pattern="HH:mm" value="${i.startTime.getTime() }"/>
					- <fmt:formatDate pattern="HH:mm" value="${i.endTime.getTime() }"/></td>
				<td>${i.location }</td>
				<td>${i.requiredNumber }</td>
				<td>
					<c:if test="${i.currentStatusType.id == 1 }">
						<span class="label label-danger checkboxspan">
				</c:if>
				<c:if test="${i.currentStatusType.id == 2 }">
						<span class="label label-success checkboxspan">
				</c:if>
				<c:if test="${i.currentStatusType.id == 3 }">
						<span class="label label-info checkboxspan">
				</c:if>
				${i.currentStatusType.name }</span></td>
				<td><fmt:formatDate pattern="MM-dd HH:mm" value="${i.insertTime }" /></td>
				<td><a class="btn btn-primary" href="admin/updateinviinfo/${i.id }" role="button">
				<span class="glyphicon glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑</a>  
						<a class="btn btn-primary"  href="admin/assigninvi/${i.id }" role="button">分配</a></td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	<p class="text-danger">说明: 
	编辑，可对监考信息进行修改，添加监考课程名称等，提交后自动转到监考分配功能
	分配，直接分配该监考
	</p>
	</c:if>
	<c:if test="${exception != null}">
		&nbsp&nbsp
		<div class="alert alert-danger alert-dismissable" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
			</button><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  <strong>错误！</strong> ${exception }
</div>
</c:if>       
    </jsp:body>
</myTemplate:template>