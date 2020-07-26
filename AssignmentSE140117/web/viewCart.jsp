<%-- 
    Document   : viewCart
    Created on : Jul 12, 2020, 10:12:12 PM
    Author     : Long Le
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
    </head>
    <body>
        <h1>Your cart:</h1>
	<c:set var="cart" value="${requestScope.CART}"/>
	<c:set var="itemList" value="${cart.itemList}"/>
	<c:if test="${not empty itemList}">
	    <form action="removeBook">
		<table border="1">
		    <thead>
			<tr>
			    <th>No.</th>
			    <th>Book</th>
			    <th>Quantity</th>
			    <th>Remove</th>
			</tr>
		    </thead>
		    <tbody>
			<c:forEach var="book" items="${itemList}" varStatus="counter">
			    <tr>
				<td>
				    ${counter.count}
				</td>
				<td>
				    ${book.key}
				</td>
				<td>
				    ${book.value}
				</td>
				<td>
				    <input type="checkbox" name="chkRemove" value="${book.key}" />
				</td>
			    </tr>
			</c:forEach>

			<tr>
			    <td colspan="3">
				<a href="shopping">Buy more book</a>
			    </td>
			    <td>
				<input type="submit" value="Remove" name="btAction" />
			    </td>
			</tr>
		    </tbody>
		</table>
	    </form>
	    <a href="confirmCart">Click here to check out the cart</a>
	</c:if>

	<c:if test="${empty itemList}">
	    <h2 style="color:red">Your cart is empty!!!</h2>
	    <a href="shopping">Back to shopping page</a>
	</c:if>
    </body>
</html>
