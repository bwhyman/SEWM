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
            $(function () {
                $('#date').datetimepicker({
                	// 打开即默认输入当前日期
                	useCurrent: false,
                	sideBySide: true,
                	format: 'YYYY-MM-DD',
                	showClear: true,
                	minDate: 0,
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
                $('input:radio').click(function() {
                	var stime;
                	var etime;
                	switch($(this).attr('id')) {
                	case "section12":
                		stime = "08:00";
                		etime = "10:00";
                	break;
                	case "section34":
                		stime = "10:05";
                		etime = "12:00";
                	break;
                	case "section56":
                		stime = "13:30";
                		etime = "15:30";
                	break;
                	case "section78":
                		stime = "15:35";
                		etime = "17:35";
                	break;
                	case "section910":
                		stime = "18:00";
                		etime = "20:00";
                		break;
                	}
                
                	$('input[name=stime]').val(stime);
                	$('input[name=etime]').val(etime);
                });
            });
        </script>
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">添加监考信息</li>
</ol>
	<form class="form-horizontal" action="admin/invi/addinviinfo" method="POST">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">监考日期</label>
						<div class="col-sm-10 col-md-4">
							<div class='input-group date' id="date">
							<input type='text' class="form-control" name="date" required />
							<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
							</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">监考时间</label>
						<div class="col-sm-10 col-md-4">
						<label class=" radio" for="section12">
							<input type="radio" name="section" id="section12" data-toggle="radio">
							第一、二节
						</label>
						<label class=" radio" for="section34">
							<input type="radio" name="section" id="section34" data-toggle="radio">
							第三、四节
						</label>
						<label class=" radio" for="section56">
							<input type="radio" name="section" id="section56" data-toggle="radio">
							第五、六节
						</label>
						<label class=" radio" for="section78">
							<input type="radio" name="section" id="section78" data-toggle="radio">
							第七、八节
						</label>
						<label class=" radio" for="section910">
							<input type="radio" name="section" id="section910" data-toggle="radio">
							第九、十节
						</label>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">开始时间</label>
						<div class="col-sm-10 col-md-4">
							<div class='input-group date' id="stime">
						<input type='text' class="form-control" name="stime" required />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">结束时间</label>
						<div class="col-sm-10 col-md-4">
							<div class='input-group date' id="etime">
						<input type='text' class="form-control"  name="etime" required />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
						</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">地点</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" list="location" required name="location">
							<datalist id="location">
								<option value="丹青楼">
								<option value="成栋楼">
								<option value="锦绣楼">
								<option value="研究生楼">
							</datalist>
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">人数</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="人数" required name="requiredNumber">
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">课程/备注</label>
						<div class="col-sm-10 col-md-4">
							<textarea class="form-control" rows="5" placeholder="课程/备注" name="comment" maxlength="15"></textarea>
						</div>
						<div class="col-sm-10 col-md-3"><p class="text-danger">限制在15字符</p></div>
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