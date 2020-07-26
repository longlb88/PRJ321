<%-- 
    Document   : checkout
    Created on : Jul 13, 2020, 11:43:26 AM
    Author     : Long Le
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Cart</title>
    </head>
    <body>
        <h1>Checkout cart</h1>
	<c:set var="cart" value="${requestScope.CART}"/>
	<c:set var="itemList" value="${cart.itemList}"/>
	<c:if test="${not empty itemList}">
	    <div>
		<font color="red">Please check your cart detail again!</font> <br/> 
		After checkout you can't make change to your cart.
	    </div>
	    <table border="1">
		<thead>
		    <tr>
			<th>No.</th>
			<th>Product name</th>
			<th>Quantity</th>
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
			</tr>
		    </c:forEach>
		</tbody>
	    </table>
	    Want to make change? <a href="viewCart">Click here</a>
	    <br/>

	    <h3>Checkout information</h3>
	    <c:set var="errors" value="${requestScope.CHECKOUT_ERRORS}"/>
	    <form action="checkout">
		Full name <input type="text" name="txtFullname" value="" /> (eg 2 - 50 chars)
		<c:if test="${not empty errors.fullnameLengthError}">
		    <font color="red">
			${errors.fullnameLengthError}
		    </font>
		</c:if>
		<br/>

		Address <input type="text" name="txtAddress" value="" /> (eg 5 - 100 chars)
		<c:if test="${not empty errors.addressLengthError}">
		    <font color="red">
			${errors.addressLengthError}
		    </font>
		</c:if>
		<br/>    
		
		<input type="submit" value="Checkout" name="btAction" />
	    </form>
	</c:if>

	<c:if test="${empty itemList}">
	    <h2 style="color:red">Your cart is empty!!! </h2>
	    <a href="shopping">Back to shopping page</a>
	</c:if>
    </body>
</html>
