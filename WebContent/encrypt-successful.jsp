<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/ryCrypto.css">
<link href="https://fonts.googleapis.com/css?family=Bangers|Orbitron&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="ISO-8859-1">
<title>Encryption Successful!</title>
</head>
<body>
	<%
		if(session.getAttribute("email")==null)
		{
			response.sendRedirect("login.jsp");
		}
	%>

	<div class="header">
		<a class="logo" href="index.jsp">RyCrypto</a>
	<div class="header-right">
		<a class="active" href=home.jsp>Home</a>
		<a href="logout.jsp">Log Out</a>
	</div>
	</div>
	
	<div class="main">
		<h1>The encryption was successful! Check the original folder for the results.</h1>
		<a href="home.jsp">Back to Home</a>
	</div>
	
</body>
</html>