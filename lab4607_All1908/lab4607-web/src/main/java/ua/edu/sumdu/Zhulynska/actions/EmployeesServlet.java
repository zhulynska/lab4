/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Departments;
import ua.edu.sumdu.j2ee.Zhulynska.employeeBean.Employee;
import ua.edu.sumdu.j2ee.Zhulynska.employeeBean.Employees;

/**
 * @author user
 */
public class EmployeesServlet extends ServletFrame {

    @EJB
    private Employees empBean;

    @EJB
    private Departments departmentBean;

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger log = null;
        String pageName = "default.jsp";
        ArrayList<String> logsCollection = null;
        log = Logger.getLogger(EmployeesServlet.class);
        String paramName = null;
        try {
            Enumeration parameterNames = request.getParameterNames();
            logsCollection = (ArrayList<String>) request.getSession().getAttribute("logsCollection");

            while (parameterNames.hasMoreElements()) {
                paramName = (String) parameterNames.nextElement();

                if (paramName.contains("Remove")) {
                    if (request.getParameter(paramName) != null) {
                        removeInfo(request, log, paramName, logsCollection);
                        pageName = "default.jsp";
                    }
                }

                if (paramName.contains("Add") && !request.getParameter(paramName).isEmpty()) {
                    addInfo(request, log, logsCollection);
                    pageName = "addPage.jsp";
                }
                if (paramName.equals("Filter") && request.getParameter(paramName) != null) {
                    filterInfo(request, log, paramName, logsCollection);
                    pageName = "default.jsp";
                }

                if (paramName.equals("linkedNo") && request.getParameter(paramName) != null) {
                    getDependentWorkers(request, log, paramName, logsCollection);
                    pageName = "detailsPage.jsp";
                }

                if (request.getParameter(paramName).equals("Correct Employee")) {
                    String idToCorrect = paramName;
                    correctInfo(request, log, idToCorrect, logsCollection);
                    pageName = "correctPage.jsp";
                }

                if (paramName.equals("ChangedEmp") && request.getParameter(paramName) != null) {
                    String id = request.getParameter(paramName);
                    correctEmployee(request, log, id, logsCollection);
                    pageName = "correctPage.jsp";
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            logsCollection.add(e.getMessage());
            request.setAttribute("message", e.getMessage());
        }
        getServletContext().getRequestDispatcher("/" + pageName).forward(
                request, response);
    }

    public void correctInfo(HttpServletRequest request, Logger log, String id, ArrayList<String> logsCollection) {
        Object corrected = empBean.selectEmployee(new Long(id));
        request.setAttribute("correctedItem", corrected);
    }

    /**
     * to update Employee object
     *
     * @param request
     * @param empBean
     * @param departmentBean
     * @param log
     */
    private void correctEmployee(HttpServletRequest request, Logger log, String id, ArrayList<String> logsCollection) {
        Employee newEmp = null;
        try {
            newEmp = empBean.selectEmployee(new Long(id));
            newEmp.setEname(request.getParameter("ename"));
            newEmp.setJob(request.getParameter("job"));

            newEmp.setManager(empBean.getManagerNo(request
                    .getParameter("managerSelected")));
            newEmp.setHiredate(ServletFrame.DateFormat1.parse(request
                    .getParameter("yearsSelected") + "-" + request
                    .getParameter("monthsSelected") + "-" + request
                    .getParameter("daysSelected")));
            newEmp.setSalary(new Float(request.getParameter("salary")));
            if (!request.getParameter("commission").isEmpty()) {
                newEmp.setCommission(new Float(request
                        .getParameter("commission")));
            }
            BigDecimal deptno = departmentBean.getDepartmentNo(request.getParameter("departmentSelected"));
            newEmp.setDepartment(departmentBean.selectDepartment(deptno));
            empBean.update(newEmp);
            request.setAttribute("message", newEmp + " was updated!");
            log.info("corrected " + newEmp.getClass().getSimpleName()
                    + "with id " + newEmp.getEmpno());
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "corrected " + newEmp.getClass().getSimpleName()
                    + "with id " + newEmp.getEmpno());
            logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + "corrected " + newEmp.getClass().getSimpleName()
                    + "with id " + newEmp.getEmpno());

        } catch (ParseException e) {
            request.setAttribute("message",
                    "check out hiredate");
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "field <<hiredate>> set in incorrect format");
        }
    }

    /**
     * used to select the list of dependent workers of employee with empno is in
     * parameterName
     *
     * @param request
     * @param log
     * @param parameterName
     */
    public void getDependentWorkers(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection) {
        List<Employee> collection = new ArrayList<Employee>();
        Employee selectedEmp = empBean.selectEmployee(new Long(request.getParameter("linkedNo")));
        request.setAttribute("selectedEmp", selectedEmp);
          
        request.setAttribute("hierarchyBottom_up", empBean.getManagersTree(selectedEmp.getEmpno()));
        
        
        collection = empBean.getDependents(new Long(request.getParameter(parameterName)));
        request.setAttribute("collection", collection);
        if (collection.isEmpty()) {
            request.setAttribute("message", "There are no workers in selected Employee!");
        }
        log.info("depentent workers of Employee " + selectedEmp.getEname() + " are shown");
        sendMessage(ServletFrame.DateFormat3.format(new Date()) + " depentent workers of Employee " + selectedEmp.getEname() + " are shown");
        logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + " depentent workers of Employee " + selectedEmp.getEname() + " are shown");
    }

    public void removeInfo(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection) {
        String empNo = parameterName.substring(6);
        String ename = empBean.selectEmployee(new Long(empNo)).getEname();
        
        ArrayList<Employee> empColl = (ArrayList<Employee>) empBean.getManagers();
        String message = null;
        boolean isHere = false;
        for (Employee emp : empColl) {
            if (emp.getEmpno().equals(new Long(empNo))) {
                isHere = true;
            }
        }
        if (!isHere) {
            empBean.deleteEmployee(new Long(empNo));
            message = "Employee " + ename
                    + " was removed!";
        } else {
            message = "Employee " + ename + " can't be removed! He has dependent workers!";
        }

        request.setAttribute("message", message);
        log.info(message);
        sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + message);
        logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + message);
}

    public void addInfo(HttpServletRequest request, Logger log, ArrayList<String> logs) {
        try {
            Employee newEmp = new Employee();
            newEmp.setEmpno(new Long(request.getParameter("empno")));
            newEmp.setEname(request.getParameter("ename"));
            newEmp.setJob(request.getParameter("job"));

            newEmp.setManager(empBean.getManagerNo(request
                    .getParameter("managerSelected")));
            newEmp.setHiredate(ServletFrame.DateFormat1.parse(request
                    .getParameter("yearsSelected") + "-" + request
                    .getParameter("monthsSelected") + "-" + request
                    .getParameter("daysSelected")));
            newEmp.setSalary(new Float(request.getParameter("salary")));
            if (!request.getParameter("commission").isEmpty()) {
                newEmp.setCommission(new Float(request
                        .getParameter("commission")));
            }
            BigDecimal deptno = departmentBean.getDepartmentNo(request.getParameter("departmentSelected"));
            newEmp.setDepartment(departmentBean.selectDepartment(deptno));

            empBean.createEmployee(newEmp);
            request.setAttribute("message", newEmp + "was created!");
            log.info("add new " + newEmp);
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "add new " + newEmp);
            logs.add(ServletFrame.DateFormat3.format(new Date()) + " add new " + newEmp);

        } catch (ParseException e) {
            log.error("incorrect parsing date", e);
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "incorrect parsing date:" + e.toString());
            request.setAttribute("message", e);
            logs.add(ServletFrame.DateFormat3.format(new Date()) + " " + "incorrect parsing date");
        }
    }

    public void filterInfo(HttpServletRequest request, Logger log, String filterButton, ArrayList<String> logs) throws RemoteException {
        List<Employee> collection = null;
        String managerSelect = request.getParameter("managerName");
        Long managerFilter = null;
        try {
            managerFilter = (managerSelect != null ? empBean.getManagerNo(managerSelect) : null);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
        }
        if (request.getParameter("nameSelect") != null
                || request.getParameter("jobSelect") != null
                || request.getParameter("departmentSelect") != null
                || managerFilter != null
                || request.getParameter("salaryMinSelect") != null
                || request.getParameter("salaryMaxSelect") != null) {
            collection = empBean.selectEmployee(
                    request.getParameter("nameSelect"),
                    request.getParameter("jobSelect"),
                    request.getParameter("departmentSelect"),
                    managerFilter,
                    request.getParameter("salaryMinSelect"),
                    request.getParameter("salaryMaxSelect"));
            request.setAttribute("collection", collection);
        }
    }
}
