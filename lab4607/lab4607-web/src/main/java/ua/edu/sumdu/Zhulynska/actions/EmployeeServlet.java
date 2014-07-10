package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
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
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Departments;
import ua.edu.sumdu.j2ee.Zhulynska.employeeBean.Employee;
import ua.edu.sumdu.j2ee.Zhulynska.employeeBean.Employees;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet implements ActionsFrame {

    @EJB
    private Employees empBean;

    @EJB
    private Departments departmentBean;

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger log = null;
        String pageName = request.getParameter("pageName");
        log = Logger.getLogger(EmployeeServlet.class);

        Actions.controllerActions(pageName, this, request, response, log);
        getServletContext().getRequestDispatcher("/" + pageName).forward(
                request, response);
    }

    public void removeInfo(HttpServletRequest request, Logger log) {

        try {
            if (request.getParameter("Remove") != null) {

                Long empno = new Long(request.getParameter("RemovedId"));

                if (empBean.selectEmployee(empno) == null) {
                    throw new IncorrectDataException("incorrect id parameter");
                }

                empBean.deleteEmployee(empno);
                request.setAttribute("message", "component with id "
                        + empno + " removed");
                log.info("delete Department " + empno);
                sendMessage(Actions.DateFormat3.format(new Date()) + " " + "delete Department " + empno);

            }
        } catch (RemoteException e) {
            log.error("problems to connect with EJB", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "problems to connect with EJB: " + e.toString());
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
                Object obj = new Employee();

                //Actions.fieldsDetermonation(request, obj);
                request.setAttribute("fields", empBean.getFieldsName());
                request.setAttribute("className", obj.getClass().getSimpleName());
                request.setAttribute("table", table);
            }

            if (criterionBtn == null && (table != null || !table.isEmpty())) {

                List<Employee> tableContent = null;
                if (request.getParameter("empno") != null
                        || request.getParameter("ename") != null
                        || request.getParameter("job") != null
                        || request.getParameter("manager") != null
                        || request.getParameter("hiredate") != null
                        || request.getParameter("salary") != null
                        || request.getParameter("commission") != null
                        || request.getParameter("department") != null) {
                    tableContent = empBean.selectEmployee(
                            request.getParameter("empno"),
                            request.getParameter("ename"),
                            request.getParameter("job"),
                            request.getParameter("manager"),
                            request.getParameter("hiredate"),
                            request.getParameter("salary"),
                            request.getParameter("commission"),
                            request.getParameter("department"));
                } else {
                    tableContent = empBean.showAll();
                }
                log.info("show all info from emp");
                sendMessage(Actions.DateFormat3.format(new Date()) + " " + "show all info from emp");
                request.setAttribute("empCollection", tableContent);

            }

            /*
             String table = request.getParameter("ShowTable");
             String criterionBtn = request.getParameter("Criterions");

             if (criterionBtn != null) {
             Object obj = new Employee();

				
             //Actions.fieldsDetermonation(request, obj);
             request.setAttribute("fields", empBean.getFieldsName());
             request.setAttribute("className", obj.getClass().getSimpleName());
             request.setAttribute("table", table);
             }

             table = (table.isEmpty() || table == null ? request
             .getParameter("hiddenTableName") : table);
             if (table == null)
             throw new MySQLException("Select table!");

             List<Employee> tableContent = null;
             if (request.getParameter("empno") != null
             || request.getParameter("ename") != null
             || request.getParameter("job") != null
             || request.getParameter("manager") != null
             || request.getParameter("hiredate") != null
             || request.getParameter("salary") != null
             || request.getParameter("commission") != null
             || request.getParameter("department") != null)

             tableContent = empBean.selectEmployee(
             request.getParameter("empno"),
             request.getParameter("ename"),
             request.getParameter("job"),
             request.getParameter("manager"),
             request.getParameter("hiredate"),
             request.getParameter("salary"),
             request.getParameter("commission"),
             request.getParameter("department"));
             else
             tableContent = empBean.showAll();
             log.info("show all info from emp");
             request.setAttribute("empCollection", tableContent);
             */        } catch (MySQLException e) {
            request.setAttribute("message", e.getMessage());
            log.error(e.getMessage(), e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + e.getMessage());
        } catch (RemoteException e) {
            log.error("can't show table content ", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "can't show table content: " + e.getMessage());
        } catch (ParseException e) {
            log.error("can't show table content ", e);
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

                Employee obj = new Employee();

                //Actions.fieldsDetermonation(request, obj);
                request.setAttribute("fields", empBean.getFieldsName());
                request.setAttribute("className", obj.getClass().getSimpleName());
                request.setAttribute("table", findTable);

            }

            String addBtn = request.getParameter("AddNew");
            if (addBtn != null) {
                if (addBtn.contains("Employee")) {
                    Employee newEmp = new Employee();

                    newEmp.setEmpno(new Long(request.getParameter("empno")));
                    newEmp.setEname(request.getParameter("ename"));
                    newEmp.setJob(request.getParameter("job"));
                    newEmp.setManager(new Integer(request
                            .getParameter("manager")));
                    newEmp.setHiredate(Actions.DateFormat1.parse(request
                            .getParameter("hiredate")));
                    newEmp.setSalary(new Float(request.getParameter("salary")));
                    if (!request.getParameter("commission").isEmpty()) {
                        newEmp.setCommission(new Float(request
                                .getParameter("commission")));
                    }
                    newEmp.setDepartment(departmentBean
                            .selectDepartment(new BigDecimal(request
                                            .getParameter("department"))));

                    empBean.createEmployee(newEmp);
                    request.setAttribute("message", newEmp + "was created!");
                    log.info("add new " + newEmp);
                    sendMessage(Actions.DateFormat3.format(new Date()) + " " + "add new " + newEmp);
                }
            }
        } catch (ParseException e) {
            log.error("incorrect parsing date", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "incorrect parsing date:" + e.toString());
            request.setAttribute("message", e);
        } catch (RemoteException e) {
            log.error("problems with adding", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "problems with adding" + e.toString());
        } catch (MySQLException e) {
            request.setAttribute("message", e.getMessage());
            log.error(e.getMessage(), e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + e.getMessage());
        }

        //return "/addPage.jsp";
    }

    public void correctInfo(HttpServletRequest request, Logger log) {
        String url = "/correctPage.jsp";
        try {
            String correctedTable = request.getParameter("ShowTable");
            if (request.getParameter("Correct") != null) {

                String correctedId = request.getParameter("CorrectedId");
                Object corrected = null;
                corrected = empBean.selectEmployee(new Long(correctedId));
                if (corrected == null) {
                    throw new IncorrectDataException("incorrect id parameter");
                }

                request.getSession().setAttribute("id", correctedId);
                request.setAttribute("fields", empBean.getFieldsName());
                request.setAttribute("correctedItem", corrected);
                request.setAttribute("table", correctedTable);
                url = "/correctPage.jsp";
            }

            String correctBtn = request.getParameter("CorrectElem");
            if (correctBtn != null) {

                if (correctBtn.contains("Employee")) {
                    correctEmployee(request, empBean, departmentBean, log);
                }
            }

        } catch (RemoteException e) {
            request.setAttribute("message", "problems to connect with EJB");
            log.error("problems to connect with EJB", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "problems to connect with EJB " + e.toString());
        } catch (IncorrectDataException e) {
            request.setAttribute("message", e.getMessage());
        }
    }

    /**
     * to update Employee object
     *
     * @param request
     * @param empBean
     * @param departmentBean
     * @param log
     */
    private void correctEmployee(HttpServletRequest request,
            Employees empBean, Departments departmentBean, Logger log) {
        Employee newEmp = null;
        try {
            newEmp = empBean.selectEmployee(new Long((String) request
                    .getParameter("empno")));
            newEmp.setEname(request.getParameter("ename"));
            newEmp.setJob(request.getParameter("job"));
            newEmp.setManager(new Integer(request.getParameter("manager")));
            newEmp.setHiredate(Actions.DateFormat1.parse(request
                    .getParameter("hiredate")));

            newEmp.setSalary(new Float(request.getParameter("salary")));
            String comm = request.getParameter("commission");
            if (comm != null || !comm.equals("")) {
                newEmp.setCommission(new Float(comm));
            }
            newEmp.setDepartment(departmentBean
                    .selectDepartment(new BigDecimal(request
                                    .getParameter("department"))));
            empBean.update(newEmp);
            request.setAttribute("message", newEmp + " was updated!");
            log.info("corrected " + newEmp.getClass().getSimpleName()
                    + "with id " + newEmp.getEmpno());
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "corrected " + newEmp.getClass().getSimpleName()
                    + "with id " + newEmp.getEmpno());
        } catch (RemoteException e) {
            request.setAttribute("message", "problems to update employee "
                    + newEmp.ename);
            log.error("problems to update employee " + newEmp.ename, e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "problems to update employee " + newEmp.ename + ": " + e.toString());
        } catch (ParseException e) {
            request.setAttribute("message",
                    "set field <<hiredate>>  in format: yyyy-mm-dd");
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + "field <<hiredate>> set in incorrect format");
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
