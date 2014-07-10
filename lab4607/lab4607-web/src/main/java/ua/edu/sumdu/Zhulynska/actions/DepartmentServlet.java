package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Department;
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Departments;

/**
 * Servlet implementation class DepartmentServlet
 */
@WebServlet("/DepartmentServlet")
public class DepartmentServlet extends HttpServlet implements ActionsFrame {

    private static final long serialVersionUID = 1L;
    @EJB
    private Departments departmentBean;

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger log = null;
        String pageName = request.getParameter("pageName");
        log = Logger.getLogger(DepartmentServlet.class);

        Actions.controllerActions(pageName, this, request, response, log);
        getServletContext().getRequestDispatcher("/" + pageName).forward(
                request, response);
    }

    public void removeInfo(HttpServletRequest request, Logger log) {

        try {
            if (request.getParameter("Remove") != null) {
                BigDecimal deptno = new BigDecimal(
                        request.getParameter("RemovedId"));

                if (departmentBean.selectDepartment(deptno) == null) {
                    throw new IncorrectDataException("incorrect id parameter");
                }

                departmentBean.deleteDepartment(deptno);
                request.setAttribute("message", "component with id " + deptno
                        + " removed");
                log.info("delete Department " + deptno);
                sendMessage(Actions.DateFormat3.format(new Date()) + " " + "delete Department " + deptno);

            }
        } catch (RemoteException e) {
            log.error("problems to connect with EJB", e);
        } catch (IncorrectDataException e) {
            request.setAttribute("message", e.getMessage());
            log.error(e.getMessage(), e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + e.getMessage());
        }
    }

    public void showAll(HttpServletRequest request, Logger log) {
        try {
            String table = request.getParameter("ShowTable");
            String criterionBtn = request.getParameter("Criterions");
            request.setAttribute("table", table);

            table = (table.isEmpty() || table == null ? request
                    .getParameter("hiddenTableName") : table);

            if (table == null) {
                throw new MySQLException("Select table!");
            }

            if (criterionBtn != null) {
                Department obj = new Department();

                // alternative of reflection:
                request.setAttribute("fields", departmentBean.getFieldsName());
                request.setAttribute("className", obj.getClass()
                        .getSimpleName());
            }

            if (criterionBtn == null && (table != null || !table.isEmpty())) {

                List<Department> tableContent = null;
                if (request.getParameter("deptno") != null
                        || request.getParameter("dname") != null
                        || request.getParameter("location") != null) {
                    tableContent = departmentBean.selectDepartment(
                            request.getParameter("deptno"),
                            request.getParameter("dname"),
                            request.getParameter("location"));
                } else {
                    tableContent = departmentBean.showAll();
                }
                log.info("show all info from dept");
                sendMessage(Actions.DateFormat3.format(new Date()) + " " + "show all info from dept");

                request.setAttribute("deptCollection", tableContent);

            }
        } catch (MySQLException e) {

            request.setAttribute("message", e.getMessage());
            log.error(e.getMessage(), e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + e.getMessage());
        } catch (RemoteException e) {
            log.error(e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + e.getMessage());
        }
    }

    public void addInfo(HttpServletRequest request, Logger log) {

        try {
            String findTable = request.getParameter("ShowTable"); // table

            if (request.getParameter("showFrame") != null) { // button
                if (findTable.isEmpty()) {
                    throw new MySQLException("Select table!");
                }

                Department obj = new Department();

                // fields
                // Actions.fieldsDetermonation(request, obj);
                request.setAttribute("fields", departmentBean.getFieldsName());
                request.setAttribute("className", obj.getClass()
                        .getSimpleName());

                request.setAttribute("table", findTable);
            }

            String addBtn = request.getParameter("AddNew");
            if (addBtn != null) {
                if (addBtn.contains("Department")) {
                    Department newDep = new Department();
                    newDep.setDeptno(new BigDecimal(request
                            .getParameter("deptno")));
                    newDep.setDname(request.getParameter("dname"));
                    newDep.setLocation(request.getParameter("location"));
                    departmentBean.createDepartment(newDep);
                    request.setAttribute("message", newDep.getString()
                            + "was created!");
                    log.info(newDep.getString() + " added!");
                    sendMessage(Actions.DateFormat3.format(new Date()) + " " + newDep.getString() + " added!");
                }
            }
        } catch (RemoteException e) {
            log.error("problems with adding", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + e.toString());
        } catch (MySQLException e) {
            request.setAttribute("message", e.getMessage());
        }
    }

    public void correctInfo(HttpServletRequest request, Logger log) {
        String url = "/correctPage.jsp";
        try {

            String correctedTable = request.getParameter("ShowTable");
            if (request.getParameter("Correct") != null) {

                String correctedId = request.getParameter("CorrectedId");

                Object corrected = null;
                corrected = departmentBean.selectDepartment(new BigDecimal(
                        correctedId));
                if (corrected == null) {
                    throw new IncorrectDataException("incorrect id parameter");
                }

                request.getSession().setAttribute("id", correctedId);

                request.setAttribute("fields", departmentBean.getFieldsName());
                request.setAttribute("correctedItem", corrected);

                request.setAttribute("table", correctedTable);
                url = "/correctPage.jsp";
            }

            String correctBtn = request.getParameter("CorrectElem");
            if (correctBtn != null) {

                if (correctBtn.contains("Department")) {
                    correctDepartment(request, departmentBean, log);
                }
            }

        } catch (RemoteException e) {
            request.setAttribute("message", "problems to connect with EJB");
            log.error("problems to connect with EJB", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "problems to connect with EJB: " + e.toString());
        } catch (IncorrectDataException e) {
            request.setAttribute("message", e.getMessage());
        }
    }

    /**
     * to update Department object
     *
     * @param request
     * @param departmentBean
     * @throws RemoteException
     */
    private void correctDepartment(HttpServletRequest request,
            Departments departmentBean, Logger log) {
        Department newDept = null;
        try {
            newDept = departmentBean.selectDepartment(new BigDecimal(
                    (String) request.getParameter("deptno")));
            newDept.setDname(request.getParameter("dname"));
            newDept.setLocation(request.getParameter("location"));

            departmentBean.update(newDept);
            request.setAttribute("message", newDept.getString()
                    + " was updated!");
            log.info(newDept.getString() + "updated");
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + newDept.getString() + "updated");
        } catch (RemoteException e) {
            request.setAttribute("message", "problems to update department"
                    + newDept.getDeptno());
            log.error("problems to update department" + newDept.getDeptno(), e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "problems to update department" + newDept.getDeptno() + ":" + e.toString());
        }
    }

    @Override
    public void sendMessage(String message) {
        TextMessage someMessage = null;
        try {
            someMessage = ((QueueSession) getServletContext().getAttribute("session")).createTextMessage();
            someMessage.setText(message);
            ((QueueSender) getServletContext().getAttribute("sender")).send(someMessage);
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(SalaryGradeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
