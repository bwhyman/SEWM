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
  <li class="active">监考信息</li>
</ol>
	<a id="unassinvi" class="btn btn-primary" href="invi/listinviinfo/unassinvi/1" role="button">未分配</a>
	<a id="assinvi" class="btn btn-primary" href="invi/listinviinfo/assinvi/1" role="button">已分配</a>
	<a id="done" class="btn btn-primary" href="invi/listinviinfo/done/1" role="button">已完成</a>
	<a id="all" class="btn btn-primary" href="invi/listinviinfo/all/1" role="button">全部</a>
	<c:if test="${user.userAuthority.level>=15 }">
	<!-- <p class="text-danger">说明: 
	编辑，对监考信息进行修改，修改监考时间地点，添加监考课程名称等，提交后自动转到监考分配<br>
	分配，对已分配监考完成重新分配，对未分配监考创建监考分配
	</p> -->
	</c:if>
	<c:if test="${infos.size()!=0 }">
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
                  <th>日期</th>
                  <th>时间</th>
                  <th>地点</th>
                  <th>课程</th>
                  <th>人数</th>
                  <th>分配</th>
                  <th>状态</th>
                  <th>导入时间</th>
                  <c:if test="${user.userAuthority.level>=15 }"></c:if>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${infos }" var="i" varStatus="s">
				<tr>
				<td>${s.count + (currentPage-1)*15 }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${i.startTime.getTime() }"/></td>
				<td><fmt:formatDate pattern="HH:mm" value="${i.startTime.getTime() }"/>
					- <fmt:formatDate pattern="HH:mm" value="${i.endTime.getTime() }"/></td>
				<td>${i.location }</td>
				<td>${i.course }</td>
				<td>${i.requiredNumber }</td>
				<td>
					<c:forEach items="${i.invigilations }" var="t">${t.teacher.user.name }<br></c:forEach>
				</td>
				
				<td>
					<c:if test="${i.currentStatusType.id == 1 }">
						<span class="label label-danger checkboxspan">
						
				</c:if>
				<c:if test="${i.currentStatusType.id == 2 }">
						<span class="label label-success checkboxspan">
				</c:if>
				<c:if test="${i.currentStatusType.id == 3 }">
						<span class="label label-success checkboxspan">
				</c:if>
				<c:if test="${i.currentStatusType.id == 4 }">
						<span class="label label-info checkboxspan">
				</c:if>
				${i.currentStatusType.name }</span></td>
				<c:if test="${user.userAuthority.level>=15 }">
				<td><fmt:formatDate pattern="MM-dd HH:mm" value="${i.insertTime }" /></td>
				<td><a class="btn btn-primary" href="admin/invi/updateinviinfo/${i.id }" role="button">编辑</a>  
						<a class="btn btn-primary"  href="admin/invi/assigninvi/${i.id }" role="button">分配</a></td>
				</c:if>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	<c:if test="${infos.size()!=0 }">
		<nav>
			  <ul class="pagination pagination-lg">
			    <li id="previous">
			      <a href="invi/listinviinfo/${type }/${currentPage-1 }" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <c:forEach begin="1" end="${countPage }" var="c">
			    	<c:if test="${c==currentPage }">
			    		<li class="active"><a href="invi/listinviinfo/${type }/${c }">${c }</a></li>
			    	</c:if>
			    	<c:if test="${c!=currentPage }">
			    		<li><a href="invi/listinviinfo/${type }/${c }">${c }</a></li>
			    	</c:if>
			    </c:forEach>
			    <li id="next">
			      <a href="invi/listinviinfo/${type }/${currentPage+1 }" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
		</nav>
	</c:if>
    </jsp:body>
</myTemplate:template>