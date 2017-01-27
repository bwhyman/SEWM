<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:attribute name="header">
		<!-- datetimepicker -->
	<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	<link href="resources/css/fileinput.min.css"  rel="stylesheet">
	</jsp:attribute>
	<jsp:attribute name="footer">
	<script src="resources/js/moment-with-locales.js"></script>
	<script src="resources/js/bootstrap-datetimepicker.min.js"></script>	
	<script src="resources/js/fileinput.min.js"></script>
	<script>
            $(function () {
            	$('#file-1').fileinput({
        			showPreview : false,
        	        browseClass: "btn btn-primary",
        	        showUpload: false,
        	        showRemove: false,
        			initialCaption: "上传模板文件",
        			maxFileSize: 50000,
        		}); 
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
                
                $('input:radio').click(function() {
                	var file = $("#file-1") ;  
                	var type = 'type_' + $(this).attr('id');
                	file.attr('accept', $('#'+type).attr('value'));
                	file.fileinput('reset');
                });
                
            });
        </script>
        
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/usersetting">任务管理</a></li>
  <li class="active">添加文件任务信息</li>
</ol>

	<form class="form-horizontal" action="admin/task/addfiletask" enctype="multipart/form-data" method="POST">
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">截止时间</label>
			<div class="col-sm-10 col-md-4">
				<div class='input-group date' id="date">
					<input type='text' class="form-control" name="datetime" required />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">任务名称</label>
			<div class="col-sm-10 col-md-4">
				<input type="text" class="form-control" placeholder="任务名称" name="name" required />
			</div>
			<div class="col-sm-10 col-md-4">
				<p class="text-danger">重要说明：任务上传文件夹、上传文件名称等均基于任务名称，因此不可修改</p>
			</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 col-md-2 control-label">文件格式</label>
				<div class="col-sm-10 col-md-4">
					<c:forEach items="${filetypes }" var="f" varStatus="s">
						<input type="hidden" id="type_${f.id }" value="${f.type }">
					
						<label class=" radio" for="${f.id }">
							<input type="radio" name="filetypeid" value="${f.id }" id="${f.id }" data-toggle="radio"
								<c:if test="${s.count == 1 }">checked</c:if>>
							${f.name }
						</label>
					</c:forEach>
				</div>
				
			</div>
		
		<div class="form-group">
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
			<label for="name" class="col-sm-2 col-md-2 control-label">说明</label>
			<div class="col-sm-10 col-md-4">
				<textarea class="form-control" rows="5" placeholder="说明" name="comment"></textarea>
			</div>	
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">单一文件</label>
			<div class="col-sm-10 col-md-4">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
							 value="true" name="singleFile" />
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">文件模板</label>
			<div class="col-sm-10 col-md-5">
				<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="0" accept="">
			</div>
			</div>
			
			<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">我的任务</label>
			<div class="col-sm-10 col-md-4">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
							 value="true" name="mytask" />
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-2 control-label">通知</label>
			<div class="col-sm-10 col-md-4">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
							 value="true" name="notice"   checked="checked"/>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-2 col-md-2 control-label"></div>
			<div class="col-sm-10 col-md-4">
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