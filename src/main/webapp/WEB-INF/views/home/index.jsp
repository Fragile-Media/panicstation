<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Home</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
</head>
<body>
	<div id="page">
		<h1>Hello world!</h1>
		<p>The time on the server is ${serverTime}.</p>
		<ul>
			<li><a href="./pizza">Pizza</a></li>
			<li><a href="/page/1">Contact</a></li>
		</ul>
	</div>
</body>
</html>
