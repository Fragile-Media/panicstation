<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Train</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
</head>
<body>
	<div id="page">
	    <h1>Train Pattern</h1>
	    <div>${pdf}</div>
	    <table class="train">
            <tbody>
	        <c:forEach var="t" items="${traintimes}">
	            <tr>
	            	<td class="">${t.id}</td>
	            	<td class="">${t.name}</td>
	            	<td class="">${t.date}</td>
	            </tr>
	        </c:forEach>
            </tbody>
	    </table>
    </div>
</body>
</html>
