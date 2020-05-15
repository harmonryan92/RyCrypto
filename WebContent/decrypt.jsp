<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/ryCrypto.css">
<link href="https://fonts.googleapis.com/css?family=Bangers|Orbitron&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="ISO-8859-1">
<title>RyCrypto Decryption Page</title>
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
		<a href=home.jsp>Home</a>
		<a href="logout.jsp">Log Out</a>
	</div>
	</div>
	
	<div class="main">
		<form action="ControllerServlet" method="POST">
			<input type="hidden" name="command" value="DECRYPT" />
			<br>
			<h3>Enter the path of the file to decrypt:</h3>
			<input type="text" name="filePath" placeholder="File Path">
			<h3>Enter your encryption/decryption password: </h3>
			<input type="password" name="key" placeholder="Decryption Key">
			<br>
			<br>
			<input type="submit" class="button" value="Decrypt">
		</form>
	</div>
</body>
</html>