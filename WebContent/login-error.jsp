<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/ryCrypto.css">
<link href="https://fonts.googleapis.com/css?family=Bangers|Orbitron&display=swap" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="ISO-8859-1">
<title>Login to RyCrypto</title>
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
    	<form action="ControllerServlet" method="POST" >
        <input type="hidden" name="command" value="LOGIN" />
        <h3>Invalid Username or Password</h3>	
        <input type="text" name="email" placeholder="Email Address">
        <br>
        <br>
        <input type="password" name="password" placeholder="Password">
        <br>
        <br>
        <input type="submit" class="button" value="Log In">      
        </form>
    </div>
</body>
</html>