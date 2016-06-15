<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>专业工作管理平台</title>
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<!-- Flat-ui -->
<link href="resources/css/flat-ui.min.css" rel="stylesheet">
<link href="resources/css/signin.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="resources/images/favicon.png">
</head>
<body>
	<div class="logindiv">
		<!-- 按钮触发模态框 -->
		<button class="btn btn-lg btn-info btn-block" data-toggle="modal" data-target="#myModal">专业工作管理平台</button>
		<button class="btn btn-lg btn-info btn-block" data-toggle="modal" data-target="#myModalStudent">学生入口</button>
	</div>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h6 class="modal-title" id="myModalLabel">教师入口</h6>
				</div>
				<div class="modal-body">
					<form class="form-signin" action="login" method="post">
						<div class="input-group ">
							<label for="employeeNumber" class="sr-only">员工号</label>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-user"></span>
							</span>
							<input type="text" name="employeeNumber" class="form-control" placeholder="员工号" required>
						</div>

						<div class="input-group">
							<label for="password" class="sr-only">密码</label>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-lock"></span>
							</span>
							<input type="password" name="password" class="form-control" placeholder="密码" required>
						</div>

						<c:if test="${exception != null}">
					&nbsp&nbsp
							<div class="alert alert-danger alert-dismissable" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
								${exception }
							</div>
						</c:if>

						<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
					</form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModalStudent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h6 class="modal-title" id="myModalLabel">学生入口</h6>
				</div>
				<div class="modal-body">
					<form class="form-signin" action="student/studentlogin" method="post">
						<div class="input-group ">
							<label for="studentId" class="sr-only">学号</label>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-user"></span>
							</span>
							<input type="text" name="studentId" class="form-control" placeholder="学号" required>
						</div>

						<div class="input-group">
							<label for="password" class="sr-only">密码</label>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-lock"></span>
							</span>
							<input type="password" name="password" class="form-control" placeholder="密码" required>
						</div>

						<c:if test="${studentexception != null}">
					&nbsp&nbsp
							<div class="alert alert-danger alert-dismissable" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
								${studentexception }
							</div>
						</c:if>

						<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
					</form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/flat-ui.min.js"></script>
	<script src="resources/js/application.js"></script>
	<script>
		$(document).ready(function() {
			if ('${exception}'.length > 0) {
				$('#myModal').modal('show');
			}
			if( '${studentexception}'.length > 0){
				$('#myModalStudent').modal('show');
			}
		})
	</script>
</body>
</html>