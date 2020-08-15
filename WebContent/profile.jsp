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
		<h1 class="main-heading">Welcome to ${user.getFname()}'s Profile</h1>
		<div></div>
		<a href="Logout">Logout</a>
	</header>
	<nav>
		<a class="nav-link" href="Home">Home</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="nav-link" href="#">Settings</a>
	</nav>
	<br><br>
	

	<!-- FRIENDS -->
	<div class="friends">
		<tag:if test="${user.getFriends().size() > 0}">
			<h1 class="sub-heading">Friends</h1>
		</tag:if>
		<tag:if test="${user.getFriends().size() <= 0}">
			<h1 class="sub-heading">No Friends</h1>
		</tag:if>
		<br>
		<tag:forEach var="friend" items="${user.getFriends()}">
			<div class="friend">
				<h1>${friend.getFname()} ${friend.getLname()}</h1>
				<form action="FriendOperation" method="GET">
					<button type="submit" value="${friend.getEmail()}" name="view">View</button>
					<button type="submit" value="${friend.getEmail()}" name="message">Message</button>
					<button type="submit" value="${friend.getEmail()}" name="remove">Remove</button>
				</form>
			</div>
		</tag:forEach>
	</div>
	
	
	<div class="main-grid">
		<form action="CreatePost" method="POST" class="profile-form">
			<h2 class="sub-heading">Create Post</h2>
			<br>
			<textarea name="postContent" placeholder="Whats on your mind ?"></textarea><br><br>
			<input type="file" name="postImage"><br><br>
			<input type="hidden" value="Profile" name="page">
			<button type="submit" name="createPost">Create</button>	
			<button type="reset">Clear</button>	
			<br>
			<br>		
		</form>

		<!-- POSTS -->
		<div class="posts">
			<tag:if test="${user.getPosts().size() > 0}">
				<h1 class="sub-heading">My Posts</h1>
			</tag:if>
			<tag:if test="${user.getPosts().size() <= 0}">
				<h1 class="sub-heading">No Posts Available</h1>
			</tag:if>
			<br>
			<tag:forEach var="post" items="${user.getPosts()}">			
				<div class="post">
					<span>${post.getUsername()}<br></span>
					<span>${post.getDate()}<br></span>
					<p>${post.getContent()}<br></p>
					<img src="${post.getImage()}" alt=""/>
					<form action="PostOperations" method="GET">
					<button type="submit" value="${post.getPostId()}" name="edit">Edit</button>
						<button type="submit" value="${post.getPostId()}" name="like">Like ${post.getLikes()}</button>
						<button type="submit" value="${post.getPostId()}" name="del">Delete</button>
						<input type="hidden" value="Profile" name="page">
					</form>
				</div>
			</tag:forEach>
		</div>
	
		<br><br><br><br>
			
		<!-- SAVED POSTS -->
		<div class="posts">
			<tag:if test="${user.getPosts().size() > 0}">
				<h1 class="sub-heading">Saved Posts</h1>
			</tag:if>
			<tag:if test="${user.getPosts().size() <= 0}">
				<h1 class="sub-heading">No Posts Available</h1>
			</tag:if>
			<br>
			<tag:forEach var="post" items="${user.getSavedPosts()}">			
				<div class="post">
					<span>${post.getUsername()}<br></span>
					<span>${post.getDate()}<br></span>
					<p>${post.getContent()}<br></p>
					<img src="${post.getImage()}" alt=""/>
					<form action="PostOperations" method="GET">
						<button type="submit" value="${post.getPostId()}" name="like">Like ${post.getLikes()}</button>
						<button type="submit" value="${post.getPostId()}" name="removeSaved">Remove</button>
						<input type="hidden" value="Profile" name="page">
					</form>
				</div>
			</tag:forEach>
		</div>
	</div>
		
	<div class="noti">
		<tag:if test="${user.getRequests().size() > 0}">
			<h1 class="sub-heading">Notifications</h1>
		</tag:if>
		<tag:if test="${user.getRequests().size() <= 0}">
			<h1 class="sub-heading">No Notifications</h1>
		</tag:if>
		<br>
		<tag:forEach var="friend" items="${user.getRequests()}">
			<div class="friend">
				<h1>${friend.getFname()} ${friend.getLname()}</h1>
				<form action="FriendOperation" method="GET">
					<button type="submit" value="${friend.getEmail()}" name="accept">Accept</button>
					<button type="submit" value="${friend.getEmail()}" name="reject">Reject</button>
				</form>
			</div>
		</tag:forEach>
	</div>
</body>
</html>