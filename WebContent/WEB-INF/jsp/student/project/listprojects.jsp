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
		$(function() {
			$('#'+'${type}').attr('class','btn btn-danger');
			$('.selecttitle').click(function(){
				var current = $(this);
				var strs= new Array();
				strs = current.attr('href').split(",");
				$.post('student/project/selecttitle',{
					'titleId':strs[0],
					'teacherId':strs[1]},function(data){
						if(data=='success'){
							location.href = 'student/project/listtitles/' + '${type}' + '/1';
						}
				}) 
				return false;
			})
			
			$('.myspan').each(function(){
				$(this).parent('td').children('a').hide()
			})
			$('.selecttitle').next('a').hide();
			
			$(".telphone").mouseenter(function(){
				$(this).popover('show');
			})
			$(".telphone").mouseleave(function(){
				$(this).popover('hide');
			})
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li class="active">题目信息</li>
</ol>
		<!-- <ul class="text-danger">
			<li>教师后面的数字是<span class="label label-danger">教师最多还能带学生人数</span></li>
			<li>若多人选择同一题目，导师将根据学生实际情况进行确认学生选题是否成功</li>
			<li>重新选择题目后，之前选择的自动无效</li>
		</ul> -->
		<c:if test="${selectedTitleDetail != null}">
			<div class="alert alert-success alert-dismissable" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				<strong>已选题目：</strong> ${selectedTitleDetail.title.name }
			</div>
		</c:if>
		<c:forEach items="${teachers }" var="t">
			<a id="${t.id }" class="btn btn-primary" href="student/project/listtitles/${t.id }/1" role="button" style="margin-bottom: 2px;">${t.user.name }(${t.leadNum })</a>
		</c:forEach>
		<a id="-1" class="btn btn-primary" href="student/project/listtitles/-1/1" role="button">全部题目</a>
		<c:if test="${type==-1 }">
			<br>
				<c:if test="${currentPage*15>=count }">
					(${(currentPage-1)*15+1 } &nbsp;-&nbsp;${count }&nbsp;/&nbsp;${count })
				</c:if>
				<c:if test="${currentPage*15<count }">
					(${(currentPage-1)*15+1 }&nbsp;-&nbsp;${currentPage*15 }&nbsp;/&nbsp;${count })
				</c:if>
			<br>
		</c:if>
		<div class="table-responsive">
			<table class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					 <th>#</th>
	                  <th>题目</th>
	                  <th>指导教师</th>
	                  <th>论证报告</th>
	                  <th>已选学生</th>
	                  <th>已确认学生
	                  	<c:if test="${!selectedTitleDetail.confirmed }">
	                  		/操作
	                  	</c:if>
	                  </th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${fileDetails }" var="p" varStatus="s">
							<tr>
								<td>${s.count+(currentPage-1)*15 }</td>
								<td><a href="project/title/${p.id }">${p.title.name }</a></td>
								<td >
									 <a class="telphone" tabindex="0" data-toggle="popover" data-trigger="focus" data-placement="top" title="联系方式" data-content="${p.title.teacher.user.phoneNumber }">${p.title.teacher.user.name }</a>
								</td>
								<td>
									<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
								</td>
								<td><c:forEach items="${p.title.selectedTitleDetails }" var="t">${t.student.student.name }<br></c:forEach></td>
								<td>
									<c:if test="${!selectedTitleDetail.confirmed }">
				                  		<a href="${p.title.id },${p.title.teacher.user.id}" class="selecttitle" id="${p.title.teacher.user.id}">选择</a>
				                  	</c:if>
									<c:forEach items="${p.title.selectedTitleDetails }" var="st">
										<c:if test="${st.confirmed }">
											<span class="label label-success myspan">${st.student.student.name }</span>
										</c:if>
									</c:forEach>
								</td>
							</tr>
				</c:forEach>
				</tbody>
		</table>
		</div>
		<c:if test="${type==-1 && fileDetails.size()>0 }">
			<div>
	            <ul class="pagination">
		            <c:if test="${currentPage > 1 }">
		            	<li class="previous"><a href="student/project/listtitles/-1/${currentPage-1 }" class="fui-arrow-left"></a></li>
		            </c:if>
		              <c:forEach var="x" begin="1" end="${countPage }" step="1">
		              	<li <c:if test="${x == currentPage }">class="active"</c:if>>
		              	<a href="student/project/listtitles/-1/${x }">${x }</a></li>
		              </c:forEach>
		              <c:if test="${currentPage < countPage }">
		            	<li class="next"><a href="student/project/listtitles/-1/${currentPage+1 }" class="fui-arrow-right"></a></li>
		            </c:if>   
	            </ul>
	         </div>
         </c:if>
    </jsp:body>
</myTemplate:template>