<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Edit Pizza| ${pizza.id}</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
</head>
<body>
	<div id="page">
	    <h1>Editing Pizza ${pizza.id}</h1>	    
	    <form:form action="${pageContext.request.contextPath}/pizza/${pizza.id}/edit" modelAttribute="pizza">
	    	<fieldset>
				<form:label path="name">Name:</form:label>
		        <form:input path="name" />
				<form:label path="price">Price:</form:label>
		        <form:input path="price" />
				<input type="submit" value="Submit" />
			</fieldset>
		</form:form>
		<a href="${pageContext.request.contextPath}/pizza">&lt;Back</a>
	</div>    
</body>
</html>
