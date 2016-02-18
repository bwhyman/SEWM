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
  <li><a href="admin/usersetting">用户管理</a></li>
  <li class="active">导入监考信息</li>
</ol>

	<a class="btn btn-primary" href="admin/list/unassinvi" role="button">未分配</a>
	<a class="btn btn-primary" href="admin/list/assinvi" role="button">已分配</a>
	<a class="btn btn-primary" href="admin/list/done" role="button">已完成</a>
	<a class="btn btn-primary" href="admin/list/all" role="button">全部</a>
	<p class="text-danger">说明: 
	编辑，对监考信息进行修改，修改监考时间地点，添加监考课程名称等，提交后自动转到监考分配<br>
	分配，对已分配监考完成重新分配，对未分配监考创建监考分配
	</p>
		    <table class="table table-striped">
		<thead>
			<tr>
				 <th>#</th>
                  <th>日期</th>
                  <th>时间</th>
                  <th>地点</th>
                  <th>人数</th>
                  <th>分配</th>
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
					<c:forEach items="${i.invigilations }" var="t">${t.teacher.user.name }<br></c:forEach>
				</td>
				
				<td>
					<c:if test="${i.currentStatusType.id == 1 }">
						<span class="label label-danger checkboxspan">
						
				</c:if>
				<c:if test="${i.currentStatusType.id == 2 }">
						<span class="label label-success checkboxspan">
				</c:if>
				<c:if test="${i.currentStatusType.id == 3 }">
						<span class="label label-success checkboxspan">
				</c:if>
				<c:if test="${i.currentStatusType.id == 4 }">
						<span class="label label-info checkboxspan">
				</c:if>
				${i.currentStatusType.name }</span></td>
				<td><fmt:formatDate pattern="MM-dd HH:mm" value="${i.insertTime }" /></td>
				<td><a class="btn btn-primary" href="admin/updateinviinfo/${i.id }" role="button">编辑</a>  
						<a class="btn btn-primary"  href="admin/assigninvi/${i.id }" role="button">分配</a></td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
    </jsp:body>
</myTemplate:template>