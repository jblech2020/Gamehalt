<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>AIT Library</title>
    
    <style type="text/css">
      <%@ include file="css/styles.css" %>
    </style>
  </head>
  <body>
    <div>
      <h1>Inventory Management</h1>
      
      <div class="header">
        <a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
        <a href="${pageContext.request.contextPath}/add" class="header-button">ADD A GAME</a> 
      </div>
    </div>
    <div>
      <table border="1">        
        <tr>
          <th>Title</th>
          <th>Author</th>
          <th>Copies</th>
          <th>Available</th>
          <th>Actions</th>
        </tr>
        <c:forEach var="book" items="${games}">
          <tr>
            <td><c:out value="${game.title}" /></td>
            <td><c:out value="${game.author}" /></td>
            <td><c:out value="${game.copies}" /></td>
            <td><c:out value="${game.available}" /></td>
            <td>
	      <div>
	        <a href="${pageContext.request.contextPath}/update?action=rent&id=${game.id}"
            	   class="button"
                >
              	  RENT
              	</a>
              	<a href="${pageContext.request.contextPath}/update?action=return&id=${game.id}"
              	   class="button"
              	>
              	  RETURN
              	</a>
              	<a href="${pageContext.request.contextPath}/edit?id=${game.id}"
              	   class="button"
              	>
              	  EDIT
              	</a>
              </div>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </body>
</html>