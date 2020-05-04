<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Overview page</title>
</head>
<body>
	<form action="addCity">
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Country:</td>
				<td><input type="text" name="country"></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><input type="text" name="cityDescription"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Add city"></td>
			</tr>
			<tr>
				<td></td>
				<td>${message}</td>
			</tr>
		</table>
	</form>
	<h3>Upload a Airports:</h3>
	<form action="uploadAirports" method="post"
		enctype="multipart/form-data">
		<input type="file" name="file"> <input type="submit"
			value="Upload">
	</form>
	<h3>Upload a Routes:</h3>
	<form action="uploadRoutes" method="post" enctype="multipart/form-data">
		<input type="file" name="file"> <input type="submit"
			value="Upload">
	</form>
	<h4>${uploadMessage}</h4>
</body>
</html>