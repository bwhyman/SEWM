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
			$('#'+'${type}').attr('class','btn btn-danger');
			if('${currentPage}'=='1'){
				$('#previous').addClass('disabled');
				$('#previous').click(function(){
					return false;
				})
			}else{
				$('#previous').removeClass('disabled');
			}
			if('${currentPage}'=='${countPage}'){
				$('#next').addClass('disabled');
				$('#next').click(function(){
					return false;
				})
			}else{
				$('#next').removeClass('disabled');
			}
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
	  <li><a href="">主页</a></li>
	  <li><a href="project/projectmanagement">毕设管理</a></li>
	  <li class="active">结果信息</li>
	</ol>
	<a id="selected" class="btn btn-primary" href="project/selectresult/selected/1" role="button">已选题</a>
	<a id="unselect" class="btn btn-primary" href="project/selectresult/unselect/1" role="button">未选题</a>
	<a class="btn btn-primary" href="project/exportSelectResult" role="button">导出选题信息</a>
	<br>
		<c:if test="${currentPage*15>=count }">
			(${(currentPage-1)*15+1 } &nbsp;-&nbsp;${count }&nbsp;/&nbsp;${count })
		</c:if>
		<c:if test="${currentPage*15<count }">
			(${(currentPage-1)*15+1 }&nbsp;-&nbsp;${currentPage*15 }&nbsp;/&nbsp;${count })
		</c:if>
	<br>
	<div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>学号</th>
                  <th>学生</th>
                  <th>班级</th>
                  <th>题目</th>
                  <th>指导老师</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${students }" var="st" varStatus="s">
				<tr>
				<td>${s.count + (currentPage-1)*15 }</td>
				<td>${st.student.studentId }</td>
				<td>${st.student.name }</td>
				<td>${st.student.classes.name }</td>
				<td>${st.selectedTitleDetail.title.name }</td>
				<td>${st.selectedTitleDetail.title.teacher.user.name }</td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	<nav>
			  <ul class="pagination pagination-lg">
			    <li id="previous">
			      <a href="project/selectresult/${type }/${currentPage-1 }" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <c:forEach begin="1" end="${countPage }" var="c">
			    	<c:if test="${c==currentPage }">
			    		<li class="active"><a href="project/selectresult/${type }/${c }">${c }</a></li>
			    	</c:if>
			    	<c:if test="${c!=currentPage }">
			    		<li><a href="project/selectresult/${type }/${c }">${c }</a></li>
			    	</c:if>
			    </c:forEach>
			    <li id="next">
			      <a href="project/selectresult/${type }/${currentPage+1 }" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
	</div>
    </jsp:body>
</myTemplate:template>