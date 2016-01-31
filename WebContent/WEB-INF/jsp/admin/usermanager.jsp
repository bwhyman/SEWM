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
	$(document).ready(function() {
		$('#selectuserupdate').change(function() {
			$.ajax({
	    	    // The URL for the request
	    	    url: "admin/selectuser",
	    	 
	    	    // The data to send (will be converted to a query string)
	    	    data: {"userId": $(this).val()},
	    	    // Whether this is a POST or GET request
	    	    type: "GET",
	    	  
	    	    // The type of data we expect back
	    	    dataType : "text",
	    	    
	    	    beforeSend: function(jqXHR){
	    	    	$("#myloading").show(); 
	           }, 
	    	    // Code to run if the request succeeds;
	    	    // the response is passed to the function
	    	    success: function(json ) {
	    	    	var a = JSON.parse(json);
	    	    	$( '#inputnameupdate').val(a.name); 
	    	    	$( '#phonenumberupdate').val(a.phoneNumber); 
	    	    	
	    	    },
	    	 
	    	    // Code to run if the request fails; the raw request and
	    	    // status codes are passed to the function
	    	    error: function( xhr, status, errorThrown ) {
	    	        alert( "Status: " + status + "; Error: " + errorThrown);
	    	        console.log( "Error: " + errorThrown );
	    	        console.log( "Status: " + status );
	    	        console.dir( xhr );
	    	    },
	    	 
	    	    // Code to run regardless of success or failure
	    	    /*complete: function( xhr, status ) {
	    	        alert( "The request is complete!" );
	    	    }*/
	    	});
			$('#myloading').hide();
			$('#fieldsetuserupdate').show();
			
			
		});
	})
	</script>
</jsp:attribute>
	<jsp:body>
	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
							aria-expanded="false" aria-controls="collapseOne">
          添加用户
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
          	<form class="form-horizontal">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">姓名</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" id="name" placeholder="姓名" required>
						</div>
					</div>
					<div class="form-group">
						<label for="employeeNumber" class="col-sm-2 col-md-1 control-label">员工号</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" id="employeeNumber" placeholder="员工号" required>
						</div>
					</div>
					
					<div class="form-group">
						<label for="phoneNumber" class="col-sm-2 col-md-1 control-label">手机号</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" id="phoneNumber" placeholder="手机号" required>
						</div>
					</div>
					
					<div class="form-group">
						<label for="title" class="col-sm-2 col-md-1 control-label">职称</label>
						<div class="col-sm-10 col-md-3">
							<select data-toggle="select" class="select select-primary mrs mbm">
							<option value="0" selected>讲师</option>
							<option value="1">副教授</option>
							<option value="2">教授</option>
							<option value="3">助教</option>
						</select>
						</div>
					</div>
					<div class="form-group">
						<label for="introduction" class="col-sm-2 col-md-1 control-label">简介</label>
						<div class="col-sm-10 col-md-3">
							<textarea class="form-control" rows="5" id="introduction" placeholder="简介"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="employeeNumber" class="col-sm-2 col-md-1 control-label">员工号</label>
						<div class="col-sm-10 col-md-3">
							<input type="checkbox" data-toggle="switch" data-on-color="primary" data-off-color="default"  />
						</div>
					</div>	
					<div class="form-group">
						<div class="col-sm-2 col-md-1 control-label"></div>
						<div class="col-sm-10 col-md-3">
							<button type="reset" class="btn btn-danger btn-wide pull-right" id="reset">重置</button>	
							<button type="submit" class="btn btn-primary btn-wide pull-right">提交</button>
						</div>
					</div>
					
				</form>	
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingTwo">
      <h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
							aria-expanded="false" aria-controls="collapseTwo">
          修改用户
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
      <form class="form-horizontal">
      <div class="form-group">
						<label for="title" class="col-sm-2 col-md-1 control-label">用户</label>
						<div class="col-sm-10 col-md-3">
							<select data-toggle="select" class="select select-primary mrs mbm" name="userId" id="selectuserupdate">
							<option>用户</option>
							<c:forEach items="${users}" var="u">
								<option value="${u.id}">${u.name }</option>
							</c:forEach>
						</select>
						</div>
						</div>
						</form>
						<div id="myloading" hidden><img src="resources/images/loading.gif"/></div>
        <form class="form-horizontal">
 					<fieldset hidden=""  id="fieldsetuserupdate">
						<div class="form-group">
						<label for="name" class="col-sm-2 col-md-1 control-label">姓名</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" id="inputnameupdate" placeholder="姓名" required>
						</div>
					</div>
					<div class="form-group">
						<label for="employeeNumber" class="col-sm-2 col-md-1 control-label">员工号</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" id="employeeNumber" placeholder="员工号" required>
						</div>
					</div>
					
					<div class="form-group">
						<label for="phoneNumber" class="col-sm-2 col-md-1 control-label">手机号</label>
						<div class="col-sm-10 col-md-3">
							<input type="text" class="form-control" id="phonenumberupdate" placeholder="手机号" required>
						</div>
					</div>
					
					<div class="form-group">
						<label for="title" class="col-sm-2 col-md-1 control-label">职称</label>
						<div class="col-sm-10 col-md-3">
							<select data-toggle="select" class="select select-primary mrs mbm">
							<option value="0" selected>讲师</option>
							<option value="1">副教授</option>
							<option value="2">教授</option>
							<option value="3">助教</option>
						</select>
						</div>
					</div>
					<div class="form-group">
						<label for="introduction" class="col-sm-2 col-md-1 control-label">简介</label>
						<div class="col-sm-10 col-md-3">
							<textarea class="form-control" rows="5" id="introduction" placeholder="简介"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 col-md-1 control-label"></div>
						<div class="col-sm-10 col-md-3">
							<button type="submit" class="btn btn-primary btn-wide pull-right">提交</button>
						</div>
					</div>
					</fieldset>
 			</form>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingThree">
      <h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"
							aria-expanded="false" aria-controls="collapseThree">
          Collapsible Group Item #3
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
      <div class="panel-body">
        
      </div>
    </div>
  </div>
</div>
    </jsp:body>
</myTemplate:template>