<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/Error.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Formulario</title>
		<style type="text/css">
			body{
				text-align: center;
				justify-content: center;
				align-items: center;
				display: flex;
				flex-direction: column;
			}
		</style>
	</head>
	<body>
		<h1>TRATAMIENTO DE FICHEROS</h1>
		<form action="ServletFich" method="post">
			<table>
				<tr>
					<td>
						Fromato de fichero: 
						<select name="fileTypes">
							<option name="fileTypes" value="XLS">XLS</option>
							<option name="fileTypes" value="CSV">CSV</option>
							<option name="fileTypes" value="JSON">JSON</option>
							<option name="fileTypes" value="XML">XML</option>
							<option name="fileTypes" value="RDF">RDF</option>
						</select>
						<hr>
						<p>¿Que quiere hacer con el fichero?</p>
						
							Lectura: <input type="radio" name="readingWriting" value = "lectura" checked="checked"><br>
							Escritura:<input type="radio" name="readingWriting" value = "escritura">
					</td>
					<td>
						<table>
							<tr>
								<td>DATO1:</td>
								<td><input type="text" name = "fact1"></td>
							</tr>
							<tr>
								<td>DATO2:</td>
								<td><input type="text" name = "fact2"></td>
							</tr>
							<tr>
								<td>DATO3:</td>
								<td><input type="text" name = "fact3"></td>
							</tr>
							<tr>
								<td>DATO4:</td>
								<td><input type="text" name = "fact4"></td>
							</tr>
							<tr>
								<td>DATO5:</td>
								<td><textarea rows="2" cols="21" name = "fact5"></textarea></td>
							</tr>
							<tr>
								<td>DATO6:</td>
								<td><input type="text" name = "fact6"></td>
							</tr>
						</table>
						<%if(request.getAttribute("emptyError") != null){ %>
							<p style="color: red"><%=request.getAttribute("emptyError") %></p>
						<%} %>
					</td>
				</tr>
			</table>
			<input type="submit" name="button" value="Enviar">
		</form>
	</body>
</html>