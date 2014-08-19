<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Departments</title>
    </head>
    <body>
        <h1>Departments</h1>
        <jsp:include page="menu.jsp" flush="true" />

        <form action="DepartmentServlet" method="post">
            <c:set var="deptBean" value="${sessionScope.depts}"></c:set>

                <table border="1">
                    <tr> 
                        <td>Id </td>
                        <td>Name </td>
                        <td>Location </td>
                    </tr>
                    <tr> 
                        <td> <input type="text" name="deptnoSelect"/> </td>
                        <td> <input type="text" name="nameSelect"/> </td>
                        <td> <input type="text" name="locationSelect"/></td>
                        <td> <input type="submit" name="FilterBtn" value="Filter"/> </td>
                    </tr>

                <c:set var="collection" value="${empty requestScope.collection ? deptBean.showAll(): requestScope.collection}"></c:set>   
              
                <c:forEach var="element" items="${collection}">
                    <tr>

                        <td><input name="deptno<c:out value="${element['deptno']}"></c:out>" readonly value="<c:out value="${element['deptno']}"></c:out>" type="text"/> </td>  
                        <td><input name="dname<c:out value="${element['deptno']}"></c:out>" value="<c:out value="${element['dname']}"></c:out>" type="text"/> </td>    
                        <td><input name="location<c:out value="${element['deptno']}"></c:out>" value="<c:out value="${element['location']}"></c:out>" type="text"/> </td>    

                            <!--remove button-->
                                <td> <input type="submit" name="Correct<c:out value="${element['deptno']}"></c:out>"  value="Correct"/> 
                            
                                <input type="submit" name="Remove<c:out value="${element['deptno']}"></c:out>"  value="Remove"/>
                            </td>

                        </tr>
                </c:forEach>

            </table>
              
            <a href="DepartmentServlet?Addnew=1">Add new Department</a>
             

            <c:set var="needToAdd" value="${requestScope.needToAdd}"></c:set>
            <c:set var="deptFields" value="${deptBean.getFieldsName()}"></c:set> 

        
            <c:if test="${needToAdd eq '1'}" >
               
                <table>
                    <c:forEach var="field" items="${deptFields}">
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
