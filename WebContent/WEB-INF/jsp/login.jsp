<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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

<!--[if lte IE 8]>
<script>
alert('检测到您使用的是IE8或以下版本浏览器，将无法正常使用本系统功能'); 
</script>
<div class="ie_alert">
    <p>检测到您使用的是IE8或以下版本浏览器，将无法正常使用本系统功能</p>
    <p>建议使用Google Chrome最新版本浏览器访问本系统
    <a href="http://rj.baidu.com/soft/lists/3" target="_blank">最新浏览器下载列表</a></p>
</div>
<![endif]-->

	<div class="logindiv">
		<!-- 按钮触发模态框 -->
		<button class="btn btn-lg btn-info btn-block" data-toggle="modal" data-target="#myModal">专业工作管理平台</button>
	</div>


	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h6 class="modal-title" id="myModalLabel">请登录</h6>
				</div>
				<div class="modal-body">
				
				<div id="ilogin" hidden="">
					<form class="form-signin" action="ilogin" method="post">
						<button class="btn btn-lg btn-primary btn-block" type="submit">${name }老师，登录</button>
						<!-- <a id="relogin">重新登录</a> -->
						<label class="checkbox" for="relogin">
								<input type="checkbox" id="relogin" name="checked" data-toggle="checkbox" />
								<span class="text-info"><strong>重新登录</strong></span>
							</label>
					</form>
				</div>
				
				<div id="a" hidden="" class="text-info">text-info</div>
				
				<div id="login" hidden="">
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
						
						<div class="input-group">
							<label for="password" class="sr-only"></label>
							<label class="checkbox" for="checkbox1">
								<input type="checkbox" id="checkbox1" name="checked" value="1" data-toggle="checkbox" />
								<span class="text-info"><strong>记住密码</strong></span>
							</label>
						</div>
						<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
					</form>
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
			/* 登录异常  */
			if ('${exception}'.length > 0) {
				$('#myModal').modal('show');
			}
			/* cookie是否存在 */
			if('${remember}'.length > 0) {
				$('#ilogin').show();
			} else {
				$('#login').show();
			}
			
			/* 重新登录 */
			$('#relogin').click(function() {
				$('#login').slideToggle('slow');
				$('#ilogin').slideUp();
			})
		})
	</script>
</body>
</html>