<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="tag" %> 

<%@ page import="model.*" %>  

<% 
	User user = (User) session.getAttribute("user"); 

	if(user == null){
		response.sendRedirect("Logout");
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile</title>
<link rel="stylesheet" href="./css/index.css">
<script defer src="./script/index.js"></script>
</head>
<body>
	<!-- error -->
	<span style="display:none" class="error">${msg}</span>
	<tag:if test="${msg != null}">
		<script>	
				document.querySelector('.error').style.display = 'block';						
				setTimeout(function() {
					document.querySelector('.error').style.display = 'none';
				}, 2000);
		</script>
	</tag:if>	
	
	<header>
		<h1 class="main-heading">Home Page</h1>
		
		<div class="search-bar">
			<input type="text" name="search" placeholder="Make Some Friends" autocomplete="off">			
		</div>
		<div class="overlay">
			<div class="users">
				<tag:forEach var="temp" items="${allUsers}">
					<tag:if test="${temp.getEmail() != user.getEmail()}">
						<div class="user"> 
							<h1>${temp.getFname()} ${temp.getLname()}<br></h1>
							<form action="FriendOperation">
								<button type="submit" name="send" value="${temp.getEmail()}">Send</button>
								<button type="submit" name="view" value="${temp.getEmail()}">View</button>
							</form>
						 </div>
					</tag:if>
				</tag:forEach>
			</div>
		</div>
		
		<a href="index.jsp">Logout</a>
	</header>
	<nav>
		<a class="nav-link" href="Profile">Profile</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="nav-link" href="#">Settings</a>
	</nav>
	<br><br>
	
	<form action="CreatePost" method="POST" class="home-form">
		<h2 class="sub-heading">Create Post</h2>
		<br>
		<textarea name="postContent" placeholder="Whats on your mind ?"></textarea><br><br>
		<input type="file" name="postImage"><br><br>
		<input type="hidden" value="Home" name="page">
		<button type="submit" name="createPost">Create</button>	
		<button type="reset">Clear</button>	
		<br>
		<br>	
	</form>
	<br><br>
	<tag:if test="${allPosts.size() > 0}">
		<h1 class="sub-heading">All Posts</h1>
	</tag:if>
	<tag:if test="${allPosts.size() <= 0}">
		<h1 class="sub-heading">No Posts Available</h1>
	</tag:if>
	<br>
	<div class="posts">
		<tag:forEach var="post" items="${allPosts}">
			<div class="post">
				<span>${post.getUsername()}<br></span>
				<span>${post.getDate()}<br></span>
				<p>${post.getContent()}<br></p>
				<img src="${post.getImage()}" alt=""/>
				<form action="PostOperations" method="GET">
					<tag:if test="${post.getEmail() == user.getEmail()}">
						<button type="submit" value="${post.getPostId()}" name="edit">Edit</button>
					</tag:if>	
					<button type="submit" value="${post.getPostId()}" name="like" >Like ${post.getLikes()}</button>
					<tag:if test="${post.getEmail() != user.getEmail()}">
						<button type="submit" value="${post.getPostId()}" name="save">Save</button>
					</tag:if>
					<tag:if test="${post.getEmail() == user.getEmail()}">
						<button type="submit" value="${post.getPostId()}" name="del" >Delete</button>
					</tag:if>
					<input type="hidden" value="Home" name="page">
				</form>
			</div>
		</tag:forEach>
	</div>
</body>
</html>