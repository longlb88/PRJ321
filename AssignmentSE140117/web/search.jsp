<%-- 
    Document   : search
    Created on : Jul 8, 2020, 11:59:32 AM
    Author     : Long Le
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
	<c:set var="account" value="${sessionScope.ACCOUNT}" />
	<c:if test="${not empty account}">
	    <font color="red">
	    Welcome, ${account.fullname}
	    </font>
	</c:if>
	
	<form action="logout">
	    <input type="submit" value="Logout" name="btAction" />
	</form>

        <h1>Search</h1>

	<c:set var="searchValue" value="${param.txtSearchValue}" />
	<form action="search">
	    Search value <input type="text" name="txtSearchValue" value="${searchValue}" />
	    <input type="submit" value="Search" name="btAction" />
	</form>

	<c:if test="${not empty searchValue}">
	    <c:set var="result" value="${requestScope.SEARCH_RESULT}" />
	    <c:if test="${not empty result}">
		<table border="1">
		    <thead>
			<tr>
			    <th>Username</th>
			    <th>Password</th>
			    <th>Full Name</th>
			    <th>Admin</th>
			    <th>Delete</th>
			    <th>Update</th>
			</tr>
		    </thead>
		    <tbody>
			<c:forEach var="dto" items="${result}" varStatus="counter">
			<form action="updateAccount">
			    <tr>
				<td>
				    ${dto.username}
				    <input type="hidden" name="txtUsername" value="${dto.username}" />
				</td>
				<td>    
				    <input type="text" name="txtPassword" value="${dto.password}" />
				</td>
				<td>
				    <input type="text" name="txtFullname" value="${dto.fullname}" />
				</td>
				<td>
				    <input type="checkbox" name="chkAdmin" value="ON"
					   <c:if test="${dto.role}">
					       checked="checked"
					   </c:if>
					   />
				</td>
				<td>
				    <c:url var="urlRewriting" value="deleteAccount">
					<c:param name="username" value="${dto.username}"/>
					<c:param name="lastSearchValue" value="${searchValue}"/>
				    </c:url>
				    <a href="${urlRewriting}">Delete</a>
				</td>
				<td>
				    <input type="submit" value="Update" name="btAction" />
				    <input type="hidden" name="txtSearchValue" value="${searchValue}" />
				</td>
			    </tr>
			</form>    
		    </c:forEach>
		</tbody>
	    </table>
	</c:if>

	<c:if test="${empty result}">
	    <h2>No record found!!!</h2>
	</c:if>

	<c:set var="updateErrors" value="${requestScope.UPDATE_ERRORS}"/>
	<c:if test="${not empty updateErrors}"> 
	    <font color="red">
	    Errors when update account ${requestScope.UPDATED_USER}:<br/>
		<c:if test="${not empty updateErrors.passwordLengthError}">
		    ${updateErrors.passwordLengthError}<br/>
		</c:if>
		<c:if test="${not empty updateErrors.fullnameLengthError}">
		    ${updateErrors.fullnameLengthError}<br/>
		</c:if>
	    </font
	</c:if>
    </c:if>
</body>
</html>
