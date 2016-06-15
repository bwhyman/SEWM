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
	<jsp:attribute name="header">
		<link href="resources/css/fileinput.min.css"  rel="stylesheet">
	</jsp:attribute>

	<jsp:attribute name="footer">
	<script>
		$(function() {
			$('#file-1').fileinput({
				showPreview : false,
				showUpload: false,
		        browseClass: "btn btn-primary",
				initialCaption: "上传论证报告",
			});
			$('#title').blur(function(){
				$.ajax({
					url:"project/checkProjectTitle",
					data: {name: $(this).val()},
		    	    type: "POST",
		    	    dataType: "text",
		    	    success:function(data){
		    	    	if(data == 'false'){
		    	    		$('#myalert').show();
		    	    		$('#title').focus();
		    	    	}else if (data == 'true') {
		    	    		$('#myalert').hide();
						}
		    	    	
		    	    }
				})
			})
			
			$('.deltitle').click(function(){
				var current = $(this);
				$.post('project/deltitle',{
					'id':current.attr('href')
				},function(){
					location.href = 'project/addtitle';
				})
				return false;
			})
		})
	</script>
	<script src="resources/js/fileinput.min.js"></script>
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="project/projectmanagement/titleinfo">题目信息</a></li>
  <li class="active">添加题目信息</li>
</ol>
	<c:if test="${exception != null}">
		&nbsp;&nbsp;
		<div class="alert alert-danger alert-dismissable" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<strong>错误！</strong> ${exception }
		</div>
	</c:if>
	<c:if test="${ fileDetails.size()>0 }">
		
		<div class="table-responsive">
			<table class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					 <th>#</th>
	                  <th>题目</th>
	                  <th>题目性质</th>
	                  <th>指导教师</th>
	                  <th>论证报告</th>
	                  <th>操作</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${fileDetails }" var="p" varStatus="s">
							<tr>
								<td>${s.count }</td>
								<td><a href="project/title/${p.title.id }">${p.title.name }</a></td>
								<td>${p.title.property }</td>
								<td>${p.title.teacher.user.name }</td>
								<td>
									<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
								</td>
								<td>
									<a href="project/updatetitle/${p.title.id}">修改</a>
									<a href="${p.title.id }" class="deltitle">删除</a>
								</td>
							</tr>
				</c:forEach>
				</tbody>
		</table>
		</div>
	</c:if>
	
	<form class="form-horizontal" action="project/addtitle" method="POST" enctype="multipart/form-data">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目</label>
						<div class="col-sm-10 col-md-5">
							<input type="text" class="form-control" placeholder="题目" required name="name" id="title">
						</div>
						<div class="col-sm-10 col-md-5" hidden id="myalert">
							<div class="alert alert-danger alert-dismissable" role="alert">
							  			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
												<span aria-hidden="true">&times;</span>
										</button><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							  <strong>该题目已存在！</strong> 
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="major" class="col-sm-2 col-md-1 control-label">专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业</label>
						<div class="col-sm-10 col-md-5">
							<input type="text" class="form-control" placeholder="专业" required name="major" value="软件工程">
						</div>
					</div>
					<div class="form-group">
						<label for="property" class="col-sm-2 col-md-1 control-label">题目性质</label>
						<div class="col-sm-10 col-md-5">
							<input type="text" class="form-control" placeholder="题目性质" name="property" value="软件设计">
						</div>
					</div>
					<div class="form-group">
						<label for="objective" class="col-sm-2 col-md-1 control-label">题目内容</label>
						<div class="col-sm-10 col-md-8">
							<textarea class="form-control" rows="15" placeholder="题目内容" name="objective" id="editor_id"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="objective" class="col-sm-2 col-md-1 control-label">论证报告</label>
						<div class="col-sm-10 col-md-8">
							<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="1" accept=".doc,.docx">
						</div>
					</div>
						
					<div class="form-group">
						<div class="col-sm-2 col-md-1 control-label"></div>
						<div class="col-sm-10 col-md-2">
							<button type="submit" class="btn btn-primary btn-wide">提交</button>
						</div>
						<div class="col-sm-10 col-md-2">
							<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>	
						</div>
					</div>
				</form>	
	<script charset="utf-8" src="resources/editor/kindeditor-all-min.js"></script>
	<script charset="utf-8" src="resources/editor/lang/zh-CN.js"></script>
	<script>

		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('#editor_id', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [ 'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist','|', 
				          'formatblock', 'fontname', 'fontsize', '|',
				          'forecolor', 'hilitecolor', 'bold','italic', 'underline']
			});
		});
	</script>
    </jsp:body>
</myTemplate:template>