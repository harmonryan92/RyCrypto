<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/ryCrypto.css">
<link href="https://fonts.googleapis.com/css?family=Bangers|Orbitron&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="ISO-8859-1">
<title>Update a User</title>
</head>
<body>
	<div class="header">
		<a class="logo" href="index.jsp">RyCrypto</a>
	<div class="header-right">
		<a href=home.jsp>Home</a>
		<a href="logout.jsp">Log Out</a>
	</div>
	</div>
		<div class="main">
			<form action="ControllerServlet" method="POST">
				<input type="hidden" name="command" value="UPDATE" />
				<input type="hidden" name="id" value="${THE_USER.id}" />
				<br>
				<input type="text" name="firstName" value="${THE_USER.firstName}" />
				<br>
				<input type="text" name="lastName" value="${THE_USER.lastName}" />
				<br>
				<input type="text" name="email" value="${THE_USER.email}" />
				<br>
				<br>
				<input type="submit" value="Save" class="save" />
			</form>
		</div>
		<div style="clear: both;"></div>
</body>
</html>