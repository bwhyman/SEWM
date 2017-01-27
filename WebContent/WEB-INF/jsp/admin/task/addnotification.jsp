<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<mybase:base>
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

	<form class="form-horizontal" action="admin/task/addnotification" enctype="multipart/form-data" method="POST">
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">截止时间</label>
			<div class="col-sm-10 col-md-3">
				<div class='input-group date' id="date">
					<input type='text' class="form-control" name="datetime" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">通知内容</label>
			<div class="col-sm-10 col-md-3">
				<textarea class="form-control" rows="5" placeholder="通知内容" name="comment" required></textarea>
			</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 col-md-1 control-label">高级</label>
			<div class="col-sm-10 col-md-3">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
							 value="true" name="advanced" id="advanced" />
			</div>
			</div>
		
		<div class="form-group" hidden id="pointdiv">
			<label for="title" class="col-sm-2 col-md-1 control-label">分值</label>
			<div class="col-sm-10 col-md-3">
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
			<label for="name" class="col-sm-2 col-md-1 control-label">教师</label>
			<div class="col-sm-10 col-md-3">
				<select data-toggle="select" multiple="multiple" class="form-control multiselect multiselect-info" name="teachers"
					required>
					<c:forEach items="${users }" var="t">
						<option value="${t.id }" selected>${t.name }
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label"></div>
			<div class="col-sm-10 col-md-3">
				<button type="submit" class="btn btn-primary btn-wide">提交</button>
				<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>
			</div>
		</div>
	</form>
	<p class="text-danger">说明：
				截止时间：默认禁止创建当前时间之前的任务。
				任务名称：同时用于规范上传文件的命名，
				如任务名称为<span class="label label-success checkboxspan">教学日历</span>，
				则教师上传的该任务文件将以<span class="label label-success checkboxspan">教学日历_王波</span>命名；<br>
				文件格式：用于规范上传文件类型，防止误传，但不会验证文件有效性；<br>
				人员：默认为所有<span class="label label-success checkboxspan">通知开启</span>教师<br>
				备注：任务详细说明<br>
				单一文件：教师在同一文件中修改，通过版本控制完成<br>
				文件模板：<span class="label label-success checkboxspan">非必选</span>，没有可不必上传；
				教师需填写的任务文件，以<span class="label label-success checkboxspan">任务名称_模板</span>命名；<br>
				我的任务：没有模板文件，自动完成本任务；有模板文件，系统自动创建模板文件副本作为任务创建者的任务文件<br>
				文件模板文件同时为本任务创建者的任务文件，系统自动创建模板文件、创建者文件共2个文件，创建者自动完成本任务，
				任务创建者可在任务中重新上传覆盖自己的任务文件，不会影响模板文件<br>
				通知：同时发送短信通知<br>
				</p>
    </jsp:body>
</mybase:base>