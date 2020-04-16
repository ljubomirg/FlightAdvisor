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
			value="${comment.commentDescription}"> <input type="hidden"
			name="id" value="${comment.id}"> <input type="submit"
			name="button" value="${button}">
	</form>

</body>
</html>