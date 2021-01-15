<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer List</title>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
	<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
			<p>
				User: <security:authentication property="principal.username"/>, Role: <security:authentication property="principal.authorities"/>
			</p>
			<security:authorize access="hasAnyRole('MANAGER','ADMIN')">
				
				<input type="button" value="Add Customer"
					   onclick="window.location.href='showFormForAdd'; return false;"
					   class="add-button"
				/>
				
			
			</security:authorize>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					
					<security:authorize access="hasAnyRole('MANAGER','ADMIN')">
						
						<th>Action</th>
					
					</security:authorize>
			
				</tr>
				
				<!-- loop over customers -->
				<c:forEach var="customer" items="${customers}">
				
				<c:url var="updateLink" value="/customer/updateCustomer">
					<c:param name="customerId" value="${customer.id}"/>
				</c:url>
				
				<c:url var="deleteLink" value="/customer/delete">
					<c:param name="customerId" value="${customer.id}"/>
				</c:url>
				
				<tr>
					<td>${customer.firstName}</td>
					<td>${customer.lastName}</td>
					<td>${customer.email}</td>
					
					<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
						
							<td>
								<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
									<!-- display the update link -->
									<a href="${updateLink}">Update</a>
								</security:authorize>
	
								<security:authorize access="hasAnyRole('ADMIN')">
									<a href="${deleteLink}"
									   onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
								</security:authorize>
							</td>

						</security:authorize>
					
					
					
				</tr>
				
				</c:forEach>
				
			
			</table>
			
		</div>
	</div>
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" 
			   method="POST">
	
		<input type="submit" value="Logout" class="add-button" />
	
	</form:form>

	</body>
</html>