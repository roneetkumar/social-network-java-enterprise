<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="tag" %> 

<%@ page import="model.*" %>  
<%@ page import="java.util.*" %>  

<%
			
	String fEmail = (String)request.getAttribute("fEmail");

	User user = (User) session.getAttribute("user");


			
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Messenger</title>
<link rel="stylesheet" href="./css/index.css">
</head>
<body>
	<div class="messages-wrapper">
		<div class="messages">

			<tag:forEach var="message" items="${user.getMessages()}">	
				<tag:if test="${fEmail == message.getToEmail()}">
					<div class="from">
						<div class="message">
							${message.getDate()}
							<br>
							<div class="value">${message.getMessage()}</div>
						</div>				
					</div>
				</tag:if>
			
			</tag:forEach>
			
			<tag:forEach var="message" items="${fMessages}">	
				<tag:if test="${user.getEmail() == message.getToEmail()}">
					<div class="to">
						<div class="message">
							${message.getDate()}
							<br>
							<div class="value">${message.getMessage()}</div>
						</div>		
					</div>
				</tag:if>	
			</tag:forEach>
		</div>
		
		<form action="SendMessage" method="POST">
			<textarea name="message">
			
			
			</textarea>
			<button type="submit" name="friend" value="${fEmail}"> Send </button>
		
		</form>
	
	</div>
	

</body>
</html>