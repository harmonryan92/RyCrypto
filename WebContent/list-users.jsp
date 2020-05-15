<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>RyCrypto Users</title>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>RyCrypto Users</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">
				<a href="add-user-form.jsp">Register</a>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>	
				</tr>
				<c:forEach var="tempUser" items="${USER_LIST}">
				
				<!-- set up a link for each user -->
					<c:url var="tempLink" value="ControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="id" value="${tempUser.id}" />
					</c:url>
					
				<!--  set up a link to delete a user -->
					<c:url var="deleteLink" value="ControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="id" value="${tempUser.id}" />
					</c:url>
					
					<tr>
						<td> ${tempUser.firstName} </td>
						<td> ${tempUser.lastName} </td>
						<td> ${tempUser.email} </td>
						<td>
							<a href="${tempLink}">Update</a>
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this user?'))) return false">
							Delete</a>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>