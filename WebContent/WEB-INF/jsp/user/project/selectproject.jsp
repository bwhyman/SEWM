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
			
			//更改确认选题的学生
			$('.updatestudent').click(function(){
				$(this).next('.myModal').modal('show')
				return false;
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
	  <li><a href="project/projectmanagement/selecttitle">选题信息</a></li>
	  <li class="active">选题信息</li>
	</ol>
	<c:forEach items="${teachers }" var="t">
		<a id="${t.id }" class="btn btn-primary" href="project/selecttitles/${t.id }/1" role="button" style="margin-bottom: 2px;">${t.user.name }(${t.leadNum })</a>
	</c:forEach>
	<a id="-1" class="btn btn-primary" href="project/selecttitles/-1/1" role="button">全部题目</a>
	<c:if test="${type==-1 && fileDetails.size()>0 }">
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
                  <th>论证报告</th>
                  <th>已选学生</th>
                  <th>已确认学生</th>
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
						<td><a href="project/title/${p.title.id }">${p.title.name }</a></td>
						<td>${p.title.property }</td>
						
						<td>
							<a href="download/${p.directory }/${p.fileName}/">论证报告</a>
						</td>
						<td>
							<c:forEach items="${p.title.selectedTitleDetails }" var="t">
								<a class="telphone" tabindex="0" data-toggle="popover" data-trigger="focus" data-placement="top" title="${t.student.student.name }" data-content="${t.student.student.phoneNumber }">${t.student.student.name }</a>
								<br>
							</c:forEach>
						</td>
						<td>
							<c:forEach items="${p.title.selectedTitleDetails }" var="st">
								<c:if test="${st.confirmed == true }">
									<span class="label label-success">${st.student.student.name }</span>						
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
	            	<li class="previous"><a href="project/selecttitles/${type }/${currentPage-1 }" class="fui-arrow-left"></a></li>
	            </c:if>
	              <c:forEach var="x" begin="1" end="${countPage }" step="1">
	              	<li <c:if test="${x == currentPage }">class="active"</c:if>>
	              	<a href="project/selecttitles/${type }/${x }">${x }</a></li>
	              </c:forEach>
	              <c:if test="${currentPage < countPage }">
	            	<li class="next"><a href="project/selecttitles/${type }/${currentPage+1 }" class="fui-arrow-right"></a></li>
	            </c:if>   
            </ul>
         </div>
	</c:if>
    </jsp:body>
</myTemplate:template>