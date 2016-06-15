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
				$('#allcheck').on('change.radiocheck', function() {
					if ($('#allchecked').prop('checked')) {
						$('.student').radiocheck('check') 
					} else {
						$('.student').radiocheck('uncheck') 
					}
				});
				
				//提交按钮是否可用，若无选中项，无法提交
				$('.student').on('change.radiocheck', function(){
					var isHaveSelected = false;
					$('.student').each(function(i){
						if ($(this).prop('checked')) {
							isHaveSelected = true;
						}
					})
					if (isHaveSelected) {
						$('#btn_submit').removeAttr('disabled');
					}else{
						$('#btn_submit').attr('disabled','disabled');
					}
				})
				
				$(".userinfo").popover({
					trigger:'manual',
		            html: 'true', //needed to show html of course
		            animation: false
		        }).on("mouseenter", function () {
		                    var _this = this;
		                    $(this).popover("show");
		                    $(this).siblings(".popover").on("mouseleave", function () {
		                        $(_this).popover('hide');
		                    });
		                }).on("mouseleave", function () {
		                    var _this = this;
		                    setTimeout(function () {
		                        if (!$(".popover:hover").length) {
		                            $(_this).popover("hide")
		                        }
		                    }, 100);
		         });
			})
		</script>
	</jsp:attribute>
	<jsp:body>
	<ol class="breadcrumb">
	  <li><a href="">主页</a></li>
	  <li><a href="admin/project/projectmanagement">毕设管理</a></li>
	  <li class="active">${typeZH }评审</li>
	</ol>
		<form class="form-horizontal" action="admin/project/updateevaluation" method="POST" style="margin-left:1em">
			<div class="form-group">
				<label class="checkbox col-sm-5 col-md-1" id="allcheck">
					<input type="checkbox" data-toggle="checkbox" id="allchecked">
						<span class="label label-danger checkboxspan">
							全选
						</span>
				</label>
			</div>
			<c:if test="${studentProjects!=null && studentProjects.size()!=0 }">
				<div class="row">
					<h6>已通过教师评审</h6>
					<c:forEach items="${studentProjects }" var="i" varStatus="s">
						<div class="form-group col-sm-5 col-md-4">
							<label class="checkbox">
								<input type="checkbox" class="student" data-toggle="checkbox" name="studentIds"  value="${i.student.id }">
									<a tabindex="0" class="userinfo" data-toggle="popover" data-trigger="focus" 
										title="详细信息" data-content="
										学号：${i.student.studentId }<br>
								                班级：${i.student.classes.name}<br>
								                电话：${i.student.phoneNumber }" style="font-size: large;">
								             <span class="label label-success" style="font-size: large;">${i.student.name }</span>&nbsp;&nbsp;<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
								    </a>
							</label>
						</div>
					</c:forEach>
				</div>
			</c:if>
			
				<c:if test="${notPassTeacherStus!=null && notPassTeacherStus.size()>0 }">
					<div class="row">
						<h6>未通过教师评审</h6>
						<c:forEach items="${notPassTeacherStus }" var="i">
							<c:if test="${i.opened }">
								<div class="form-group col-sm-5 col-md-4">
									<label class="checkbox">
										<input type="checkbox" class="student" data-toggle="checkbox" name="studentIds"  value="${i.student.id }">
											<a tabindex="0" class="userinfo" data-toggle="popover" data-trigger="focus"
											title="详细信息" data-content="
											学号：${i.student.studentId }<br>
									                班级：${i.student.classes.name}<br>
									                电话：${i.student.phoneNumber }" style="font-size: large;">
									             <span class="label label-warning" style="font-size: large;">${i.student.name }</span>&nbsp;&nbsp;<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
									        </a>
									</label>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
			
				<c:if test="${notOpenedStus!=null && notOpenedStus.size()>0 }">
					<div class="row">
						<h6>未开题</h6>
						<c:forEach items="${notOpenedStus }" var="i">
							<div class="form-group col-sm-5 col-md-4">
								<label class="checkbox">
									<input type="checkbox"data-toggle="checkbox" name="studentIds"  value="${i.student.id }" disabled="disabled">
										<a tabindex="0" class="userinfo" data-toggle="popover" data-trigger="focus" 
										title="详细信息" data-content="
										学号：${i.student.studentId }<br>
								                班级：${i.student.classes.name}<br>
								                电话：${i.student.phoneNumber }" style="font-size: large;">
								             <span class="label label-default" style="font-size: large;">${i.student.name }</span>&nbsp;&nbsp;<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
								        </a>
								</label>
							</div>
						</c:forEach>
					</div>
				</c:if>
				<input type="hidden" name="type" value="${type }">
			<div class="form-group">
				<div class="col-md-1">
					<button type="submit" class="btn btn-primary btn-wide" id="btn_submit" disabled="disabled">提交</button>
				</div>
			</div>
		</form>

	<c:if test="${evaluations!=null && evaluations.size()!=0 }">
		<c:if test="${currentPage*15>=count }">
			<div>(${(currentPage-1)*15+1 } &nbsp;-&nbsp;${count }&nbsp;/&nbsp;${count })</div>
		</c:if>
		<c:if test="${currentPage*15<count }">
			<div>(${(currentPage-1)*15+1 }&nbsp;-&nbsp;${currentPage*15 }&nbsp;/&nbsp;${count })</div>
		</c:if>
		<div class="table-responsive">
			<table class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					 <th>#</th>
	                 <th>学号</th>
	                 <th>姓名</th>
                     <th>导师评审</th>
                     <th>专业评审</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${evaluations }" var="i" varStatus="s">
						<tr>
							<td>${s.count + (currentPage-1)*15 }</td>
							<td>${i.student.student.studentId }</td>
							<td>${i.student.student.name }</td>
							<td>
								<c:if test="${i.teacherEval == true }">
									<span class="label label-success">是</span>
								</c:if>
								<c:if test="${i.teacherEval == false }">
									<span class="label label-danger">否</span>
								</c:if>
							</td>
							<td>
								<c:if test="${i.managerEval == true }">
									<span class="label label-success">是</span>
								</c:if>
								<c:if test="${i.managerEval == false }">
									<span class="label label-danger">否</span>
								</c:if>
							</td>
						</tr>
				</c:forEach>
				</tbody>
		</table>
		</div>
		<c:if test="${evaluations.size()!=0 }">
			<div>
            <ul class="pagination">
            <c:if test="${currentPage > 1 }">
            	<li class="previous"><a href="admin/project/listevaluation/${type }/${currentPage-1}" class="fui-arrow-left"></a></li>
            </c:if>
              <c:forEach var="x" begin="1" end="${countPage }" step="1">
              	<li <c:if test="${x == currentPage }">class="active"</c:if>>
              	<a href="admin/project/listevaluation/${type }/${x }">${x }</a></li>
              </c:forEach>
              <c:if test="${currentPage < countPage }">
            	<li class="next"><a href="admin/project/listevaluation/${type }/${currentPage+1 }" class="fui-arrow-right"></a></li>
            </c:if>   
            </ul>
          </div>
		</c:if>
	</c:if>
    </jsp:body>
</myTemplate:template>