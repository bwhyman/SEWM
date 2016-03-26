<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<myTemplate:template>
<jsp:attribute name="footer">
	<script>
		$(function(){
			if ('${projectFileDetail.title.selectedTitleDetails.size() }' > 0) {
				$('#myform').show();
			}
			/* $('.mycheck').on({'switchChange.bootstrapSwitch': function(event, state) {
			            // 按钮状态发生改变
			        var num = 0;
			        $('.mycheck').each(function(index,data){
			        	alert($(index).css('checked'))
			        })
			    }
			}) */
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="project/projectmanagement">毕设管理</a></li>
  <li class="active">选题信息</li>
</ol>

	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
                  <th>题目</th>
                  <th>题目性质</th>
                  <th>论证报告</th>
                  <th>已选人数</th>
			</tr>
			</thead>
			<tbody>
				<tr>
				<td>${projectFileDetail.title.name }</td>
				<td>${projectFileDetail.title.property }</td>
				<td>${projectFileDetail.title.selectedTitleDetails.size() }</td>
				<td>
					<a href="download/${projectFileDetail.directory }/${projectFileDetail.fileName}/">论证报告</a>
				</td>
			</tr>
			</tbody>
	</table>
	</div>
	<p>选题学生：</p>
	<form class="form-horizontal" action="project/confirmselectproject" method="POST" id="myform" hidden>
		<c:forEach items="${projectFileDetail.title.selectedTitleDetails }" var="p" varStatus="s">
			<div class="form-group">
				<div class="col-sm-2 col-md-1 control-label"></div>
				 <label class="radio col-md-1 ">
	                <input type="radio" data-toggle="radio" name="studentId" id="optionsRadios1" value="${p.student.user.id }" data-radiocheck-toggle="radio" required>
	                ${p.student.user.name }
	             </label>
			</div>
		</c:forEach>
		<input type="hidden" name="detailid" value="${projectFileDetail.id }">
		<div class="form-group">
						<div class="col-sm-2 col-md-1 control-label"><p class="text-danger">说明</p></div>
						<div class="col-sm-10 col-md-11 ">
							<p class="text-danger">
							确认后无法修改，请慎重考虑。
							</p>
						</div>
					</div>
		<div class="form-group">
	<div class="col-md-1">
			<button type="submit" class="btn btn-primary btn-wide">提交</button>
		</div>
	</div>
	</form>
    </jsp:body>
</myTemplate:template>