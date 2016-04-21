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
			var leadNum = parseInt('${leadNum}');
			
			//点击时判断已选人数是否超过系统要求人数
			$('.myradio').click(function(){
				var current = $(this);
				if(current.prop('checked')==true){
					var i = 0;
					$('.myradio').each(function(){
						if($(this).prop('checked')){
							i++;
							if (i>leadNum) {
								current.radiocheck('uncheck'); 
							}
						}
					})
				}
			})

			$("#mybtn").click(function(){
				//创建存储已选学生id的数组，已1,2,3的形式传至服务器进行解析，将选中学生的选题信息确认成功
	            var str = new Array();
	            var i = 0;
				$(".myradio").each(function(){
					if ($(this).prop('checked')) {
						str[i++] = $(this).val();
					}
				})
				if(i==0){
					return false;
				}
				$.post('project/confirmselectproject',{
					'studentId':str.toString()
				},function(){
					location.href = "project/selecttitles/" + ${user.id} + '/1';
				})
			})
			
			var curstudentid = 0;
			var clicktimes = 0;
			//使用clicktimes根据点击次数设置radio的值，因执行radio的点击事件时radio已选中
			$('.myradio').click(function(){
				if(curstudentid != $(this).attr('value')){
					curstudentid = $(this).attr('value');
					clicktimes = 1;
				}
				if(clicktimes%2==0){
					$(this).radiocheck('uncheck');
				}else{
					$(this).radiocheck('check');
				}
				clicktimes++;
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
						<div class="col-sm-12 col-md-10 col-md-offset-1">${t.name }</div>
					</div>
					<c:forEach items="${t.selectedTitleDetails }" var="st">
						<div class="form-group">
							<div class="col-sm-2 col-md-1 control-label"></div>
							<label class="radio col-md-6 col-md-offset-1" style="font-size: 1em;">
						         <input type="radio" data-toggle="radio" class="myradio" name="radio${t.id} " value="${st.student.student.id }" data-radiocheck-toggle="radio" required>
						          ${st.student.student.name }(&nbsp;tel:&nbsp;${st.student.student.phoneNumber }&nbsp;)
						    </label>
						</div>
					</c:forEach>
			</c:forEach>
			<!-- <div class="form-group">
				<div class="col-sm-10 col-md-11 ">
					<p class="text-danger">说明：确认后无法修改，请慎重考虑。</p>
				</div>
			</div> -->
			<div class="form-group">
				<div class="col-md-1 col-md-offset-1">
					<button type="button" class="btn btn-primary btn-wide" id="mybtn">提交</button>
				</div>
			</div>
		</form>
	</c:if>
	
    </jsp:body>
</myTemplate:template>