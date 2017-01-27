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
  <li class="active">我的任务</li>
</ol>

	<a id="undone" class="btn btn-primary" href="task/listmytask/undone" role="button">未完成</a>
	<a id="done" class="btn btn-primary" href="task/listmytask/done" role="button">已完成</a>
	
	
	<c:if test="${user.userAuthority.level>=15 }"></c:if>
	<p class="text-danger">说明: 
	我的任务：所有<span class="label label-success checkboxspan">未关闭</span>
	状态任务下的，未完成、已完成任务，即，当前仍可提交、修改的任务<br>
	任务状态：开启，正常完成任务；已过期：已超过截止时间，但仍可提交，提交后按任务分值扣分；
	已关闭：任务正式结束，仍可查看任务详细信息<br>
	
	</p>
		<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>截止时间</th>     
                  <th>任务</th>
                  <th>任务状态</th>
                  <th>完成状态</th> 
                  <th>完成时间</th>
                  <th>更多</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${details }" var="d" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${d.fileTask.endTime.getTime() }"/></td>
				<td>${d.fileTask.name }</td>
								<td>
					<c:if test="${d.fileTask.currentStatus.id == 3 }">
						<span class="label label-danger checkboxspan">
						
				</c:if>
				<c:if test="${d.fileTask.currentStatus.id == 2 }">
						<span class="label label-warning checkboxspan">
				</c:if>
				<c:if test="${d.fileTask.currentStatus.id == 1 }">
						<span class="label label-success checkboxspan">
				</c:if>
				${d.fileTask.currentStatus.name }</span>
				</td>
				<td>
					<c:if test="${type == 'undone' }">
						<span class="label label-danger checkboxspan">未完成</span>
					</c:if>	
				<c:if test="${type == 'done' }">
						<span class="label label-success checkboxspan">已完成</span>
					</c:if>
				</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${d.completeTime.getTime() }"/></td>

				<td>	
					<a class="btn btn-primary" href="task/filetaskdetail/${d.fileTask.id }" role="button">详细</a>
				</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</mybase:base>