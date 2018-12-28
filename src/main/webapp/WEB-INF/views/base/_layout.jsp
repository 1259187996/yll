<%@ page language="java" session="false"
	contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="layout" uri="layout_tld"%>
<%-- 母版页面 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="static/css/base.css?v=2">
<link rel="stylesheet" href="static/css/jedate.css">
<link rel="shortcut icon" href="#" type="image/png">
<link rel="shortcut icon" href="static/image/favicon.ico"
	type="image/x-icon" />

<script src="static/dep/jq/jquery-1.10.2.min.js"></script>
<script src="static/dep/jq/jquery.nicescroll.js"></script>
<script src="static/js/base.js?v=2"></script>
<script src="static/dep/vue/vue.js"></script>
<script src="static/dep/vue/vue-resource.min.js"></script>
<script src="static/js/jedate.js"></script>
<script src="static/js/confirm.js?"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/echarts.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
<title>Code Flag后台系统</title>

<%--重写的head --%>
<layout:block name="head"></layout:block>
</head>

<body class="sticky-header">
	<div style="min-width: 1200px;" id="travel">
		<jsp:include page="nav.jsp" />
		<div id="main-content" class="main-content">
			<layout:block name="content"></layout:block>
		</div>
</body>
<layout:block name="js"></layout:block>


</html>

