<%-- 
    Document   : createAccount
    Created on : Jul 11, 2020, 9:18:20 PM
    Author     : Long Le
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
    </head>
    <body>
        <h1>Create Account</h1>
	<form action="createAccount" method="POST">
	    <c:set var="errors" value="${requestScope.CREATE_ERRORS}" />
	    Username <input type="text" name="txtUsername" value="${param.txtUsername}" /> (eg 6 - 20 chars) 
	    <font color="red">
		<c:if test="${not empty errors.usernameLengthError}">
		    ${errors.usernameLengthError}
		</c:if>
		<c:if test="${not empty errors.usernameExisted}">
		    ${errors.usernameExisted}
		</c:if>
	    </font>
	    <br/>

	    Password <input type="password" name="txtPassword" value="" /> (eg 6 - 30 chars) 
	    <font color="red">
		<c:if test="${not empty errors.passwordLengthError}">
		    ${errors.passwordLengthError}
		</c:if>
	    </font>
	    <br/>

	    Confirm <input type="password" name="txtConfirm" value="" /> 
	    <font color="red">
		<c:if test="${not empty errors.confirmNotMatch}">
		    ${errors.confirmNotMatch}
		</c:if>
	    </font>
	    <br/>

	    Full name <input type="text" name="txtFullname" value="${param.txtFullname}" /> (eg 2 - 50 chars)
	    <font color="red">
		<c:if test="${not empty errors.fullnameLengthError}">
		    ${errors.fullnameLengthError}
		</c:if>
	    </font>
	    <br/>
	    
	    <input type="submit" value="Create new account" name="btAction" />
	    <input type="reset" value="Reset" />
	</form>
    </body>
</html>
