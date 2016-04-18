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
  <li class="active">任务信息</li>
</ol>

	<a id="started" class="btn btn-primary" href="task/list/started/1" role="button">已开启</a>
	<a id="expired" class="btn btn-primary" href="task/list/expired/1" role="button">已过期</a>
	<a id="closed" class="btn btn-primary" href="task/list/closed/1" role="button">已关闭</a>
	<a id="all" class="btn btn-primary" href="task/list/all/1" role="button">全部</a>
	<p class="text-danger">说明: 
	详细：任务详细信息，任务提交操作
	</p>
	<c:if test="${tasks.size()!=0 }">
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
                  <th>截止时间</th>
                  <th>任务</th>
                  <th>状态</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${tasks }" var="t" varStatus="s">
				<tr>
				<td>${s.count + (currentPage-1)*15 }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${t.endTime.getTime() }"/></td>
				<td>${t.name }</td>
				<td>
					<c:if test="${t.currentStatus.id == 3 }">
						<span class="label label-danger checkboxspan">		
				</c:if>
				<c:if test="${t.currentStatus.id == 2 }">
						<span class="label label-warning checkboxspan">
				</c:if>
				<c:if test="${t.currentStatus.id == 1 }">
						<span class="label label-success checkboxspan">
				</c:if>
				${t.currentStatus.name }</span></td>
				<td><a class="btn btn-primary" href="task/filetaskdetail/${t.id }" role="button">详细</a></td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	<c:if test="${tasks.size()!=0 }">
			<nav>
			  <ul class="pagination pagination-lg">
			    <li id="previous">
			      <a href="task/list/${type }/${currentPage-1 }" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <c:forEach begin="1" end="${countPage }" var="c">
			    	<c:if test="${c==currentPage }">
			    		<li class="active"><a href="task/list/${type }/${c }">${c }</a></li>
			    	</c:if>
			    	<c:if test="${c!=currentPage }">
			    		<li><a href="task/list/${type }/${c }">${c }</a></li>
			    	</c:if>
			    </c:forEach>
			    <li id="next">
			      <a href="task/list/${type }/${currentPage+1 }" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
		</c:if>
    </jsp:body>
</myTemplate:template>