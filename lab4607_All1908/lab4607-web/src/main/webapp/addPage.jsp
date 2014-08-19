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
        <c:set var="days" value="${sessionScope.days}"></c:set> 
        <c:set var="months" value="${sessionScope.months}"></c:set> 
        <c:set var="years" value="${sessionScope.years}"></c:set> 
        <c:set var="empBean" value="${sessionScope.empBean}"></c:set> 
        <c:set var="deptBean" value="${sessionScope.depts}"></c:set> 
        <c:set var="fields" value="${empBean.getFieldsName()}"></c:set> 
            <!-- to send request with new object fields -->
            <form action="EmployeesServlet" method="post">
                <table>
                <c:forEach var="field" items="${fields}">
                    <tr>
                        <td>Enter <c:out value="${field}"></c:out></td>


                        <c:choose>
                            <c:when test="${field eq 'hiredate'}"> 
                                <td>Days:
                                    <select name="daysSelected">
                                        <option></option>
                                        <c:forEach var="day" items="${days}"> 
                                            <option> <c:out value="${day}"/> </option>
                                        </c:forEach>  
                                    </select>



                                    Months:
                                    <select name="monthsSelected">
                                        <option></option>
                                        <c:forEach var="month" items="${months}"> 
                                            <option> <c:out value="${month}"/> </option>
                                        </c:forEach>  
                                    </select>


                                    Years:
                                    <select name="yearsSelected">
                                        <option></option>
                                        <c:forEach var="year" items="${years}"> 
                                            <option> <c:out value="${year}"/> </option>
                                        </c:forEach>  
                                    </select>
                                </td>



                            </c:when>

                            <c:when test="${field eq 'manager'}"> 
                                <td>
                                    <select name="managerSelected">
                                        <option></option>
                                        <c:forEach var="managers" items="${empBean.getManagers() }"> 
                                            <option> <c:out value="${managers.getEname()}"/> </option>
                                        </c:forEach>  
                                    </select>
                                </td>
                            </c:when>

                                
                            <c:when test="${field eq 'department'}"> 
                                <td>
                                    <select name="departmentSelected">
                                        <option></option>
                                        <c:forEach var="deptss" items="${deptBean.getDeptName()}"> 
                                            <option> <c:out value="${deptss}"/> </option>
                                        </c:forEach>  
                                    </select>
                                </td>
                            </c:when>
                                

                            <c:otherwise> <td><input name="<c:out value="${field}"></c:out>" type="text"/></td> </c:otherwise>
                            </c:choose> 

                    </tr>
                </c:forEach>
            </table>



            <c:if test="${not empty fields }">
                <input type="submit" name="AddNew" value="Add <c:out value="${requestScope.className}"></c:out>">
            </c:if>
        </form>
        <c:out value="${requestScope.message}"></c:out>

    </body>
</html>