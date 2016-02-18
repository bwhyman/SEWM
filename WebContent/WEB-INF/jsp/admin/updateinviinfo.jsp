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
<link href="resources/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
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
                	format: 'YYYY-MM-DD',
                	showClear: true,
                	toolbarPlacement: 'top',
                	// 与input readonly搭配，禁止手动输入
                	ignoreReadonly: false,
                });
                $('#stime').datetimepicker({
                	// 打开即默认输入当前日期
                	useCurrent: false,
                	sideBySide: true,
                	format: 'HH:mm',
                	showClear: true,
                	toolbarPlacement: 'top',
                	// 与input readonly搭配，禁止手动输入
                	ignoreReadonly: false,
                });
                $('#etime').datetimepicker({
                	// 打开即默认输入当前日期
                	useCurrent: false,
                	sideBySide: true,
                	format: 'HH:mm',
                	showClear: true,
                	toolbarPlacement: 'top',
                	// 与input readonly搭配，禁止手动输入
                	ignoreReadonly: false,
                });
            });
        </script>
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/usersetting">用户管理</a></li>
  <li class="active">添加用户</li>
</ol>
	<form class="form-horizontal" action="admin/updateinviinfo" method="POST">
					<input type="hidden" value="${info.id }" name="id">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">监考日期</label>
						<div class="col-sm-10 col-md-3">
							<div class='input-group date' id="date">
						<input type='text' class="form-control" name="date" 
						value="<fmt:formatDate pattern="yyyy-MM-dd" value="${info.startTime.getTime() }"/>" required />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">开始时间</label>
						<div class="col-sm-10 col-md-3">
							<div class='input-group date' id="stime">
						<input type='text' class="form-control" name="stime" 
						 value="<fmt:formatDate pattern="HH:mm" value="${info.startTime.getTime() }"/>" required />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">结束时间</label>
						<div class="col-sm-10 col-md-3">
							<div class='input-group date' id="etime">
						<input type='text' class="form-control"  name="etime" 
						value="<fmt:formatDate pattern="HH:mm" value="${info.endTime.getTime() }"/>" required />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
						</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">地点</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" placeholder="地点" required 
							value="${info.location }" name="location">
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">人数</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" placeholder="人数" required 
							value="${info.requiredNumber }" name="requiredNumber">
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">监考课程</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" placeholder="监考课程" 
							value="${info.course }" name="course">
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">备注</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" placeholder="备注" 
							value="${info.comment }" name="comment">
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
				<form class="form-horizontal" action="admin/delinviinfo" method="POST">
				<div class="form-group">
						<div class="col-sm-2 col-md-1 control-label"></div>
						<div class="col-sm-10 col-md-3">
							<input type="hidden" value="${info.id }" name="infoinviid">
							<button type="submit" class="btn btn-danger btn-wide">删除监考信息</button>	
						</div>
					</div>
				</form>
				
    </jsp:body>
</myTemplate:template>