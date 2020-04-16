<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Overview page</title>
</head>
<body>
	<form action="getAllCities">
		Comment number: <input type="text" name="commentNumber"><br>
		<input type="submit" value="Get all cities">
	</form>
	<form action="getCityByName">
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Comment number:</td>
				<td><input type="text" name="commentNumber"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Find city"></td>
			</tr>
			<tr>
				<td></td>
				<td>${message}</td>
			</tr>
		</table>
	</form>
</body>
</html>