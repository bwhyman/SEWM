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
	<jsp:attribute name="footer">
		<script>
			$(function(){
				$('.mybtn').click(function(){
					$('#myalert').hide();
					var current = $(this);
					$.ajax({
						url:'admin/project/resetpassword',
						data:{'userId':current.attr('href')},
						type:'POST',
						dataType:'text',
						success:function(data){
							if(data == 'success'){
								$('#myalert').show();
							}
						}
					});
					return false;
				})
				
				$(".delbtn").click(function(){
					var current = $(this);
					$.post('admin/project/delstudent',{
						'userId':current.attr('href')
					},function(){
						location.href = 'admin/project/studentmanagement';
					})
					return false;
				})
			})
		</script>
	</jsp:attribute>
	<jsp:body>
		<ol class="breadcrumb">
		  <li><a href="">主页</a></li>
		  <li><a href="admin/project/projectmanagement">毕设管理</a></li>
		  <li class="active">学生密码重置</li>
		</ol>
			<div class="alert alert-success alert-dismissable" role="alert" hidden id="myalert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				 密码重置成功
			</div>
		<div>
			<a href="admin/project/clearStudents" class="btn btn-primary btn-wide">清除所有学生信息</a>
			<p class="text-danger">注意：该操作仅限于学生选题之前</p>
		</div>
		<div class="table-responsive">
			<table class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					 <th>#</th>
	                 <th>学号</th>
	                 <th>姓名</th>
	                 <th>操作</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${users }" var="u" varStatus="s">
						<tr>
							<td>${s.count }</td>
							<td>${u.employeeNumber }</td>
							<td>${u.name }</td>
							<td><a class="btn btn-primary btn-wide mybtn" href="${u.id }">密码重置</a>
							<a class="btn btn-primary btn-wide delbtn" href="${u.id }">删除</a></td>
						</tr>
				</c:forEach>
				</tbody>
		</table>
		</div>
    </jsp:body>
</myTemplate:template>