<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Correct Page</title>
</head>
<body>
<jsp:include page="menu.jsp" flush="true" />
<h1>Correct Data Page</h1>
<form action="Actions" method="post">
<table>
<tr>
<td>Enter table name:</td>
<!--  <td><input type="text" name="CorrectedTable" >-->
<td><select name="ShowTable">
<option></option>
<option>emp</option>
<option>dept</option>
<option>salgrade</option>
</select>
</td>
</tr>
<tr>
<td>Set id: </td>
<td><input type="text" name="CorrectedId"></td>
<td><input type="submit" value="OK" name="Correct"></td>
</table>
<input type="hidden" value= "correctPage.jsp" name="pageName">
</form>

<c:set var="field" value="${requestScope.fields}"></c:set>
<c:set var="object" value="${requestScope.correctedItem}"></c:set>
<form action="Actions" method="post">
<table>
<c:forEach var="fieldItem" items="${field}">
<tr>
<td>Enter <c:out value="${fieldItem}"></c:out></td>
<td><input name="<c:out value="${fieldItem}"></c:out>" type="text" value="<c:out value="${object[fieldItem]}"/>"/></td>
</tr>
</c:forEach>
</table>
<c:if test="${not empty field }">
<input type="submit" name="CorrectElem" value="Correct <c:out value="${object.class.simpleName}"></c:out>">
</c:if>
<c:out value="${requestScope.message}"></c:out>

<input type="hidden" value= "correctPage.jsp" name="pageName">
<input type="hidden" value= "${requestScope.table}" name="ShowTable">
</form>


</body>
</html>