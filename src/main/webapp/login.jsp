<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login page</title>
</head>
<body>
	<h2>Login page</h2>
	<form action="checkLogin">
		User name: <input type="text" name="userName" /><br /> Password: <input
			type="password" name="password" /><br /> <input type="submit"
			value="Login" /><br />
	</form>
	${message}
	<br />
	<br />
	<h2>Sign up</h2>
	<form action="register">
		First name: <input type="text" name="firstName" /><br /> Last name:
		<input type="text" name="lastName" /><br /> User name: <input
			type="text" name="userName" /><br /> Password: <input type="text"
			name="password" /> <br /> <input type="submit" value="Sign up" /><br />
	</form>
	${registerMessage}

</body>
</html>