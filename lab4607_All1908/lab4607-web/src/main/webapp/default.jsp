<%-- 
    Document   : default1.jsp
    Created on : 18.07.2014, 5:30:00
    Author     : user
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.lang.Long" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employees</title>
    </head>
    <body>

        <h1>Employees</h1>
        <jsp:include page="menu.jsp" flush="true" />
        <c:set var="empBean" value="${sessionScope.empBean}"></c:set>
        <c:set var="depts" value="${sessionScope.depts}"></c:set>

        <c:set var="collection" value="${empty requestScope.collection ? empBean.showAll(): requestScope.collection}"></c:set>   

            <form action="EmployeesServlet" method="post">
                <table border="1">
                    <tr> 
                        <td> Name</td>
                        <td> Job</td>
                        <td> Department</td>
                        <td> Manager </td>
                        <td> Salary,$ </td>
                        <td> Action</td>
                    </tr>
                    <tr> 
                        <td> <input type="text" name="nameSelect"/> </td>

                        <td> <input type="text" name="jobSelect"/></td>

                        <td>

                            <select name="departmentSelect">
                                <option></option>
                            <c:forEach var="dept" items="${depts.getDeptName()}"> 
                                <option> <c:out value="${dept}"/> </option>
                            </c:forEach>  
                        </select>
                    </td>

                    <td><input type="text" name="managerName"/> </td>
                    <td><input type="text" name="salaryMinSelect"/> - <input type="text" name="salaryMaxSelect"/></td>
                    <td> <input type="submit" name="Filter" value="Filter"/> </td>
                </tr>

                <c:forEach var="element" items="${collection}">
                    <tr>
                        <td><a href="EmployeesServlet?linkedNo=${element['empno']}">  
                                <input name="<c:out value="${element['ename']}"></c:out>" value="<c:out value="${element['ename']}"></c:out>" type="text"/> </a></td>  
                        <td><input name="<c:out value="${element['job']}"></c:out>" value="<c:out value="${element['job']}"></c:out>" type="text"/> </td>    
                        <td><input name="<c:out value="${element['department']}"></c:out>" value="<c:out value="${element.getDepartment().getDname()}"></c:out>" type="text"/> </td>    
                        <td><input name="<c:out value="${element['manager']}"></c:out>" value="${empBean.getManagerName(element['manager'])}" type="text"/> </td>    
                        <td><input name="<c:out value="${element['salary']}"></c:out>" value="<c:out value="${element['salary']}"></c:out>" type="text"/> </td>  
                            <!--remove button-->
                                <td> <input type="submit" name="Remove<c:out value="${element['empno']}"></c:out>"  value="Remove"/> </td>
                        </tr>
                </c:forEach>
            </table>
        </form>
        <form action="EmployeesServlet" method="post">
            <a href="addPage.jsp">Add new Employee</a>
        </form>

        <c:out value="${requestScope.message}"></c:out> 
    </body>
</html>
