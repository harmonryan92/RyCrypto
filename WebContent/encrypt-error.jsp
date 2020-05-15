<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/ryCrypto.css">
<link href="https://fonts.googleapis.com/css?family=Bangers|Orbitron&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="ISO-8859-1">
<title>RyCrypto Encryption Page</title>
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
		<form action="ControllerServlet" method="POST">		
			<input type="hidden" name="command" value="ENCRYPT" />
			<h3>Enter the path of the file to encrypt:</h3>
			<input type="text" name="filePath" placeholder="File Path">
			<h3>Create a 16-character (spaces included) password for encryption. This will also be used for decryption. </h3>
			<input type="password" name="key" id="key" placeholder="Encryption Key">
			<h3>Confirm your password:</h3>
			<input type="password" name="key2" id=key2" placeholder="Confirm Encryption Key">
			<h3>Encryption keys did not match.</h3>
			<br>
			<br>
			<input type="submit" class="button" value="Encrypt">			
		</form>
	</div>
</body>
</html>