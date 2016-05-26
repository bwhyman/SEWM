<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<myTemplate:template>
	<jsp:attribute name="footer">
		<style>
.panel {
	margin-left: 3em;
}
</style>
	</jsp:attribute>
	<jsp:body>
        <div class="row">
        	<!-- 任务信息 -->
        	<div class="panel panel-info col-md-5">
			  <!-- Default panel contents -->
			  <div class="panel-heading">任务信息</div>
			  <div class="panel-body">
			  	<p>当前分值:<span class="label label-success">${teacherTask.point }</span>
				</p>
			    <p>开启状态未完成:<span class="label label-success">${startUndoTaskTimes }；</span>过期状态未完成:<span
							class="label label-success">${expiredUndoTaskTimes }；</span>
				</p>
			    
			    	<!-- Table -->
					  <table class="table">
					  	<thead>
							<tr>
					   			<th>未完成任务</th>
					   			<th></th>
					   		</tr>
					   	</thead>
					   	<tbody>
					   		<c:forEach items="${recentTasks }" var="rt">
					   		<tr>
					   			<td>任务:<a href="task/filetaskdetail/${rt.id }">${rt.fileTask.name }</a></td>
					   			<td>截止时间:<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${rt.fileTask.endTime.getTime() }" /></td>
					   		</tr>
					   		</c:forEach>
					   	</tbody>
					  </table>
			    
			    <p class="text-right">
					<a href="task/listmytask/undone"><strong>more</strong></a>
				</p>
			  </div>
			</div>
			<!-- 监考信息 -->
			<div class="panel panel-info col-md-5">
			  <!-- Default panel contents -->
			  <div class="panel-heading">监考信息</div>
			  <div class="panel-body">
			    <p>普通监考:<span class="label label-success">未完成：${undoInviTimes }；分配总数：${myInviTimes }；</span>
					</p>
			    <p>特殊监考:<span class="label label-success">未完成：${undoSpeInviTimes }；分配总数：${mySpeInviTimes }；</span>
					</p>
				<!-- Table -->
					  <table class="table">
					  	<thead>
					  		<tr>
					  			<th>未完成监考</th>
					  			<th></th>
					  		</tr>
					  	</thead>
					   	<tbody>
					   		<c:forEach items="${undoInvi }" var="i">
					   		<tr>
					   			<td>地点:${i.location }</td>
					   			<td>时间:<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${i.startTime.getTime() }" /></td>
								
					   		</tr>
					   		</c:forEach>
					   	</tbody>
					  </table>
			    
			    <p class="text-right">
					<a href="invi/listmyinviinfo/undone"><strong>more</strong></a>
				</p>
			  </div>
			</div>
			<!-- 毕设信息 -->
			<div class="panel panel-info col-md-5">
			  <!-- Default panel contents -->
			  <div class="panel-heading">毕设信息</div>
			  <div class="panel-body">
			    <c:if test="${stageType=='titles' }">
			    	<p>当前阶段：<span class="label label-success">选题阶段</span></p>
			    	<p>1.请及时添加题目，以便学生选题</p>
			    	<p>2.查看选题信息</p>
			    	<p>3.确认学生选题</p>
			    </c:if>
			    <c:if test="${stageType=='opening' }">
			    	<p>当前阶段：<span class="label label-success">开题阶段</span></p>
			    	<p>1.查看开题报告，进行指导</p>
			    	<p>2.请注意通知，及时进行开题阶段评审，逾期将无法评审</p>
			    	<p>3.查看评审结果信息</p>
			    </c:if>
			    <c:if test="${stageType=='interim' }">
			    	<p>当前阶段：<span class="label label-success">中期阶段</span></p>
			    	<p>1.查看中期报告，进行指导</p>
			    	<p>2.请注意通知，及时进行中期阶段评审，逾期将无法评审</p>
			    	<p>3.查看评审结果信息</p>
			    </c:if>
			    <c:if test="${stageType=='paper' }">
			    	<p>当前阶段：<span class="label label-success">结题阶段</span></p>
			    	<p>1.查看论文，进行指导</p>
			    	<p>2.请注意通知，及时进行结题阶段评审，逾期将无法评审</p>
			    	<p>3.查看评审结果信息</p>
			    </c:if>
			  </div>
			</div>
		</div>  

        </jsp:body>
</myTemplate:template>