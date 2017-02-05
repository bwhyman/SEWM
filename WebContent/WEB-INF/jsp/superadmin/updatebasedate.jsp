<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
            $(function () {
                $('#date').datetimepicker({
                	// 打开即默认输入当前日期
                	useCurrent: false,
                	// 不可用星期
                	daysOfWeekDisabled: [0, 2, 3, 4, 5, 6],
                	sideBySide: true,
                	format: 'YYYY-MM-DD',
                	showClear: true,
                	minDate: 0,
                	toolbarPlacement: 'top',
                	// 与input readonly搭配，禁止手动输入
                	ignoreReadonly: false,
                });
            });
        </script>
	</jsp:attribute>
	<jsp:body>
	
	<form class="form-horizontal" action="superadmin/updatebasedate" method="POST">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">学期基点日期</label>
						<div class="col-sm-10 col-md-4">
							<div class='input-group date' id="date">
						<input type='text' class="form-control" name="date" value="${basedate }" required />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
						</div>
					</div>
						
					<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label"></div>
						<div class="col-sm-10 col-md-4">
							<button type="submit" class="btn btn-primary btn-wide">提交</button>
							<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>	
						</div>
					</div>
					<div class="form-group">
			<div class="col-sm-2col-md-12">
				<p class="text-danger">说明: 学期基点日期，为每学期第一周的周一，所有授课日期、监考日期均基于此日期计算。
			</div>
		</div>
				</form>	
    </jsp:body>
</mybase:base>