<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<myTemplate:template>
	<jsp:attribute name="footer">
		<style>
			.panel{
				margin-left: 3em;
			}
		</style>
		<script>
			$(function(){
				if('${user.phoneNumber }'.length == 0){
					$('#myModal').modal({
						'backdrop':'static',
						'show' :true
					});
				}
			})
		</script>
	</jsp:attribute>
	<jsp:body>
		<!-- Modal -->
		<div class="modal fade bs-example-modal-md" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog modal-md" role="document">
		    <div class="modal-content col-md-10">
		      <div class="modal-header">
		        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
		        <h4 class="modal-title" id="myModalLabel">请输入电话号码</h4>
		      </div>
		      <form action="student/addPhoneNumber" method="post">
		      	<div class="modal-body">
			        <div class="input-group">
							<label for="phoneNumber" class="sr-only">电话号码</label>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-phone-alt"></span>
							</span>
							<input type="text" name="phoneNumber" class="form-control" placeholder="电话号码" required>
					</div>
					<div class="input-group">
							<p class="text-danger"><strong>*为保持毕设期间及时取得联系，请先填写联系电话！</strong></p>
					</div>
			    </div>
			    <div class="modal-footer">
			        <button type="submit" class="btn btn-primary">保存</button>
			    </div>
		      </form>
		      
		    </div>
		  </div>
		</div>
        </jsp:body>
</myTemplate:template>