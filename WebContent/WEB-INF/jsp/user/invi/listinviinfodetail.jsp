<%@page import="com.se.working.invigilation.entity.InvigilationStatusType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<mybase:base>
	<jsp:attribute name="footer"></jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
	  <li><a href="">主页</a></li>
	  <li><a href="invi/listinviinfo/unassinvi">监考信息</a></li>
	  <li class="active">监考详细信息</li>
	</ol>
	监考信息<br>
	<table class="table table-striped table-condensed table-hover">
			<tbody>
				<tr>
					<td>时间</td>
					<td>${week }周
						<fmt:formatDate pattern="MM-dd E" value="${info.startTime.getTime() }"/>
						<br>
						<fmt:formatDate pattern="HH:mm" value="${info.startTime.getTime() }"/>
							- <fmt:formatDate pattern="HH:mm" value="${info.endTime.getTime() }"/></td>
					<td>地点</td>
					<td>${info.location }</td>
				</tr>
				<tr>
					<td>课程/备注</td>
					<td>${info.comment }</td>
					<td>人数</td>
					<td>${info.requiredNumber }</td>
				</tr>
				<tr>
					<td>状态</td>
					<td>${info.currentStatusType.name }</td>
				</tr>
			</tbody>
	</table>
	<br>
	监考分配信息
	<br>
	<table class="table table-striped table-condensed table-hover">
			<tbody>
			<tr>
			<td>导入时间</td>
			<td><fmt:formatDate pattern="MM-dd HH:mm" value="${info.insertTime}"/></td>
				<c:forEach items="${info.invStatusDetail }" var="i">
				 	<td>
				 	<c:if test="${i.invStatus.id == InvigilationStatusType.ASSIGNED}">
				 		分配时间
				 	</c:if>
				 	<c:if test="${i.invStatus.id == InvigilationStatusType.DONE}">
				 		完成时间
				 	</c:if>
				 	</td>
					<td><fmt:formatDate pattern="MM-dd HH:mm" value="${i.assignTime }"/></td>
				</c:forEach>
			</tr>
			</tbody>
	</table>
<br>
	监考通知信息
	<br>
	<table class="table table-striped table-condensed table-hover">
			<tbody>
				<c:forEach items="${info.invigilations }" var="i">
				<tr>
				 	<td>${i.teacher.user.name }</td>
					<c:forEach items="${i.messageDetails }" var="m">
						<td>${m.type.name }</td>
						<td><fmt:formatDate pattern="MM-dd HH:mm" value="${m.insertTime }"/></td>
					</c:forEach>
				</tr>
				</c:forEach>
			
			</tbody>
	</table>

    </jsp:body>
</mybase:base>