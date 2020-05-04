<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Overview page</title>
</head>
<body>
	<form action="getAllCities">
		Comment number: <input type="text" name="commentsLimit"><br>
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
				<td><input type="text" name="commentsLimit"></td>
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

	<h3>Choose a trip:</h3>
	<form action="getRoute">
		Insert source city: <input type="text" name="sourceCity">
		Insert destination city: <input type="text" name="destinationCity">
		<input type="submit" value="Find rout">
		<div>${messageRoute}</div>
	</form>
	<br />
	<br />
	<c:if test="${not empty travel}">
		<h3>
			<p>
				Source city:
				<c:out value="${travel.sourceCity.name}" />
			</p>
			<p>
				Destination city:
				<c:out value="${travel.destinationCity.name}" />
			</p>
		</h3>
		<table>
			<tr>
				<th>Cities name</th>
				<th>Total price</th>
				<th>Length in km</th>
			</tr>

			<c:forEach items="${travel.travelRoutes}" var="travelRoutes">
				<tr>
					<td><c:out value="${travelRoutes.citiesName}" /></td>
					<td><c:out value="${travelRoutes.totalPrice}" /></td>
					<td><c:out value="${travelRoutes.length}" /></td>
				</tr>
			</c:forEach>

		</table>
	</c:if>

</body>
</html>