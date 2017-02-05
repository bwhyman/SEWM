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
  <li class="active">更新监考信息</li>
</ol>
	<form class="form-horizontal" action="admin/invi/updateinviinfo" method="POST">
					<input type="hidden" value="${info.id }" name="id">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">监考日期</label>
						<div class="col-sm-10 col-md-4">
							<div class='input-group date' id="date">
								<fmt:formatDate pattern="yyyy-MM-dd" value="${info.startTime.getTime() }" var="odate"/>
								<input type='text' class="form-control" name="date" value="${odate }" required />
								<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
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
							<fmt:formatDate pattern="HH:mm" value="${info.startTime.getTime() }" var="ostime"/>
							<input type='text' class="form-control" name="stime" value="${ostime }" required />
							<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
						</span>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">结束时间</label>
						<div class="col-sm-10 col-md-4">
							<div class='input-group date' id="etime">
							<fmt:formatDate pattern="HH:mm" value="${info.endTime.getTime() }" var="oetime"/>
						<input type='text' class="form-control"  name="etime" value="${oetime }" required />
						<span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
						</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">地点</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="地点" required 
							value="${info.location }" name="location">
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">人数</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="人数" required 
							value="${info.requiredNumber }" name="requiredNumber">
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">课程/备注</label>
						<div class="col-sm-10 col-md-4">
							<textarea class="form-control" rows="5" placeholder="课程/备注" name="comment">${info.comment }</textarea>
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
				
				<div class="row">
						<div class="col-sm-2 col-md-2 control-label"></div>
						<div class="col-sm-10 col-md-4">
							<button type="button" class="btn btn-danger btn-wide" data-toggle="modal" data-target="#myModal">删除监考信息</button>
						</div>
					</div>
					<c:if test="${info.requiredNumber > 1}">
						<form class="form-horizontal" action="admin/invi/splitinviinfo" method="POST">
							<input type="hidden" value="${info.id }" name="inviinfoid">
						<div class="form-group">
							<div class="col-sm-2 col-md-2 control-label"></div>
							<div class="col-sm-10 col-md-4">
							<button type="submit" class="btn btn-primary btn-wide">分解监考</button>
							</div>
						</div>	
					</form>
					</c:if>
				<br>
	<p class="text-danger">说明: 
	修改<span class="label label-danger">已分配</span>监考信息时，如监考人数发生变化，
	监考变为<span class="label label-danger">未分配</span>状态。<br>
	原因，原监考分配3人，现调整为2人，系统无法自动确定取消谁的监考安排<br>
	删除，将删除监考信息，以及关联的监考安排
	</p>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">删除监考信息</h4>
      </div>
      <div class="modal-body">
        删除本监考信息，同时删除关联的监考安排，不可恢复。
      </div>
      <div class="modal-footer"> 
	      <form action="admin/invi/delinviinfo" method="POST">
	      	<input type="hidden" value="${info.id }" name="infoinviid">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="submit" class="btn btn-primary">提交</button>
	       </form>
      </div>
    </div>
  </div>
</div>
		
    </jsp:body>
</mybase:base>