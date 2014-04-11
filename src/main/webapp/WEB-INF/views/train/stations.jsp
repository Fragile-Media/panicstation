<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Stations </title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
</head>
<body>
	<div id="page">
	    <h1>Last Trains on ${line} line</h1>
	    <ul class="stations">
	        <c:forEach var="station" items="${stations}">
	            <li>
	            	<a href="/lasttrain/${line}/${station.rawName}">${station.name}</td>
	            </li>
	        </c:forEach>
	    </ul>
    </div>
</body>
</html>
