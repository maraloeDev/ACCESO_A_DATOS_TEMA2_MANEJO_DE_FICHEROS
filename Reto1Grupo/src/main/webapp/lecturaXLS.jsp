<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Datos del archivo XLS</title>
</head>
<body>
    <h1>Datos del archivo XLS</h1>
    <%
        // Obtener los datos del atributo "datosArchivo"
        String datosArchivo = (String) request.getAttribute("datosArchivo");
        String error = (String) request.getAttribute("error");

        if (error != null) {
            // Mostrar mensaje de error si existe
    %>
            <p style="color: red;"><strong>Error:</strong> <%= error %></p>
    <%
        } else if (datosArchivo != null) {
            // Separar los datos por filas
            String[] filas = datosArchivo.split("\n");
    %>
            <table>
                <thead>
                    <tr>
                        <th>Celda</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    // Recorrer las filas y mostrarlas en la tabla
                    for (String fila : filas) {
                        if (!fila.trim().isEmpty()) {
                %>
                            <tr>
                                <td><%= fila %></td>
                            </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
    <%
        } else {
    %>
            <p>No hay datos disponibles para mostrar.</p>
    <%
        }
    %>
</body>
</html>
