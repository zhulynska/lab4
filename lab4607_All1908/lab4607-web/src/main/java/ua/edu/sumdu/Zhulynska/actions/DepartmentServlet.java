package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Department;
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Departments;
import ua.edu.sumdu.j2ee.Zhulynska.employeeBean.Employee;
import ua.edu.sumdu.j2ee.Zhulynska.employeeBean.Employees;

/**
 * Servlet implementation class DepartmentServlet
 */
@WebServlet("/DepartmentServlet")
public class DepartmentServlet extends ServletFrame {

    private static final long serialVersionUID = 1L;
    @EJB
    private Departments departmentBean;
    @EJB
    private Employees empBean;

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger log = null;
        String pageName = "departments.jsp";
        ArrayList<String> logsCollection = null;
        String paramName = null;

        try {
            Enumeration parameterNames = request.getParameterNames();
            logsCollection = (ArrayList<String>) request.getSession().getAttribute("logsCollection");
            log = Logger.getLogger(DepartmentServlet.class);
            while (parameterNames.hasMoreElements()) {
                paramName = (String) parameterNames.nextElement();

                if (paramName.contains("Remove") && request.getParameter(paramName) != null) {
                    removeInfo(request, log, paramName, logsCollection);

                }
                if (paramName.equals("Addnew") && request.getParameter(paramName) != null) {
                    request.setAttribute("needToAdd", "1");
                }

                if (request.getParameter("Add") != null) {
                    addInfo(request, log, logsCollection);
                }

                if (request.getParameter("FilterBtn") != null) {
                    filterInfo(request, log, null, logsCollection);
                }

                if (paramName.contains("Correct") && request.getParameter(paramName) != null) {
                    correctInfo(request, log, paramName, logsCollection);
                }
            }

        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            log.error(e.getMessage(), e);
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + e.getMessage());
        }
        getServletContext().getRequestDispatcher("/" + pageName).forward(
                request, response);
    }

    public void filterInfo(HttpServletRequest request, Logger log, String param, ArrayList<String> logsCollection) {
        List<Department> collection = null;
        if (request.getParameter("deptnoSelect") != null
                || request.getParameter("nameSelect") != null
                || request.getParameter("locationSelect") != null) {
            collection = departmentBean.selectDepartment(
                    request.getParameter("deptnoSelect"),
                    request.getParameter("nameSelect"),
                    request.getParameter("locationSelect"));
            request.setAttribute("collection", collection);
        }
    }

    public void removeInfo(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection) {
        BigDecimal deptno = new BigDecimal(parameterName.substring(6));
        ArrayList<Employee> empColl = (ArrayList<Employee>) empBean.showAll();
        String message = null;
        boolean isHere = false;
        for (Employee emp : empColl) {
            if (emp.getDepartment().getDeptno().equals(deptno)) {
                isHere = true;
            }
        }

        if (!isHere) {
            departmentBean.deleteDepartment(deptno);
            message = "Department #" + deptno
                    + " removed";
        } else {
            message = "Department #" + deptno + " can't be removed! There are related links on this component!";
        }

        request.setAttribute("message", message);
        log.info(message);
        sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + message);
        logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + message);
    }

    
    
    
    public void addInfo(HttpServletRequest request, Logger log, ArrayList<String> logsCollection) {
        Department newDep = new Department();
        newDep.setDeptno(new BigDecimal(request
                .getParameter("deptno")));
        newDep.setDname(request.getParameter("dname"));
        newDep.setLocation(request.getParameter("location"));

        ArrayList<Department> all = (ArrayList) departmentBean.showAll();
        boolean isHere = false;
        for (Department dept : all) {
            if (dept.getDeptno().equals(new BigDecimal(request.getParameter("deptno")))) {
                isHere = true;
            }
        }
        if (!isHere) {
            departmentBean.createDepartment(newDep);

            request.setAttribute("message", newDep.getString()
                    + "was created!");
            log.info(newDep.getString() + " added!");
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + newDep.getString() + " added!");
            logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + newDep.getString() + " added!");
        }
    }

    public void correctInfo(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection) {
        String deptno = parameterName.substring(7);
        Department corrected = departmentBean.selectDepartment(new BigDecimal(deptno));
        String depName = request.getParameter("dname" + deptno);
        corrected.setDname(depName);
        corrected.setLocation(request.getParameter("location" + deptno));
        departmentBean.update(corrected);
        request.setAttribute("message", "Department " + depName + " was updated");
        log.info("Department " + depName + " was updated");
        sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "Department " + depName + " was updated");
        logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + "Department " + depName + " was updated");
    }
}
