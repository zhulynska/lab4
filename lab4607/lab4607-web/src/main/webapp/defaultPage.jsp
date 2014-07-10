<%@ page language="java" import="java.util.*,ua.edu.sumdu.j2ee.Zhulynska.employeeBean.*,ua.edu.sumdu.j2ee.Zhulynska.departmentBean.*,ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show all info</title>
</head>
<body>
<jsp:include page="menu.jsp" flush="true" />
<h1>Show all info</h1>
Enter table:
<form action="Actions" method="post">
<table>
<tr>
<td><select name="ShowTable">
<option></option>
<option>emp</option>
<option>dept</option>
<option>salgrade</option>
</select>
<input type="hidden" value= "${requestScope.table}" name="hiddenTableName">

<!-- to recognize the page, sent request-->
<input type="hidden" value= "defaultPage.jsp" name="pageName"> 

</td>
<td></td>
</tr>
<tr>
<td><input type="submit" value="Set Criterions" name="Criterions"></td>
<td>

<c:set var="fields" value="${requestScope.fields}"></c:set> 
<!-- to send request with new object fields -->
  
<table>
<c:forEach var="field" items="${fields}">
<tr>
<td>Enter <c:out value="${field}"></c:out></td>
<td><input name="<c:out value="${field}"></c:out>" type="text"/> </td>
</tr>
</c:forEach>
</table>

</td>
</tr>
<tr>
<td><input type="submit" value="OK" name="ShowAll"></td>
<td></td>
</tr>
</table>

</form>
<c:out value="${requestScope.message}"></c:out>



<c:set var="empCollection" value="${requestScope.empCollection}"></c:set> 
<c:if test="${not empty empCollection}">
<table border=1>
<tr>
<td><c:out value="id"></c:out> </td>
<td><c:out value="name"></c:out> </td>
<td><c:out value="job"></c:out> </td>
<td><c:out value="manager id"></c:out> </td>
<td><c:out value="salary"></c:out> </td>
<td><c:out value="hire date"></c:out> </td>
<td><c:out value="commissions"></c:out> </td>
<td><c:out value="department"></c:out> </td>
</tr> 
<c:forEach var="tableItem" items="${empCollection}">

<tr> 
<td><c:out value="${tableItem.empno}"></c:out> </td>
<td><c:out value="${tableItem.ename}"></c:out> </td>
<td><c:out value="${tableItem.job}"></c:out> </td>
<td><c:out value="${tableItem.manager}"></c:out> </td>
<td><c:out value="${tableItem.salary}"></c:out> </td> 
<td><c:out value="${tableItem.hiredate}"></c:out> </td>
<td><c:out value="${tableItem.commission}"></c:out> </td>
<td><c:out value="${tableItem.department.dname}"></c:out> </td>
</tr> 
</c:forEach>
</table>
</c:if>


<c:set var="deptCollection" value="${requestScope.deptCollection}"></c:set> 
<c:if test="${not empty deptCollection}">
<table border=1>
<tr>
<td><c:out value="id"></c:out> </td>
<td><c:out value="name"></c:out> </td>
<td><c:out value="location"></c:out> </td>
</tr> 
<c:forEach var="tableItem" items="${deptCollection}">

<tr> 
<td><c:out value="${tableItem.deptno}"></c:out> </td>
<td><c:out value="${tableItem.dname}"></c:out> </td>
<td><c:out value="${tableItem.location}"></c:out> </td>
</tr> 
</c:forEach>
</table>
</c:if>

<c:set var="salgradeCollection" value="${requestScope.salgradeCollection}"></c:set> 
<c:if test="${not empty salgradeCollection}">
<table border=1>
<tr>
<td><c:out value="grade"></c:out> </td>
<td><c:out value="min salary"></c:out> </td>
<td><c:out value="max salary"></c:out> </td>
</tr> 
<c:forEach var="tableItem" items="${salgradeCollection}">
<tr> 
<td><c:out value="${tableItem.grade}"></c:out> </td>
<td><c:out value="${tableItem.minsal}"></c:out> </td>
<td><c:out value="${tableItem.hisal}"></c:out> </td>
</tr> 
</c:forEach>
</table>
</c:if>
</body>
</html>