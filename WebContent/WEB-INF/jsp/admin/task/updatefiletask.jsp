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
                	// minDate: moment(),
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

	<form class="form-horizontal" action="admin/task/updatefiletask" enctype="multipart/form-data" method="POST">
			<input type="hidden" value="${task.id }" name="id">
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">截止时间</label>
			<div class="col-sm-10 col-md-3">
				<div class='input-group date' id="date">
					<input type='text' class="form-control" name="datetime" required
					 value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${task.endTime.getTime() }"/>" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">任务名称</label>
			<div class="col-sm-10 col-md-3">
				${task.name }
			</div>
			
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 col-md-1 control-label">文件格式</label>
				<div class="col-sm-10 col-md-3">
					<c:forEach items="${filetypes }" var="f" varStatus="s">
						<input type="hidden" id="type_${f.id }" value="${f.type }">
					
						<label class=" radio" for="${f.id }">
							<input type="radio" name="filetypeid" value="${f.id }" id="${f.id }" data-toggle="radio"
								<c:if test="${task.fileType.id == f.id }">checked</c:if>>
							${f.name }
						</label>
					</c:forEach>
				</div>
				
			</div>
		
		<div class="form-group">
			<label for="title" class="col-sm-2 col-md-1 control-label">分值</label>
			<div class="col-sm-10 col-md-3">
				<select data-toggle="select" class="select select-primary mrs mbm" name="point">
					<option value="1" <c:if test="${task.point == 1 }">selected</c:if>>1			
					<option value="2" <c:if test="${task.point == 2 }">selected</c:if>>2
					<option value="3" <c:if test="${task.point == 3 }">selected</c:if>>3
					<option value="4" <c:if test="${task.point == 4 }">selected</c:if>>4
					<option value="5" <c:if test="${task.point == 5 }">selected</c:if>>5
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">人员</label>
			<div class="col-sm-10 col-md-3">
				<select data-toggle="select" multiple="multiple" class="form-control multiselect multiselect-info" name="teachers"
					required>
					<c:forEach items="${users }" var="a">
						<option value="${a.id }"  
						<c:forEach items="${teachers }" var="t">
							<c:if test="${t.id == a.id }"> selected</c:if>
						</c:forEach>>${a.name }
					</c:forEach>
				</select>
			</div>
		
		</div>

		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">说明</label>
			<div class="col-sm-10 col-md-3">
				<textarea class="form-control" rows="5" placeholder="说明" name="comment">${task.comment }</textarea>
			</div>
			
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">文件模板</label>
			<div class="col-sm-10 col-md-5">
				<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="0" accept="">
			</div>
			</div>
			
			<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">我的任务</label>
			<div class="col-sm-10 col-md-3">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
							 value="true" name="mytask" />
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 col-md-1 control-label">通知</label>
			<div class="col-sm-10 col-md-3">
				<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"
							 value="true" name="notice" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-md-1 control-label"><p class="text-danger">说明</p></div>
			<div class="col-sm-10 col-md-11">
				<p class="text-danger">
				更新仅修改任务信息，不会更新教师任务状态，如，不会删除教师更新前上传的任务文件<br>
				如有重大更新，或需教师基于模板文件重新修改上传任务，建议删除任务并重新创建<br>
				我的任务：将新任务模板文件同时作为创建者任务文件<br>
				通知：发送更新短信通知，通知使用创建相同的短信模板<br>
				</p>
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
	
	<div class="row">
		<div class="col-sm-2 col-md-1 control-label"></div>
		<div class="col-sm-10 col-md-3">
			<button type="button" class="btn btn-danger btn-wide" data-toggle="modal" data-target="#myModal">删除任务</button>	
		</div>
	</div>
	<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">删除任务</h4>
      </div>
      <div class="modal-body">
        删除本任务，同时删除相关任务文件，不可恢复。
      </div>
      <div class="modal-footer"> 
	      <form action="admin/task/delfiletask" method="POST">
	      	<input type="hidden" value="${task.id }" name="id">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="submit" class="btn btn-primary">提交</button>
	       </form>
      </div>
    </div>
  </div>
</div>
    </jsp:body>
</mybase:base>