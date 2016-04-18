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
	<jsp:body>
	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
							aria-expanded="false" aria-controls="collapseOne">
          Collapsible Group Item #1
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
        1Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
        <hr>
          	<form class="form-horizontal">
          		<fieldset>
          			<legend>添加用户</legend>
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
					<button type="submit" class="btn btn-primary btn-wide">提交</button>
					<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>
					</fieldset>
				</form>
		<hr>		
 			
 			<form class="form-horizontal">
 				<fieldset>
 					<legend>修改用户</legend>
 					<div class="form-group">
						<label for="title" class="col-sm-2 col-md-1 control-label">用户</label>
						<div class="col-sm-10 col-md-3">
							<select data-toggle="select" class="select select-primary mrs mbm">
							<option>用户</option>
							<c:forEach items="${users}" var="u">
								<option value="${u.id}">${u.name }</option>
							</c:forEach>
						</select>
						</div>
						</div>
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
					<button type="submit" class="btn btn-primary btn-wide">提交</button>
					<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>
 				</fieldset>
 			</form>
        
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingTwo">
      <h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
							aria-expanded="false" aria-controls="collapseTwo">
          Collapsible Group Item #2
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
        2Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
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
        3Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
      </div>
    </div>
  </div>
</div>
    </jsp:body>
</myTemplate:template>