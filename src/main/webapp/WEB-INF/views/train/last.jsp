<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Last Train</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
</head>
<body>
	<div id="page">
	    <h1>Last Train from ${station}</h1>
	    <h2>On the ${line} line</h2>
	    <p>Is at: ${lasttrain}</p>
	    <a href="/lasttrain/${line}">Back to ${line} line</a>
    </div>
</body>
</html>
