<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DetailsPage</title>
    </head>
    <body>
          <h1>Details Page</h1>
        <jsp:include page="menu.jsp" flush="true" />
        
        <form action="EmployeesServlet" method="post">

        <c:set var="empBean" value="${sessionScope.empBean}"></c:set>
         <c:set var="fields" value="${empBean.getFieldsName()}"></c:set>
         <c:set var="depts" value="${sessionScope.depts}"></c:set>        
        <c:set var="selectedEmp" value="${requestScope.selectedEmp}"></c:set>
        <c:set var="collection" value="${empty requestScope.collection ? empBean.getDependents(selectedEmp.getEmpno()) : requestScope.collection}"></c:set>


        <c:set var="hierarchyBottom_up" value="${requestScope.hierarchyBottom_up}"></c:set> 
        
        <c:if test="${not empty hierarchyBottom_up}">Managers: </c:if>
        <c:forEach  var="mgr" items="${hierarchyBottom_up}">
            ---> &nbsp <c:out value="${mgr['ename']}"> &nbsp&nbsp </c:out> 
        </c:forEach>
        
        <br/><br/>
        
        <b><c:out value="${selectedEmp['ename']}"></c:out> </b>
        
        <input type="submit" name="<c:out value="${selectedEmp.getEmpno()}"></c:out>" value="Correct Employee"/>
         <input type="submit" name="Remove<c:out value="${selectedEmp['empno']}"></c:out>" value="Remove"/>
        
        <table>
            <c:forEach  var="field" items="${fields}">
            
            <c:if test="${field != 'ename'}" >
            <tr>
                <td> 
                    <c:out value="${field}"></c:out>  
                </td>
                <td> 
                    
                               
                    <c:choose>
                        <c:when test="${field eq 'manager'}"> <c:out value="${empBean.getManagerName(selectedEmp[field])}"/> </c:when>
                                    <c:when test="${field eq 'department'}"> <c:out value="${selectedEmp.getDepartment().getDname()}"/></c:when>
                                    <c:otherwise>  <c:out value="${selectedEmp[field]}"/>  </c:otherwise>
                    </c:choose> 
  </td>
              
            </tr>
            </c:if>
            </c:forEach>
        </table>
          
            
            
        <br>   
        <br>

      
        <c:if test="${not empty collection }">
          <table border="1">
          <tr> 
        <td> Name</td>
        <td> Job</td>
        <td> Department</td>
        <td> Manager</td>
        <td> Salary,$</td>
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

        <td><input type="text" name="managerSelect"/> </td>
        <td><input type="text" name="salaryMinSelect"/> - <input type="text" name="salaryMaxSelect"/></td>
        <td> <input type="submit" name="Filter" value="Filter"/> </td>
    </tr>



    <c:forEach var="element" items="${collection}">
        <tr>
            
        <td>
            <a href="EmployeesServlet?linkedNo=${element['empno']}">  
              <input name="<c:out value="${element['ename']}"></c:out>" value="<c:out value="${element['ename']}"></c:out>" type="text"/> </a></td>  
        <td><input name="<c:out value="${element['job']}"></c:out>" value="<c:out value="${element['job']}"></c:out>" type="text"/> </td>    
        <td><input name="<c:out value="${element['department']}"></c:out>" value="<c:out value="${element['department'].dname}"></c:out>" type="text"/> </td>    
        <td><input name="<c:out value="${element['manager']}"></c:out>" value="${empBean.getManagerName(element['manager'])}" type="text"/> </td>    
        <td><input name="<c:out value="${element['salary']}"></c:out>" value="<c:out value="${element['salary']}"></c:out>" type="text"/> </td>    
            
<!--remove button-->
            <td> <input type="submit" name="Remove<c:out value="${element['empno']}"></c:out>"  value="Remove"/> </td>
        </tr>
    </c:forEach>

          </table>
        </c:if>    
        </form> 
      <c:out value="${requestScope.message}"></c:out> 
    </body>
</html>
