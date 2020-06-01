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
      <c:if test="${game != null}">
        <h2>Edit Game</h2>
        <form action="update" method="post">
          <input type="hidden" name="id" value="<c:out value="${game.id}" />" />
          
          <label>
            Title
            <input type="text" name="title" value="<c:out value="${game.title}" />" />
          </label>
          <label>
            Author
            <input type="text" name="author" value="<c:out value="${game.author}" />" />
          </label>
          <label>
            Number of Copies
            <select name="copies">
              <c:forEach begin="1" end="15" varStatus="loop">
                <option value="${loop.index}"
                  <c:if test="${game.copies == loop.index}">selected</c:if>
                >
                  ${loop.index}
                </option>
              </c:forEach>
            </select>
          </label>
          <div class="form-actions">
            <input type="submit" value="SAVE" name="submit" class="add-button" />
            <input type="submit" value="DELETE" name="submit" class="add-button" />
          </div>
        </form>
      </c:if>
      <c:if test="${game == null}">
        <h2>Add Game</h2>
        <form action="insert" method="post">
          <input type="hidden" name="id" />
          <label>
            Title
            <input type="text" name="title" />
          </label>
          <label>
            Author
            <input type="text" name="author" />
          </label>
          <label>
            Number of Copies
            <input type="text" name="copies" />
          </label>
          <input type="submit" value="ADD" name="submit" class="add-button" />
        </form>
      </c:if>
    </div>
  </body>
</html>