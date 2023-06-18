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
  		<a href="${authorNick}/new">Add new entry</a>
  	<%}%>
  	<h1>Blog entries from user ${authorNick}</h1>
  	<c:forEach var="entry" items="${entries}">
        <li><a href="${authorNick}/${entry.id}"><c:out value="${entry.title}"/></a></li>
    </c:forEach>
  </body>
</html>