<%@tag pageEncoding="UTF-8" description="My Template"  language="java" %>
<%@attribute name="header" fragment="true"%>
<%@attribute name="main" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<%@tag import="com.se.working.entity.UserAuthority"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/Mytld"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<c:url value="/"  var="basepath" />
<base href="${basepath }">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<!-- Flat-ui -->
<link href="resources/css/flat-ui.min.css" rel="stylesheet">
<!-- Default CSS-->
<link href="resources/css/default.css" rel="stylesheet">
<link href="resources/images/favicon.ico" rel="shortcut icon"
	type="image/x-icon">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="resources/js/html5shiv.min.js"></script>
      <script src="resources/js/respond.min.js"></script>
    <![endif]-->
<!-- Private -->
<jsp:invoke fragment="header" />
<title>专业工作管理平台</title>
</head>
<body>
	<!-- 导航 -->
	<nav class="navbar navbar-inverse">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="">专业工作管理平台</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <strong>${sessionScope.user.name },
							老师</strong> <span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="updateusersetting">个人设置</a></li>
						<li class="divider"></li>
						<li><a href="logout">退出</a></li>
					</ul></li>
				<!-- <li><a href="#">Settings</a></li>
					<li><a href="#">Profile</a></li>
					<li><a href="#">Help</a></li> -->
			</ul>
			<!-- <form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
				</form> -->
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<!-- 左导航 -->
			<div class="col-sm-3 col-md-2 sidebar">
				<!-- <ul class="nav nav-sidebar">
					<li class="active"><a href="#"> 任务信息 <span class="sr-only">(current)</span>
					</a></li>
					<li><a href="task/listmytask/undone">我的任务</a></li>
					<li><a href="task/list/started">任务信息</a></li>
				</ul> -->
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#"> 监考信息 <span class="sr-only">(current)</span>
					</a></li>
					<li><a href="invi/listmyinviinfo/assinvi">我的监考</a></li>
					<li><a href="invi/listinviinfo/unassinvi">监考信息</a></li>
				</ul>
				<my:authorize access="${set={UserAuthority.ADMIN, UserAuthority.SUPERADMIN} }">
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#"> 工作管理 <span
								class="sr-only">(current)</span>
						</a></li>
						<li><a href="admin/invi/invimanagement">监考管理</a></li>
						<!-- <li><a href="admin/task/taskmanagement">任务管理</a></li> -->
						<li><a href="admin/setting/usermanagement">用户管理</a></li>
					</ul>
				</my:authorize>
				<my:authorize access="${set={UserAuthority.SUPERADMIN} }">
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#"> 系统管理 <span
								class="sr-only">(current)</span>
						</a></li>
						<li><a href="superadmin/sysmanagement">系统设置</a></li>
						<li><a href="">维护日志</a></li>
						<li><a href="">系统初始化</a></li>
					</ul>
				</my:authorize>
			</div>
			<!-- 主界面 -->
			<div class="col-sm-9 col-md-10 main">
				<jsp:doBody></jsp:doBody>
			</div>
		</div>
	</div>
	<!-- 全局异常显示  -->
	<c:if test="${exception != null}">
		<div class="modal fade" id="exception" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h6 class="modal-title" id="myModalLabel">异常</h6>
					</div>
					<div class="modal-body">
						<div class="text-danger">
							<strong>${exception }</strong>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</c:if>
	<!-- footer -->
	<footer class="footer">
		<div class="container">
			<p class="text-muted">东北林业大学 软件工程专业. &copy; 2016</p>
		</div>
	</footer>

	<!-- Placed at the end of the document so the pages load faster -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/flat-ui.min.js"></script>
	<script src="resources/js/application.js"></script>
	<script>
		$(document).ready(function() {
			/* 全局异常显示  */
			if ('${exception}'.length > 0) {
				$('#exception').modal('show'); 
			}
		})
	</script>
	<!--Private JS  -->
	<jsp:invoke fragment="footer" />
</body>
</html>