<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">发送监考短信通知</li>
</ol>
	<c:import url="/WEB-INF/jsp/common/navinvilist.jsp"></c:import>
	<br />
     	<form class="form-horizontal" action="admin/invi/sendinvimessage" method="POST">
     	<input type="hidden" value="${inviinfoid }" name="inviinfoid">
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">通知</label>
			<div class="col-sm-10 col-md-5">
				<select data-toggle="select" multiple="multiple" class="form-control multiselect multiselect-info" name="inviids">
					<c:forEach items="${invis }" var="i">
						<option value="${i.id }" selected>${i.teacher.user.name }	
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label"></div>
			<div class="col-sm-10 col-md-3">
				<button type="submit" class="btn btn-primary btn-wide">提交</button>			
			</div>
		</div>
	</form>
	监考通知信息
	<br>
	<table class="table table-striped table-condensed table-hover">
			<tbody>
				<c:forEach items="${invis  }" var="i">
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