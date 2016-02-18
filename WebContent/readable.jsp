<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
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
<link href="resources/css/docs.css" rel="stylesheet">
<!-- Default CSS-->
<!-- <link href="resources/css/default.css" rel="stylesheet"> -->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<title>工作管理</title>
</head>
<body>
 
          <form action="#">
            <div class="row">
              <div class="col-md-6">
                <label class="checkbox" for="checkbox1">
                  <input type="checkbox" value="" id="checkbox1" data-toggle="checkbox">
                  Checkbox
                </label>
                <label class="checkbox" for="checkbox2">
                  <input type="checkbox" value="" id="checkbox2" checked="checked" data-toggle="checkbox">
                  Checkbox
                </label>
                <label class="checkbox" for="checkbox3">
                  <input type="checkbox" value="" id="checkbox3" data-toggle="checkbox" disabled="">
                  Checkbox
                </label>
                <label class="checkbox" for="checkbox4">
                  <input type="checkbox" value="" id="checkbox4" checked="checked" data-toggle="checkbox" disabled="">
                  Checkbox
                </label>
              </div>
            </div>
          </form>
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/flat-ui.min.js"></script>
	
	<script src="resources/js/application.js"></script>
	<script>
	$(':checkbox').radiocheck();
	</script>
</body>
</html>