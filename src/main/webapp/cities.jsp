<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	All cities:
	<table>
		<c:forEach items="${cities}" var="city">
			<tr>
				<td><p></p></td>
			</tr>
			<tr>
				<td>City:</td>
			</tr>
			<tr>
				<td>Name: <c:out value="${city.name}" /></td>
				<td>Country: <c:out value="${city.country}" /></td>
				<td>Description: <c:out value="${city.cityDescription}" /></td>
			</tr>
			<tr>
				<td>Comments:</td>
				<td>
					<form action="addComment">
						<input type="hidden" value="${city.name}" name="cityName">
						<input type="submit" value="Add comment">
					</form>
				</td>
			</tr>
			<c:forEach items="${city.comment}" var="comment">
				<tr>
					<td><c:out value="${comment.commentDescription}" /></td>
					<td><c:out value="${comment.createdDate}" /></td>
					<td><c:out value="${comment.modifiedDate}" /></td>
				</tr>
				<tr>
					<td>
						<form action="deleteComment">
							<input type="hidden" value="${comment.id}" name="id"> <input
								type="submit" value="Delete comment">
						</form>
					</td>
					<td>
						<form action="updateComment">
							<input type="hidden" value="${comment.id}" name="id"> <input
								type="submit" value="Update comment">
						</form>
					</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>
	<table>
	</table>

</body>
</html>