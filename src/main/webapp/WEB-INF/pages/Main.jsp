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
  		.error-msg{
  			color: red;
  		}
  	</style>
  </head>
  <body>
  
  	<%if(session.getAttribute("current.user.id") == null) {%>
		<h1 class="error-msg">${message}</h1>
	  	<div class="centered-div">
		  	<form action="main" method="post">
		  		<label for="nick">Nick:</label>
		  		<input type="text" id="nick" name="nick" value="${nick}"><br><br>
		  		<label for="pass">Password:</label>
		  		<input type="password" id="pass" name="pass"><br><br>
		  		<input type="submit" value="Login">
		  	</form>
		  	<br><br>
		  	<a href="register">No account? Register here.</a>
		 </div>
	 <%} else{%>
	 	<h1>Logged in as <%= session.getAttribute("current.user.fn") %> <%= session.getAttribute("current.user.ln") %> (<%= session.getAttribute("current.user.nick") %>)</h1>
	 	<a href="logout">Logout</a>
	 	<hr style="width:100%;text-align:left;">
	 <% } %>
	 <br><br><br>
	 
	 <c:forEach var="user" items="${autori}">
        <li><a href="author/${user.nick}"><c:out value="${user.firstName} ${user.lastName} (${user.nick})"/></a></li>
      </c:forEach>
  
  </body>
</html>