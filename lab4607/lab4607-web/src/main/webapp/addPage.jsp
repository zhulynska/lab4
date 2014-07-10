<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Data</title>
</head>
<body>

<jsp:include page="menu.jsp" flush="true" />
<h1>Add Data</h1>
Enter table name:

<form action="Actions" method="post">
<select name="ShowTable">                     <!-- ShowTable -->
<option></option>
<option>emp</option>
<option>dept</option>
<option>salgrade</option>
</select>
<input type="submit" value="OK" name="showFrame">
<input type="hidden" value= "addPage.jsp" name="pageName">
</form>

<c:set var="fields" value="${requestScope.fields}"></c:set> 
<!-- to send request with new object fields -->
<form action="Actions" method="post">
<table>
<c:forEach var="field" items="${fields}">
<tr>
<td>Enter <c:out value="${field}"></c:out></td>
<td><input name="<c:out value="${field}"></c:out>" type="text"/></td>

</tr>
</c:forEach>
</table>

<input type="hidden" value= "addPage.jsp" name="pageName">
<input type="hidden" value= "${requestScope.table}" name="ShowTable">


<c:if test="${not empty fields }">
<input type="submit" name="AddNew" value="Add <c:out value="${requestScope.className}"></c:out>">
</c:if>
</form>
<c:out value="${requestScope.message}"></c:out>

</body>
</html>