<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<mybase:base>
	<jsp:attribute name="footer">
		<script>
			$(document).ready(function() {
				var reqnum = '${inviInfo.requiredNumber }';
				disabledF();
			    $('input[type=checkbox]').click(function() {
			    	disabledF();
			    });
			    function disabledF() {
					$("input[name='checkeds']").attr('disabled', true);
			    	$('#submit').attr('disabled', true);
			        if ($("input[name='checkeds']:checked").length >= reqnum) {
			        	$('#submit').attr('disabled', false);
			            $("input[name='checkeds']:checked").attr('disabled', false);
			        } else {
			        	$('#submit').attr('disabled', true);
			            $("input[name='checkeds']").attr('disabled', false);
			        }
				};
			})
			
			</script>
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="admin/invi/invimanagement">监考管理</a></li>
  <li class="active">监考分配</li>
</ol>
	
		 <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
                  <th>日期</th>
                  <th>时间</th>
                  <th>课程/备注</th>
                  <th>地点</th>
                  <th>人数</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>	
				<tr>
				<td>
						第${week }周 
						<fmt:formatDate pattern="yyyy-MM-dd E" value="${inviInfo.startTime.getTime() }" />
					</td>
				<td>
						<fmt:formatDate pattern="HH:mm" value="${inviInfo.startTime.getTime() }" />
					- <fmt:formatDate pattern="HH:mm" value="${inviInfo.endTime.getTime() }" />
					</td>
				<td>${inviInfo.comment }</td>
				<td>${inviInfo.location }</td>
				<td><span class="badge bg-primary checkboxspan">${inviInfo.requiredNumber }</span></td>
				<td>
						<a class="btn btn-primary" href="admin/invi/updateinviinfo/${inviInfo.id }" role="button">编辑</a>  	
			
				</tr>
			</tbody>
	</table>
	 </div>    
	     <form class="form-horizontal " action="admin/invi/assigninvi" method="POST">
					<input type="hidden" value="${inviInfo.id }" name="inviInfoId">
					
					<!------------------------ 如果是已分配监考信息，推荐原监考教师 ---------------------------------->
					
					<c:if test="${olders != null}">
					<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label">
						<span class="label label-success">推荐</span>
						</div>
							<div class="col-sm-10 col-md-10">
							<!-- 原分配 -->
							<c:forEach items="${olders }" var="t" varStatus="s">
								<label class="checkbox">
								<input type="checkbox" name="checkeds" value="${t.key }" data-toggle="checkbox" >
								<span class="label label-info checkboxspan">
								 ${t.value }
								</span></label>
								<!-- 剩余未分配人数 -->
								<c:set var="rest" value="${inviInfo.requiredNumber - s.count }"></c:set>
							</c:forEach>
							<!-- 新推荐 -->
							<c:forEach items="${rcds }" var="t" varStatus="s">
								<label class="checkbox">
								<input type="checkbox" name="checkeds" value="${t.key }" data-toggle="checkbox">
								<span class="label label-success checkboxspan">
								 ${t.value }
								</span></label>
							</c:forEach>
					</div>
					</div>
					</c:if>
					<!------------------------ 未分配 ---------------------------------->
					<c:if test="${olders == null}">
						<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label">
						<span class="label label-success">推荐</span>
						</div>
							<div class="col-sm-10 col-md-10">
							<c:forEach items="${rcds }" var="t" varStatus="s">
								<label class="checkbox">
								<input type="checkbox" name="checkeds" value="${t.key }" data-toggle="checkbox">
								<span class="label label-success checkboxspan">
								 ${t.value }
								</span></label>
								
							</c:forEach>
					</div>
					</div>
					
					</c:if>
					
					
					<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label">
						<span class="label label-warning">推荐关闭</span>
						</div>
							<div class="col-sm-10 col-md-10">
							<c:forEach items="${urcds }" var="t" varStatus="s">
								<label class="checkbox">
								<input type="checkbox" name="checkeds" value="${t.key }" data-toggle="checkbox">
								<span class="label label-warning checkboxspan">
								 ${t.value }
								</span></label>
								
							</c:forEach>
					</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label">
						<span class="label label-danger">冲突</span>
						</div>
							<div class="col-sm-10 col-md-10">
							<c:forEach items="${conflicts }" var="c" varStatus="s">
								<label class="checkbox">
								<input type="checkbox" name="checkeds" value="${c.key }" data-toggle="checkbox">
								<span class="label label-danger checkboxspan">
									${c.value }
								</span></label>
								
							</c:forEach>
					</div>
			</div>
			
			<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label">
						<span class="label label-default">关闭</span>
						</div>
							<div class="col-sm-10 col-md-10">
							<c:forEach items="${disusers }" var="c" varStatus="s">
								<label class="checkbox"><span class="label label-default checkboxspan">
									${c.name }
								</span></label>
								
							</c:forEach>
					</div>
			</div>
		
		<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label"></div>
						<div class="col-sm-10 col-md-4">
							<button type="submit" class="btn btn-primary btn-wide" id="submit">提交</button>
							<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>	
						</div>
					</div>
				<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label"><p class="text-danger">说明</p></div>
						<div class="col-sm-10 col-md-11">
							<p class="text-danger">
							颜色：使用不同颜色区分不同信息，一目了然：
							<span class="label label-info checkboxspan">原分配</span>
							<span class="label label-success checkboxspan">推荐分配</span>
							<span class="label label-warning checkboxspan">推荐功能关闭</span>
							<span class="label label-danger checkboxspan">监考时间冲突</span>
							<span class="label label-default  checkboxspan">关闭</span><br>
							冲突规则：监考时间与授课时间冲突，监考时间与其他监考时间冲突；冲突仍为可选状态，用于人工判断；<br>
							推荐关闭：推荐监考功能关闭教师，但仍可安排监考。当时间冲突时，为防止误选，将自动添加至冲突列表；<br>
							推荐规则：监考时间与授课时间无冲突，与其他监考时间无冲突，开启推荐，开启通知，按目前分配监考次数正序，ID倒序；<br>
							信息顺序：教师姓名；监考当日全部课程、授课地点、授课时间；当日全部监考时间、监考地点；已分配监考次数；<br>
							列出详细信息主要用于人工判断；例如，监考时间为910节，系统自动推荐某教师监考，
							但该教师12345678均有课，即可酌情分配其他教师完成该监考。<br>
							监考必须一次分配完成，不支持保存下次再次分配。
							如，需2人，仅分配1人保存，但又将监考人数修改为3人，逻辑过于混乱<br>
						
							</p>
						</div>
					</div>
						
					
					
				</form>	
			
    </jsp:body>
</mybase:base>