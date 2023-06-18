<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

  <head>
  	<style>
  		body{
  			display: flex;
  			flex-direction: column;
  			justify-content: center;
  			align-items: center;
  		}
  		.centered-div{
  			display: flex;
  			flex-direction: column;
  			justify-content: center;
  			align-items: center;
  			border: 2px solid black;
  			padding: 5px;
  		}
  	</style>
  </head>
  <body>
  	<a href="/blog">Home</a>
  	<%if(session.getAttribute("current.user.id") != null) {%>
		<h1>Logged in as <%= session.getAttribute("current.user.fn") %> <%= session.getAttribute("current.user.ln") %> (<%= session.getAttribute("current.user.nick") %>)</h1>
	 	<a href="/blog/servleti/logout">Logout</a>
	 <%} else {%>
	 	<h1>Not logged in.</h1>
	 <% } %>
	 <hr style="width:100%;text-align:left;">
	 
	 <br><br><br>
	 
	 <% if(session.getAttribute("current.user.nick") != null && session.getAttribute("current.user.nick").equals(request.getAttribute("authorNick"))){ %>
  		<a href="edit?entryId=${entryId}">Edit entry</a>
  	<%}%>
  	
  	<% 
  	String authorNick = (String) request.getAttribute("authorNick");
  	BlogEntry entry = (BlogEntry)request.getAttribute("entry");
  	if(entry != null && entry.getCreator() != null && !authorNick.equals(entry.getCreator().getNick().toString())){ %>
  		<h1> Taj entry nije od korisnika ${authorNick}</h1>
  	<%} else {%>
  	
  		<h1> Post from: ${authorNick}</h1>
	 	<div class="centered-div">
		  	<h1>${entry.title}</h1>
		  	<br><br>
		  	<h3>${entry.text}</h3>
		 </div>
		 <br> <br>
	
		<% if(session.getAttribute("current.user.nick") != null){ %>
	  		<div class="centered-div">
			  	<form action="/blog/servleti/addComment?entryId=${entryId}" method="post">
			  		<label for="message">Comment:</label>
			  		<textarea id="message" name="message" rows="3"></textarea>
			  		<input type="submit" value="Comment">
			  		<br><br>
			  	</form>
			  	<br><br>
		 	</div>
	  	<%} else{ %>
	  		<h1>Login to comment.</h1>
	  		<br><br>
	  	<% } %>
		
		<c:forEach var="comment" items="${comments}">
	        <h2>${comment.usersEMail}: ${comment.message} </h2>
	        <br>
	        <hr style="width:50%;text-align:left;">
	        <br>
	      </c:forEach>
	      
	<% } %>
	 
  </body>
</html>