
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="mybase" tagdir="/WEB-INF/tags/"%>
<%@attribute name="breadcrumb" fragment="true"%>
<mybase:base>
	<jsp:body>
		aaaa<jsp:invoke fragment="breadcrumb" />
	</jsp:body>
</mybase:base>

