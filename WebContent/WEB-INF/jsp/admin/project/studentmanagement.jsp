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
						data:{'studentId':current.attr('href')},
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
						'studentId':current.attr('href')
					},function(){
						location.href = 'admin/project/studentmanagement';
					})
					return false;
				})
				if('${currentPage}'=='1'){
					$('#previous').addClass('disabled');
					$('#previous').click(function(){
						return false;
					})
				}else{
					$('#previous').removeClass('disabled');
				}
				if('${currentPage}'=='${countPage}'){
					$('#next').addClass('disabled');
					$('#next').click(function(){
						return false;
					})
				}else{
					$('#next').removeClass('disabled');
				}
			})
		</script>
	</jsp:attribute>
	<jsp:body>
		<ol class="breadcrumb">
		  <li><a href="">主页</a></li>
		  <li><a href="admin/project/projectmanagement">毕设管理</a></li>
		  <li class="active">学生信息管理</li>
		</ol>
			<div class="alert alert-success alert-dismissable" role="alert" hidden id="myalert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				 密码重置成功
			</div>
		<div>
			<a href="admin/project/clearStudents" class="btn btn-primary btn-wide">清除所有学生信息</a>
			<p class="text-danger">注意：该操作仅限于学生选题之前</p>
		</div>
		<c:if test="${students.size()!=0 }">
			<br>
				<c:if test="${currentPage*15>=count }">
					(${(currentPage-1)*15+1 } &nbsp;-&nbsp;${count }&nbsp;/&nbsp;${count })
				</c:if>
				<c:if test="${currentPage*15<count }">
					(${(currentPage-1)*15+1 }&nbsp;-&nbsp;${currentPage*15 }&nbsp;/&nbsp;${count })
				</c:if>
			<br>
		</c:if>
		<div class="table-responsive">
			<table class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					 <th>#</th>
	                 <th>学号</th>
	                 <th>姓名</th>
	                 <th>性别</th>
                     <th>班级</th>
                     <th>操作</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${students }" var="i" varStatus="s">
						<tr>
							<td>${s.count + (currentPage-1)*15 }</td>
							<td>${i.studentId }</td>
							<td>${i.name }</td>
							<td>${i.sex }</td>
							<td>${i.classes.name }</td>
							<td><a class="btn btn-primary btn-wide mybtn" href="${i.id }">密码重置</a>
							<a class="btn btn-primary btn-wide delbtn" href="${i.id }">删除</a></td>
						</tr>
				</c:forEach>
				</tbody>
		</table>
		</div>
		<c:if test="${students.size()!=0 }">
			<nav>
			  <ul class="pagination pagination-lg">
			    <li id="previous">
			      <a href="admin/project/studentmanagement/students/${currentPage-1 }" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <c:forEach begin="1" end="${countPage }" var="c">
			    	<c:if test="${c==currentPage }">
			    		<li class="active"><a href="admin/project/studentmanagement/students/${c }">${c }</a></li>
			    	</c:if>
			    	<c:if test="${c!=currentPage }">
			    		<li><a href="admin/project/studentmanagement/students/${c }">${c }</a></li>
			    	</c:if>
			    </c:forEach>
			    <li id="next">
			      <a href="admin/project/studentmanagement/students/${currentPage+1 }" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
		</nav>
		</c:if>
		
    </jsp:body>
</myTemplate:template>