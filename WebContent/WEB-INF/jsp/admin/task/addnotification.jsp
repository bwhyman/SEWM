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
		<!-- datetimepicker -->
	<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	</jsp:attribute>
	<jsp:attribute name="footer">
	<script src="resources/js/moment-with-locales.js"></script>
	<script src="resources/js/bootstrap-datetimepicker.min.js"></script>	
	<script>
            $(function () {
                $('#date').datetimepicker({
                	// 打开即默认输入当前日期
                	useCurrent: false,
                	sideBySide: true,
                	format: 'YYYY-MM-DD HH:mm',
                	showClear: true,
                	/* minDate: moment(), */
                	toolbarPlacement: 'top',
                	stepping: 10,
                	// 与input readonly搭配，禁止手动输入
                	ignoreReadonly: false,
                });
                $('#advanced').on('switchChange.bootstrapSwitch',function(event,state) {
                	$('#pointdiv').slideToggle('slow');
                });
            });
        </script>
        
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/usersetting">任务管理</a></li>
  <li class="active">创建通知信息</li>
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
	<form class="form-horizontal" action="admin/task/addnotification" enctype="multipart/form-data" method="POST">
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">截止时间</label>
			<div class="col-sm-10 col-md-4">
				<div class='input-group date' id="date">
					<input type='text' class="form-control" name="datetime" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">通知内容</label>
			<div class="col-sm-10 col-md-4">
				<textarea class="form-control" rows="5" placeholder="通知内容" name="comment" required></textarea>
			</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 col-md-2 control-label">高级</label>
			<div class="col-sm-10 col-md-4">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
							 value="true" name="advanced" id="advanced" />
			</div>
			</div>
		
		<div class="form-group" hidden id="pointdiv">
			<label for="title" class="col-sm-2 col-md-2 control-label">分值</label>
			<div class="col-sm-10 col-md-4">
				<select data-toggle="select" class="select select-primary mrs mbm" name="point">
					<option value="1">1
					<option value="2">2
					<option value="3">3
					<option value="4">4
					<option value="5">5
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">教师</label>
			<div class="col-sm-10 col-md-4">
				<select data-toggle="select" multiple="multiple" class="form-control multiselect multiselect-info" name="teachers"
					required>
					<c:forEach items="${users }" var="t">
						<option value="${t.id }" selected>${t.name }
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label"></div>
			<div class="col-sm-10 col-md-2">
				<button type="submit" class="btn btn-primary btn-wide">提交</button>
			</div>
			<div class="col-sm-10 col-md-2">
				<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>
			</div>
		</div>
	</form>
    </jsp:body>
</myTemplate:template>