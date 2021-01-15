<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Login</title>
</head>
<body>
	<form:form action="${pageContext.request.contextPath}/authenticateTheUser"
		method="POST">
		
		<c:if test="${param.error!= null }">
			<i>OOPS! Invalid login credentials :(</i>
		</c:if>

		<p>
			User Name: <input type="text" name="username" />
		</p>

		<p>
			Password: <input type="password" name="password" />
		</p>

		<input type="submit" value="Login">



	</form:form>

</body>
</html>