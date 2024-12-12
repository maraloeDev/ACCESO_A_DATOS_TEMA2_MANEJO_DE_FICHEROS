<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Error</title>
	</head>
	
	<body>
		<hr>
		
		<h1 style="color: red;text-align: center;">TIPO DE ERROR</h1>
		
		<hr>
		
		<% if (exception != null) {
			exception.printStackTrace(new PrintWriter(out));
		}%>
		
		<hr>
	</body>
</html>