<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<mybase:base>
<jsp:attribute name="footer">
	<script>
		$(function() {
			$('#'+'${type}').attr('class','btn btn-danger');
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li class="active">任务信息</li>
</ol>

	<a id="started" class="btn btn-primary" href="task/list/started" role="button">已开启</a>
	<a id="expired" class="btn btn-primary" href="task/list/expired" role="button">已过期</a>
	<a id="closed" class="btn btn-primary" href="task/list/closed" role="button">已关闭</a>
	<a id="all" class="btn btn-primary" href="task/list/all" role="button">全部</a>
	<p class="text-danger">说明: 
	详细：任务详细信息，任务提交操作<br>
	</p>
		<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>截止时间</th>
                  <th>任务</th>
                  <th>状态</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${tasks }" var="t" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${t.endTime.getTime() }"/></td>
				<td>${t.name }</td>
				<td>
					<c:if test="${t.currentStatus.id == 3 }">
						<span class="label label-danger checkboxspan">		
				</c:if>
				<c:if test="${t.currentStatus.id == 2 }">
						<span class="label label-warning checkboxspan">
				</c:if>
				<c:if test="${t.currentStatus.id == 1 }">
						<span class="label label-success checkboxspan">
				</c:if>
				${t.currentStatus.name }</span></td>
				<td><a class="btn btn-primary" href="task/filetaskdetail/${t.id }" role="button">详细</a></td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</mybase:base>