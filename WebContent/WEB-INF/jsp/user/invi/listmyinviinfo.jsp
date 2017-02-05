<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
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
  <li class="active">监考信息</li>
</ol>
	<div class="row-fluid">
	<div class="pull-right">
		<a class="btn btn-primary" href="invi/downloadinviinfoexcel" role="button">下载监考记录</a>
	</div>
		<a id="assinvi" class="btn btn-primary" href="invi/listmyinviinfo/assinvi" role="button">已分配</a>
		<a id="done" class="btn btn-primary" href="invi/listmyinviinfo/done" role="button">已完成</a>
		<a id="all" class="btn btn-primary" href="invi/listmyinviinfo/all" role="button">全部</a>
	</div>
		 <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>日期</th>
                  <th>时间</th>
                  <th>地点</th>
                  <th>课程/备注</th>
                  <th>人数</th>
                  <th>分配</th>
                  <th>状态</th>
                  <th>导入时间</th>
                  <c:if test="${user.userAuthority.level>=15 }">
                  <th>操作</th>
                  </c:if>
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
				<td>${i.comment }</td>
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
				<c:if test="${sessionScope.user.userAuthority.level>=15 }">
				<td><fmt:formatDate pattern="MM-dd HH:mm" value="${i.insertTime }" /></td>
				<td><a class="btn btn-primary" href="admin/invi/updateinviinfo/${i.id }" role="button">编辑</a>  
						<a class="btn btn-primary"  href="admin/invi/assigninvi/${i.id }" role="button">分配</a></td>
				</c:if>		
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
    </jsp:body>
</mybase:base>