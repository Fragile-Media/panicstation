<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Pizza</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
</head>
<body>
	<div id="page">
	    <h1>List of all Pizzas</h1>
	    <ul class="pizzas">
	        <c:forEach var="p" items="${pizzas}">
	            <li>
	            	<span class="pizza-item">${p.id}. <a href="./pizza/${p.id}">${p.name}</a></span>
	            	<span class="pizza-item-price"> - &pound;${p.price}</span>
	            	<span class="pizza-item-controls">[<a href="./pizza/${p.id}/edit">Edit</a>]</span>
	            </li>
	        </c:forEach>
            <li>
            	<span class=""><a href="./pizza/add">Add new</a></span>
            </li>
	    </ul>
    </div>
</body>
</html>
