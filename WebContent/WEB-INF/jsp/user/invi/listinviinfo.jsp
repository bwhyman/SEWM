<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<mybase:base>
<jsp:attribute name="footer">
	<script>
		$(function() {
			$('#'+'${type}').attr('class','btn btn-danger');
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
  <li><a href="">主页</a></li>
  <li class="active">监考信息</li>
</ol>
<div class="row-fluid">
<div class="pull-right">
	<a class="btn btn-primary" href="invi/downloadinviinfoexcel" role="button">下载监考记录</a>
	</div>
	<c:import url="/WEB-INF/jsp/common/navinvilist.jsp"></c:import>
	
	</div>
	<c:if test="${user.userAuthority.level>=15 }">
	<div class="row-fluid">
	<p class="text-danger">说明: 
	编辑，对监考信息进行修改，修改监考时间地点，添加监考课程名称等，提交后自动转到监考分配<br>
	分配，对已分配监考完成重新分配，对未分配监考创建监考分配
	</p>
	</div>
	</c:if>
	<div>
            <ul class="pagination">
            <c:if test="${currentpage > 1 }">
            	<li class="previous"><a href="invi/listinviinfo/${type}/${currentpage-1}" class="fui-arrow-left"></a></li>
            </c:if>
              <c:forEach var="x" begin="1" end="${countpages }" step="1">
              	<li <c:if test="${x == currentpage }">class="active"</c:if>>
              	<a href="invi/listinviinfo/${type}/${x }">${x }</a></li>
              </c:forEach>
              <c:if test="${currentpage < countpages }">
            	<li class="next"><a href="invi/listinviinfo/${type}/${currentpage+1}" class="fui-arrow-right"></a></li>
            </c:if>   
            </ul>
          </div>
		 <div class="table-responsive">
		 (${firstresult+1 } - ${firstresult + infos.size() } / ${typesize })
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
                  <th>时间</th>
                  <th>地点</th>
                  <th>课程/备注</th>
                  <th>人数</th>
                  <th>分配</th>
                  <th>状态</th>
                  <th>详细</th>
                  <c:if test="${user.userAuthority.level>=15 }">
                  <th>操作</th>
                  </c:if>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${infos }" var="i" varStatus="s">
				<tr>
				<td>${s.count + firstresult }</td>
				<td>${weeks[s.index] }周
				<fmt:formatDate pattern="MM-dd E" value="${i.startTime.getTime() }"/>
				<br>
				<fmt:formatDate pattern="HH:mm" value="${i.startTime.getTime() }"/>
					- <fmt:formatDate pattern="HH:mm" value="${i.endTime.getTime() }"/>
				</td>
				<td>${i.location }</td>
				<td>${i.comment }</td>
				<td>${i.requiredNumber }</td>
				<td>
					<c:forEach items="${i.invigilations }" var="t">
						<c:if test="${t.currentMessageType == null }">
							<span class="label label-danger checkboxspan"></c:if>
						<c:if test="${t.currentMessageType != null }">
							<span class="label label-success checkboxspan"></c:if>
							${t.teacher.user.name }
							</span>
					<br>
					</c:forEach>
				</td>
				
				<td>
					<c:if test="${i.currentStatusType.id == 1 }">
						<span class="label label-danger checkboxspan">
						
				</c:if>
				<c:if test="${i.currentStatusType.id == 2 }">
						<span class="label label-success checkboxspan">
				</c:if>
				<c:if test="${i.currentStatusType.id == 3 }">
						<span class="label label-info checkboxspan">
				</c:if>
				${i.currentStatusType.name }</span></td>
				<%-- <td><fmt:formatDate pattern="MM-dd HH:mm" value="${i.invigilations.size}" /></td> --%>
				<td>
				<a class="btn btn-primary" href="invi/invinfodetail/${i.id }" role="button">详细</a>
				</td>
				<c:if test="${sessionScope.user.userAuthority.level>=15 }">
				<td><a class="btn btn-primary" href="admin/invi/updateinviinfo/${i.id }" role="button">编辑</a>
						<a class="btn btn-primary"  href="admin/invi/assigninvi/${i.id }" role="button">分配</a></td>
				</c:if>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	
		<div>
            <ul class="pagination">
            <c:if test="${currentpage > 1 }">
            	<li class="previous"><a href="invi/listinviinfo/${type}/${currentpage-1}" class="fui-arrow-left"></a></li>
            </c:if>
              <c:forEach var="x" begin="1" end="${countpages }" step="1">
              	<li <c:if test="${x == currentpage }">class="active"</c:if>>
              	<a href="invi/listinviinfo/${type}/${x }">${x }</a></li>
              </c:forEach>
              <c:if test="${currentpage < countpages }">
            	<li class="next"><a href="invi/listinviinfo/${type}/${currentpage+1}" class="fui-arrow-right"></a></li>
            </c:if>   
            </ul>
          </div>
    </jsp:body>
</mybase:base>