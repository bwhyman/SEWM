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
		})
	</script>
</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
	  <li><a href="">主页</a></li>
	  <li><a href="project/projectmanagement">毕设管理</a></li>
	  <li class="active">选题信息</li>
	</ol>
	<c:forEach items="${teachers }" var="t">
		<a id="${t.id }" class="btn btn-primary" href="project/selecttitles/${t.id }/1" role="button" style="margin-bottom: 2px;">${t.user.name }(${t.leadNum })</a>
	</c:forEach>
	<a id="-1" class="btn btn-primary" href="project/selecttitles/-1/1" role="button">全部题目</a>
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
                  <th>论证报告</th>
                  <th>已选人数</th>
                  <th>已确认学生</th>
                  <th>操作</th>
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
						<td>${p.title.selectedTitleDetails.size() }</td>
						<c:if test="${p.title.selectedTitleDetails.size()==0 }">
							<td></td>
							<td></td>
						</c:if>
						<c:forEach items="${p.title.selectedTitleDetails }" var="st">
							<c:if test="${st.confirmed == true }">
								<td><span class="label label-success">${st.student.student.name }</span></td>							
							</c:if>
							<c:if test="${ p.title.teacher.id == user.id && st.confirmed == true}">
								<td><a class="updatestudent" href="#" data-toggle="modal" data-target="#myModal">修改</a>
										<!-- Modal -->
										<div class="modal fade myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
										  <div class="modal-dialog" role="document">
										    <div class="modal-content">
										      <div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
										        <h6 class="modal-title" id="myModalLabel">${p.title.name }</h6>
										      </div>
										      <form class="form-horizontal" action="project/updateselectproject" method="POST">
										      <div class="modal-body">
											        <c:forEach items="${p.title.selectedTitleDetails }" var="st">
														<div class="form-group">
															<div class="col-sm-2 col-md-1 control-label"></div>
															<label class="radio col-md-4 col-md-offset-1">
														         <input type="radio" data-toggle="radio" class="myradio" name="studentid" value="${st.student.student.id }" data-radiocheck-toggle="radio" required
														        	<c:if test="${st.confirmed == true }">checked="checked"</c:if>>
														        	<c:if test="${st.confirmed == true }">
														        		<input type="hidden" name="oldstudentid" value="${st.student.student.id }">
														        	</c:if>
														          ${st.student.student.name }
														    </label>
														</div>
													</c:forEach>
										      </div>
										      <div class="modal-footer">
										        <button type="submit" class="btn btn-primary">保存</button>
										        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
										      </div>
										      </form>
										    </div>
										  </div>
										</div></td>
									</c:if>
								<c:if test="${ p.title.teacher.id != user.id && st.confirmed == true}">
									<td></td>
								</c:if>
						</c:forEach>
				</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
	<c:if test="${type==-1 }">
		<nav>
			  <ul class="pagination pagination-lg">
			    <li id="previous">
			      <a href="project/selecttitles/${type }/${currentPage-1 }" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <c:forEach begin="1" end="${countPage }" var="c">
			    	<c:if test="${c==currentPage }">
			    		<li class="active"><a href="project/selecttitles/${type }/${c }">${c }</a></li>
			    	</c:if>
			    	<c:if test="${c!=currentPage }">
			    		<li><a href="project/selecttitles/${type }/${c }">${c }</a></li>
			    	</c:if>
			    </c:forEach>
			    <li id="next">
			      <a href="project/selecttitles/${type }/${currentPage+1 }" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
		</nav>
	</c:if>
    </jsp:body>
</myTemplate:template>