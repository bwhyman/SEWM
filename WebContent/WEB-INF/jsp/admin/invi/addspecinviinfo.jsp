<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
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
		$(function() {
			$('#date').datetimepicker({
				// 打开即默认输入当前日期
				useCurrent : false,
				sideBySide : true,
				format : 'YYYY-MM-DD HH:mm',
				showClear : true,
				toolbarPlacement : 'top',
				// 与input readonly搭配，禁止手动输入
				ignoreReadonly : false,
			});
		});
	</script>
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">添加特殊监考信息</li>
</ol>
	<form class="form-horizontal" action="admin/invi/addspecinviinfo" method="POST">
					<div class="form-group">
						<label for="title" class="col-sm-2 col-md-2 control-label">类型</label>
						<div class="col-sm-10 col-md-4">
						<select data-toggle="select" class="select select-primary mrs mbm" name="typeId">
							<c:forEach items="${types }" var="t">
								<option value="${t.id }">${t.name }
							</c:forEach>
						</select>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">日期</label>
						<div class="col-sm-10 col-md-4">
							<div class='input-group date' id="date">
								<input type='text' class="form-control" name="date" required />
								<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">地点</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="地点" required value="科学会堂" name="location">
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">备注</label>
						<div class="col-sm-10 col-md-4">
							<textarea class="form-control" rows="5" placeholder="备注" name="comment"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="title" class="col-sm-2 col-md-2 control-label">分配</label>
						<div class="col-sm-10 col-md-4">
						<select data-toggle="select" multiple="multiple" class="form-control multiselect multiselect-info" name="checkeds" required>
					<c:forEach items="${teachers }" var="t">
						<option value="${t.id }">${t.user.name }; ${t.sqecQuantity };
						</c:forEach>
				</select>
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
				
    </jsp:body>
</mybase:base>