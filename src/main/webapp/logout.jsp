

<!DOCTYPE html>



<html>
<head>
</head>
<body>


	<%
	String errorMessage = (String) request.getAttribute("errorMessage");
	%>
	<form action="LogoutServlet" method="post">
		<%@ page import="javax.servlet.http.*"%>
		<%
		// invalidate session and redirect to login page
		HttpSession session1 = request.getSession(false);
		if (session1 != null) {
			session.invalidate();
		}
		response.sendRedirect("index.jsp");
		%>

		<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
			pageEncoding="ISO-8859-1"%>
	</form>

</body>
</html>
