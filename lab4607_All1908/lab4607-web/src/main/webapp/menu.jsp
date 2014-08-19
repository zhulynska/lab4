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

<table>
    <tr>
        <td width="20%"></td>
        <td width="20%"></td>
        <td width="20%"></td>
        <td width="20%"></td>
        <td align="right">Hello: <c:out value="${requestScope.currentUser}"></c:out></td>
        <td  align="right"><a href="loginForm.jsp">Sign out</a></td>
    </tr>
    
    <tr>
        <td width="20%"><a href="default.jsp">Employees</a></td>  
        <td width="20%"><a href="departments.jsp">Departments</a> </td>
        <td width="20%"><a href="salary.jsp">SalaryGrades</a> </td>
        <td width="20%"><a href="logs.jsp">LogPage</a> </td>

        <td></td>
        <td></td>
    </tr>   
    
</table>
        <br/> <br/>
</body>
</html>