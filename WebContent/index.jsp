<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/ryCrypto.css">
<link href="https://fonts.googleapis.com/css?family=Bangers|Orbitron&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="ISO-8859-1">
<title>Welcome to RyCrypto</title>
</head>
<body>

	<%
		if(session.getAttribute("email")!=null)
		{
			response.sendRedirect("home.jsp");
		}
	%>
	
	<div class="header">
		<a class="logo" href="index.jsp">RyCrypto</a>
	<div class="header-right">
		<a class="active" href=home.jsp>Home</a>
		<a href="login.jsp">Log In</a>
		<a href="add-user-form.jsp">Register</a>
	</div>
	</div>
	
	
	<div class="main">
	<h1>Welcome! Please log in or register to continue.</h1>
	<a href="login.jsp" class="button">Log In</a>
	<a href="add-user-form.jsp" class="button">Register</a>
	</div>
	
</body>
</html>