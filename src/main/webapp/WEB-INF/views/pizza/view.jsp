<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Pizza | #${pizza.id}. ${pizza.name}</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
</head>
<body>
	<div id="page">
	    <h1>#${pizza.id}. ${pizza.name}</h1>
	    <p>Price: &pound;${pizza.price}</p>
	    <p>[<a href="${pageContext.request.contextPath}/pizza/${pizza.id}/edit">Edit</a>]</p>
		<a href="../">&lt;Back</a>
	</div>
</body>
</html>
