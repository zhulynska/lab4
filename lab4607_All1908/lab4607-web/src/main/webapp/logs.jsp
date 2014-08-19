<%-- 
    Document   : logs
    Created on : 14.08.2014, 19:53:17
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logs Page</title>
    </head>
    <body>
        <h1>Logs Page</h1>
        <jsp:include page="menu.jsp" flush="true" />

        <c:set var="logsCollection" value="${sessionScope.logsCollection}"></c:set>

        <c:forEach var="field1" items="${logsCollection}">

            <c:out value="${field1}"></c:out> <br/>
        </c:forEach>


    </body>
</html>
