<%-- 
    Document   : salaries
    Created on : 13.08.2014, 19:36:47
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SalaryGrade</title>
    </head>
    <body>
        <h1>SalaryGrade</h1>  
        <jsp:include page="menu.jsp" flush="true" />

        <form action="SalaryGradeServlet" method="post">
            <c:set var="salaryGradeBean" value="${sessionScope.salaryGradeBean}"></c:set>

                <table border="1">
                    <tr> 
                        <td>Grade </td>
                        <td>Min Salary </td>
                        <td>High Salary </td>
                    </tr>
                    <tr> 
                        <td> <input type="text" name="gradeSelect"/> </td>
                        <td> <input type="text" name="minsalSelect"/> </td>
                        <td> <input type="text" name="hisalSelect"/></td>
                        <td> <input type="submit" name="FilterBtn" value="Filter"/> </td>
                    </tr>

                <c:set var="collection" value="${empty requestScope.collection ? salaryGradeBean.showAll(): requestScope.collection}"></c:set>   

                <c:forEach var="element" items="${collection}">
                    <tr>

                        <td><input name="grade<c:out value="${element['grade']}"></c:out>" readonly value="<c:out value="${element['grade']}"></c:out>" type="text"/> </td>  
                        <td><input name="minsal<c:out value="${element['grade']}"></c:out>" value="<c:out value="${element['minsal']}"></c:out>" type="text"/> </td>    
                        <td><input name="hisal<c:out value="${element['grade']}"></c:out>" value="<c:out value="${element['hisal']}"></c:out>" type="text"/> </td>    

                            <!--remove button-->
                                <td> <input type="submit" name="Correct<c:out value="${element['grade']}"></c:out>"  value="Correct"/> 

                                <input type="submit" name="Remove<c:out value="${element['grade']}"></c:out>"  value="Remove"/>
                            </td>

                        </tr>
                </c:forEach>

            </table>

            <a href="SalaryGradeServlet?Addnew=1">Add new SalaryGrade</a>


            <c:set var="needToAdd" value="${requestScope.needToAdd}"></c:set>
            <c:set var="salaryGradeFields" value="${salaryGradeBean.getFieldsName()}"></c:set> 


            <c:if test="${needToAdd eq '1'}" >

                <table>
                    <c:forEach var="field" items="${salaryGradeFields}">
                        <tr>
                            <td>Enter <c:out value="${field}"></c:out></td>
                            <td><input name="<c:out value="${field}"></c:out>" type="text"/></td>
                            </tr>
                    </c:forEach>
                </table>
                <input type="submit" name="Add" value="Add"/>
            </c:if>
        </form>  
        <br/><br/>
        <c:out value="${requestScope.message}"></c:out>    
    </body>
</html>
