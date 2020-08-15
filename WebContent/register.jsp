<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="tag" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register to Social Spark</title>
<link rel="stylesheet" href="./css/index.css">
</head>

<body>

	<!-- error -->
	<span style="display:none" class="error">${error}</span>
	
	<tag:if test="${error != null}">
		<script>	
				document.querySelector('.error').style.display = 'block';						
				setTimeout(function() {
					document.querySelector('.error').style.display = 'none';
				}, 2000);
		</script>
	</tag:if>
	

	<header>
		<h1 class="main-heading">Register</h1>
	</header>
	<br><br><br>
	<form action="Register" class="form" method="POST">
		<input type="text" name="fname" placeholder="First Name"><br><br>
		<input type="text" name="lname" placeholder="Last Name"><br><br>
		<input type="text" name="email" placeholder="Email"><br><br>
		<input type="text" name="pass" placeholder="Password"><br><br>
		<button type="submit">Register</button> &nbsp; <button type="reset">Clear</button><br><br>
		<a href="index.jsp">Back</a>
		<br><br>
	</form>

</body>
</html>