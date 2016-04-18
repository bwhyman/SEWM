<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<%@tag description="My Template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="main" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<!-- Flat-ui -->
<link href="resources/css/flat-ui.min.css" rel="stylesheet">
<!-- Default CSS-->
<link href="resources/css/default.css" rel="stylesheet">
<link href="resources/images/favicon.ico" rel="shortcut icon"  type="image/x-icon">

<!-- Private -->
<jsp:invoke fragment="header" />
<!-- Flat-ui -->
<link href="resources/css/flat-ui.min.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="resources/js/html5shiv.min.js"></script>
      <script src="resources/js/respond.min.js"></script>
    <![endif]-->

<title>专业工作管理平台</title>
</head>
<body>
	<!-- 导航 -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
					aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="">专业工作管理平台</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<strong>${user.name }
								<c:if test="${user.userAuthority.level >=10}">, 老师</c:if>
							</strong>
							<span class="caret"></span>
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
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<!-- 左导航 -->
			<c:if test="${user.userAuthority.level >=10 }">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#">
								任务信息
								<span class="sr-only">(current)</span>
							</a></li>
						<li><a href="task/listmytask/undone/1">我的任务</a></li>
						<li><a href="task/list/started/1">任务信息</a></li>
						<li><a href="task/listnotification/started/1">通知信息</a></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#">
								监考信息
								<span class="sr-only">(current)</span>
							</a></li>
							<li><a href="invi/listmyinviinfo/undone/1">我的监考</a></li>
						<li><a href="invi/listinviinfo/unassinvi/1">监考信息</a></li>
					</ul>
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#">
								毕设信息
								<span class="sr-only">(current)</span>
							</a></li>
							<li><a href="project/projectmanagement">毕设信息</a></li>
					</ul>
					<c:if test="${user.userAuthority.level >=15 }">
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#">
								工作管理
								<span class="sr-only">(current)</span>
							</a></li>
						<li><a href="admin/invi/invimanagement">监考管理</a></li>
						<li><a href="admin/task/taskmanagement">任务管理</a></li>
						<li><a href="admin/project/projectmanagement">毕设管理</a></li>
						<li><a href="admin/setting/usersetting">用户管理</a></li>
					</ul>
					</c:if>
					<c:if test="${user.userAuthority.level >=20 }">
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#">
								系统管理
								<span class="sr-only">(current)</span>
							</a></li>
						<li><a href="">通配符</a></li>
						<li><a href="">维护日志</a></li>
						<li><a href="superadmin/initsys">系统初始化</a></li>
					</ul>
					</c:if>
				</div>
			</c:if>
			<c:if test="${user.userAuthority.level == 5 }">
				<div class="col-sm-3 col-md-2 sidebar">
					<ul class="nav nav-sidebar">
						<li class="active"><a href="#">
							毕设信息
							<span class="sr-only">(current)</span>
								</a></li>
						<li><a href="student/project/projectmanagement">毕设信息</a></li>
					</ul>
					
				</div>
			</c:if>
			
			<!-- 主界面 -->
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<jsp:doBody></jsp:doBody>
			</div>
		</div>
	</div>

	<footer class="footer">
		<div class="container">
			<div class="row">
				<div class="col-sm-6 col-md-6 col-sm-offset-4 col-md-offset-4">
					<p class="text-muted">东北林业大学 软件工程专业. &copy; 2016</p>
				</div>
			</div>
			
		</div>
	</footer>

	<!-- Placed at the end of the document so the pages load faster -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/flat-ui.min.js"></script>
	<script src="resources/js/application.js"></script>
	
	<!--Private JS  -->
	<jsp:invoke fragment="footer" />
</body>
</html>