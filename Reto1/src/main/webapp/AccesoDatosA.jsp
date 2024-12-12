<%@page import="com.google.gson.JsonElement"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/Error.jsp"%>
<!DOCTYPE html>

<%
	String eleccion = (String) request.getAttribute("eleccion");
	List<String[]> data = (List<String[]>) request.getAttribute("data");
%>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Datos</title>
	</head>
	
	<body>
    <h1>DATOS</h1>
    
    	<%if("CSV".equals(eleccion)){ %>
	    	<%
			    List<String[]> d = (List<String[]>) request.getAttribute("data");
			    if (d != null && !d.isEmpty()) {
			%>
	    		<table border="1">
			        <tr>
			            <% for (String cell : d.get(0)) { %>
			                <th><%= cell %></th>
			            <% } %>
			        </tr>
			        <% for (int i = 1; i < d.size(); i++) { %>
			            <tr>
			                <% for (String cell : d.get(i)) { %>
			                    <td><%= cell %></td>
			                <% } %>
			            </tr>
			        <% } %>
			    </table>
	    	<%} %>
    	<%} %>
    	
    	<%if("XLS".equals(eleccion)){ %>
	    	<table border="1">
		        <%
		            List<List<String>> datos = (List<List<String>>) request.getAttribute("datosArchivo");
		            if (datos != null) {
		                for (List<String> fila : datos) {
		                    out.println("<tr>");
		                    for (String celda : fila) {
		                        out.println("<td>" + celda + "</td>");
		                    }
		                    out.println("</tr>");
		                }
		            } else {
		                out.println("<tr><td>No hay datos disponibles</td></tr>");
		            }
		        %>
	    	</table>
    	<%} %>
    	
    	<%if("RDF".equals(eleccion)){ %>
	    	<table border="1">
		        <tr>
		            <th>Subject</th>
		            <th>Predicate</th>
		            <th>Object</th>
		        </tr>
		        <%
		            List<String[]> dataList = (List<String[]>) request.getAttribute("dataList");
		            if (dataList != null) {
		                for (String[] d : dataList) {
		        %>
		        <tr>
		            <td><%= d[0] %></td>
		            <td><%= d[1] %></td>
		            <td><%= d[2] %></td>
		        </tr>
		        <%
		                }
		            }
		        %>
		    </table>
    	<%} %>
    	
    	<%if("XML".equals(eleccion)){ %>
    		<table border="1">
		        <tr>
		            <%
		                ArrayList<ArrayList<String>> d = (ArrayList<ArrayList<String>>) request.getAttribute("data");
		                if (d != null && !d.isEmpty()) {
		                    for (String header : d.get(0)) {
		                        out.print("<th>" + header + "</th>");
		                    }
		                }
		            %>
		        </tr>
		        <%
		            if (d != null && !d.isEmpty()) {
		                for (int i = 1; i < d.size(); i++) {
		                    out.print("<tr>");
		                    for (String value : d.get(i)) {
		                        out.print("<td>" + value + "</td>");
		                    }
		                    out.print("</tr>");
		                }
		            }
		        %>
		    </table>
    	<%} %>
    	
    	<%if("JSON".equals(eleccion)){ %>
    		<table border="1">
		        <tr>
		            <%
		                JsonObject jsonData = (JsonObject) request.getAttribute("jsonData");
		                if (jsonData != null) {
		                    for (String key : jsonData.keySet()) {
		                        JsonElement value = jsonData.get(key);
		            %>
		            <th><%= value.getAsString() %></th>
		            <%
		                    }
		                }
		            %>
		        </tr>
		        <tr>
		            <%
		                if (jsonData != null) {
		                    for (String key : jsonData.keySet()) {
		                        JsonElement value = jsonData.get(key);
		            %>
		            <td><%= key %></td>
		            <%
		                    }
		                }
		            %>
		        </tr>
		    </table>
    	<%} %>
    
	</body>
</html>