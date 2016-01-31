
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
<!-- Private -->
<jsp:invoke fragment="header" />
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<title>工作管理</title>
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
				<a class="navbar-brand" href="">工作管理</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<strong>王波, 老师</strong>
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#">个人设置</a></li>
							<li class="divider"></li>
							<li><a href="aaa">退出</a></li>
						</ul></li>
					<li><a href="#">Settings</a></li>
					<li><a href="#">Profile</a></li>
					<li><a href="#">Help</a></li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
				</form>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<!-- 左导航 -->
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">
							任务信息
							<span class="sr-only">(current)</span>
						</a></li>
					<li><a href="#">任务信息</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">
							监考信息
							<span class="sr-only">(current)</span>
						</a></li>
					<li><a href="">监考信息</a></li>

				</ul>
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">
							工作管理
							<span class="sr-only">(current)</span>
						</a></li>
					<li><a href="">监考</a></li>
					<li><a href="">任务</a></li>
					<li><a href="admin/usermanager">用户</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">
							系统管理
							<span class="sr-only">(current)</span>
						</a></li>
					<li><a href="">通配符</a></li>
					<li><a href="">维护日志</a></li>
				</ul>
			</div>
			<!-- 主界面 -->
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<jsp:doBody></jsp:doBody>
			</div>
		</div>
	</div>

	<footer class="footer">
		<div class="container">
			<p class="text-muted">Place sticky footer content here. &copy; Company 2016</p>
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