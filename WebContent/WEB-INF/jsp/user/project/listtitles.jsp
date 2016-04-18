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
			$('.deltitle').click(function(){
				var current = $(this);
				$.post('project/deltitle',{
					'id':current.attr('href')
				},function(){
					location.href = 'project/listtitles/'+ '${user.id}' + '/1';
				})
				return false;
			})
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
  <li class="active">题目信息</li>
</ol>
	<c:forEach items="${teachers }" var="t">
		<a id="${t.id }" class="btn btn-primary" href="project/listtitles/${t.id }/1" role="button" style="margin-bottom: 2px;">${t.user.name }(${t.leadNum })</a>
	</c:forEach>
	<a id="-1" class="btn btn-primary" href="project/listtitles/-1/1" role="button">全部题目</a>
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
                  <th>题目性质</th>
                  <th>指导教师</th>
                  <th>论证报告</th>
                  <c:if test="${user.id==type }">
                  	<th>操作</th>
                  </c:if>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${fileDetails }" var="p" varStatus="s">
						<tr>
							<c:if test="${type==-1 }">
								<td>${s.count + (currentPage-1)*15 }</td>
							</c:if>
							<c:if test="${type!=-1 }">
								<td>${s.count}</td>
							</c:if>
							<td><a href="project/title/${p.id }">${p.title.name }</a></td>
							<td>${p.title.property }</td>
							<td>${p.title.teacher.user.name }</td>
							<td>
								<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
							</td>
							<c:if test="${user.id==type }">
								<td>
									<a href="project/updatetitle/${p.id}">修改</a>
									<a href="${p.id }" class="deltitle">删除</a>
								</td>
							</c:if>
						</tr>
			</c:forEach>
			</tbody>
	</table>
	<c:if test="${type==-1 }">
			<nav>
			  <ul class="pagination pagination-lg">
			    <li id="previous">
			      <a href="project/listtitles/-1/${currentPage-1 }" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <c:forEach begin="1" end="${countPage }" var="c">
			    	<c:if test="${c==currentPage }">
			    		<li class="active"><a href="project/listtitles/-1/${c }">${c }</a></li>
			    	</c:if>
			    	<c:if test="${c!=currentPage }">
			    		<li><a href="project/listtitles/-1/${c }">${c }</a></li>
			    	</c:if>
			    </c:forEach>
			    <li id="next">
			      <a href="project/listtitles/-1/${currentPage+1 }" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
		</c:if>
	</div>
    </jsp:body>
</myTemplate:template>