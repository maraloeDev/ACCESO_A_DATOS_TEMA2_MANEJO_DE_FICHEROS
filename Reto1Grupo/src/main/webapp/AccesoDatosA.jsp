<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/Error.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Datos</title>
	</head>
	
	<body>
    <h1>DATOS</h1>
    
    <%
        List<String[]> data = (List<String[]>) request.getAttribute("data");
        if (data != null && !data.isEmpty()) { 
    %>
        <table border="1">
        
            <tr>
                <% for (String cell : data.get(0)) { %>
                    <th><%= cell %></th>
                <% } %>
            </tr>
            
            <% for (int i = 1; i < data.size(); i++) { %>
                <tr>
                    <% for (String cell : data.get(i)) { %>
                        <td><%= cell %></td>
                    <% } %>
                </tr>
            <% } %>
        </table>
    <%
        }
    %>
    
</body>


</html>