<%-- 
    Document   : onlineStore
    Created on : Jul 12, 2020, 8:56:03 PM
    Author     : Long Le
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bookstore</title>
    </head>
    <body>
        <h1>Online bookstore</h1>
	<c:set var="bookList" value="${requestScope.BOOK_LIST}"/>
	<form action="addToCart">
	    Choose a book: <select name="cbBook">
		<c:forEach var="book" items="${bookList}">
		    <option>${book}</option>
		</c:forEach>
	    </select>
	    <input type="submit" value="Add to cart" name="btAction" />
	</form>
	<form action="viewCart">
	    <input type="submit" value="View cart" name="btAction" />
	</form>
	<a href="loginPage">Back to login page</a>
    </body>
</html>
