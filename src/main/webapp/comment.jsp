<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="comment">
		<input type="text" name="commentDescription"
			value="${description}"> 
			<input type="hidden" name="id" value="${id}"> 
			<input type="hidden" name="cityName" value="${cityName}"> 
			<input type="submit"
			name="button" value="${button}">
	</form>

</body>
</html>