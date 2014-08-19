<%@ page language="java" import="ua.edu.sumdu.Zhulynska.dates.DateClass" contentType="text/html; charset=ISO-8859-1"
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

        <c:set var="empBean" value="${sessionScope.empBean}"></c:set>
        <c:set var="fields" value="${empBean.getFieldsName()}"></c:set>
        <c:set var="object" value="${requestScope.correctedItem}"></c:set>
        <c:set var="deptBean" value="${sessionScope.depts}"></c:set> 
        <c:set var="days" value="${sessionScope.days}"></c:set> 
        <c:set var="months" value="${sessionScope.months}"></c:set> 
        <c:set var="years" value="${sessionScope.years}"></c:set> 

            <form action="EmployeesServlet?ChangedEmp=${object['empno']}" method="post">


            <table>
                <c:forEach var="fieldItem" items="${field}">

                    <c:if test="${fieldItem != 'empno'}" >
                        <tr>
                            <td>Enter <c:out value="${fieldItem}"></c:out></td>
                            <td><input name="<c:out value="${fieldItem}"></c:out>" type="text" value="<c:out value="${fieldItem eq 'department' ? object.getDepartment().getDname(): object[fieldItem]}"/>"/></td>
                            </tr>
                    </c:if>

                </c:forEach>

            </table>


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
                                            <option <c:if test ="${DateClass.getDayFromDate(object['hiredate']) eq day}"> selected </c:if> > <c:out value="${day}"/> </option>
                                        </c:forEach>  
                                    </select>



                                    Months:
                                    <select name="monthsSelected">
                                        <option></option>
                                        <c:forEach var="month" items="${months}"> 
                                            <option  <c:if test ="${month == DateClass.getMonthFromDate(object['hiredate'])}"> selected </c:if> > <c:out value="${month}"/>  </option>
                                        </c:forEach>  
                                    </select>


                                    Years:
                                    <select name="yearsSelected">
                                        <option></option>
                                        <c:forEach var="year" items="${years}"> 
                                            <option <c:if test ="${year == DateClass.getYearFromDate(object['hiredate'])}"> selected </c:if>> <c:out value="${year}"/> </option>
                                        </c:forEach>  
                                    </select>
                                </td>



                            </c:when>

                            <c:when test="${field eq 'manager'}"> 
                                <td>
                                    <select name="managerSelected">
                                        <option></option>
                                        <c:forEach var="manager" items="${empBean.getManagers() }"> 
                                            <option <c:if test ="${manager['empno'] == object['manager']}"> selected </c:if> > <c:out value="${manager.getEname()}"/>  </option>
                                        </c:forEach>  
                                    </select>
                                </td>
                            </c:when>


                            <c:when test="${field eq 'department'}"> 
                                <td>
                                    <select name="departmentSelected">
                                        <option></option>
                                        <c:forEach var="deptss" items="${deptBean.getDeptName()}"> 
                                            <option  <c:if test ="${deptss == object.getDepartment().getDname()}"> selected </c:if>  > <c:out value="${deptss}"/> </option>
                                        </c:forEach>  
                                    </select>
                                </td>
                            </c:when>


                            <c:when test="${field eq 'empno'}"> 
                                <td><input readonly name="<c:out value="${field}"></c:out>" value="<c:out value="${object[field]}"></c:out>" type="text"/></td>
                                </c:when>                


                            <c:otherwise> <td><input name="<c:out value="${field}"></c:out>" value="<c:out value="${object[field]}"></c:out>" type="text"/></td> </c:otherwise>
                            </c:choose> 

                    </tr>
                </c:forEach>
            </table>           

            <c:if test="${not empty fields }">
                <input type="submit" name="Save changes" value="Save changes">
            </c:if>
                <br/>
            <c:out value="${requestScope.message}"></c:out>
        </form>
    </body>
</html>