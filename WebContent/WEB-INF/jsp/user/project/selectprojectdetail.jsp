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
			$("#mybtn").click(function(){
	            var str = new Array();
	            var i = 0;
				$(".myradio").each(function(){
					if ($(this).prop('checked')) {
						str[i++] = $(this).val();
					}
				})
				$.post('project/confirmselectproject',{
					'studentId':str.toString()
				},function(){
					location.href = "project/selecttitles/" + ${user.id};
				})
			})
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li><a href="project/projectmanagement">毕设管理</a></li>
  <li class="active">选题信息</li>
</ol>
	<c:if test="${titles.size()>0 }">
		<form class="form-horizontal" action="project/confirmselectproject" method="POST">
			<c:forEach items="${titles }" var="t">
					<div class="form-group">
						<div class="col-sm-12 col-md-10">${t.name }</div>
					</div>
					<c:forEach items="${t.selectedTitleDetails }" var="st">
						<div class="form-group">
							<div class="col-sm-2 col-md-1 control-label"></div>
							<label class="radio col-md-1 ">
						         <input type="radio" data-toggle="radio" class="myradio" name="radio${t.id} " id="optionsRadios1" value="${st.student.user.id }" data-radiocheck-toggle="radio" required>
						          ${st.student.user.name }
						    </label>
						</div>
					</c:forEach>
			</c:forEach>
			<div class="form-group">
				<div class="col-sm-10 col-md-11 ">
					<p class="text-danger">说明：确认后无法修改，请慎重考虑。</p>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-1">
					<button type="button" class="btn btn-primary btn-wide" id="mybtn">提交</button>
				</div>
			</div>
		</form>
	</c:if>
	
    </jsp:body>
</myTemplate:template>