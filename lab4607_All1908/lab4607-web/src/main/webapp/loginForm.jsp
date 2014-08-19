<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="RegistrationServlet" method="post">
<table >
<tr>
<td>Enter login:</td>
<td> <input type="text" name="login" > </td>
<tr>
<td>Enter password: </td>
<td><input type="password" name="password"> </td>
<td><input type="submit" name="LogIn" value="Log in">
<input type="submit" name="Registration" value="Registration"></td>
<tr>
</table>
    

    <jsp:useBean id="logsCollection" class="java.util.ArrayList" scope="session" />
   

</form>
<c:out value="${requestScope.message}"></c:out>
</body>
</html>