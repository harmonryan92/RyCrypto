<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/ryCrypto.css">
<link href="https://fonts.googleapis.com/css?family=Bangers|Orbitron&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="ISO-8859-1">
<title>RyCrypto Registration Page</title>
</head>
<body>

	<div class="header">
		<a class="logo" href="index.jsp">RyCrypto</a>
	<div class="header-right">
		<a href=home.jsp>Home</a>
		<a href="login.jsp">Log In</a>
		<a class="active" href="add-user-form.jsp">Register</a>
	</div>
	</div>
	
	<div class="main">
    	<form action="ControllerServlet" method="POST" class="bg-light p-5 contact-form">
        	<input type="hidden" name="command" value="ADD" />
           	<br>
            <input type="text" name="firstName" placeholder="First Name">
            <br>                         
            <input type="text" name="lastName" placeholder="Last Name">
            <br>
            <input type="text" name="email" placeholder="Email">
            <br>
            <input type="password" name="password" placeholder="Password">
            <br>
            <input type="password" name="password2" placeholder="Confirm Password">
            <br>
            <h3>Passwords did not match.</h3>
            <br>
            <br>
            <input type="submit"  class="button" value="Register">             
        </form>
	</div>
</body>
</html>