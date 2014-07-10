package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
import ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean.SalaryGrade;
import ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean.SalaryGrades;

/**
 * Servlet implementation class SalaryGradeServlet
 */
@WebServlet("/SalaryGradeServlet")
public class SalaryGradeServlet extends HttpServlet implements ActionsFrame {

    private static final long serialVersionUID = 1L;
    @EJB
    private SalaryGrades salaryGradeBean;

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        Logger log = Logger.getLogger(SalaryGradeServlet.class);
        String pageName = request.getParameter("pageName");

        Actions.controllerActions(pageName, this, request, response, log);
        getServletContext().getRequestDispatcher("/" + pageName).forward(
                request, response);
    }

    public void removeInfo(HttpServletRequest request, Logger log) {

        try {
            if (request.getParameter("Remove") != null) {
                Long sal = new Long(request.getParameter("RemovedId"));
                if (salaryGradeBean.selectSalaryGrade(sal) == null) {
                    throw new IncorrectDataException("incorrect id parameter");
                }

                salaryGradeBean.deleteSalaryGrade(sal);
                request.setAttribute("message", "component with id " + sal
                        + " removed");
                log.info("removed SalaryGrade with id" + sal);
                sendMessage("removed SalaryGrade with id" + sal);

            }
        } catch (RemoteException e) {
            log.error("problems to connect with EJB", e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " problems to connect with EJB");
        } catch (IncorrectDataException e) {
            request.setAttribute("message", e.getMessage());
            log.error(e.getMessage(), e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " " + e.getMessage());
        }
    }

    public void addInfo(HttpServletRequest request, Logger log) {

        try {
            String findTable = request.getParameter("ShowTable");

            if (request.getParameter("showFrame") != null) {
                if (findTable.isEmpty()) {
                    throw new MySQLException("Select table!");
                }

                SalaryGrade obj = new SalaryGrade();

                //Actions.fieldsDetermonation(request, obj);
                request.setAttribute("fields", salaryGradeBean.getFieldsName());
                request.setAttribute("className", obj.getClass().getSimpleName());
                request.setAttribute("table", findTable);
            }

            String addBtn = request.getParameter("AddNew");
            if (addBtn != null) {
                if (addBtn.contains("SalaryGrade")) {
                    SalaryGrade newSal = new SalaryGrade();
                    newSal.setGrade(new Long(request.getParameter("grade")));
                    newSal.setMinsal(new Float(request.getParameter("minsal")));
                    newSal.setHisal(new Float(request.getParameter("hisal")));

                    salaryGradeBean.createSalGrade(newSal);
                    request.setAttribute("message", newSal.toString()
                            + "was created!");
                    log.info("add new " + newSal.getClass().getSimpleName());
                }
            }
        } catch (RemoteException e) {
            log.error("problems with adding", e);
        } catch (MySQLException e) {
            request.setAttribute("message", "Select table!");
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
                Object obj = new SalaryGrade();

                //Actions.fieldsDetermonation(request, obj);
                request.setAttribute("fields", salaryGradeBean.getFieldsName());
                request.setAttribute("className", obj.getClass().getSimpleName());
                request.setAttribute("table", table);
            }

            if (criterionBtn == null && (table != null || !table.isEmpty())) {

                List<SalaryGrade> tableContent = null;
                if (request.getParameter("grade") != null
                        || request.getParameter("minsal") != null
                        || request.getParameter("hisal") != null) {
                    tableContent = salaryGradeBean.selectSalaryGrade(
                            request.getParameter("grade"),
                            request.getParameter("minsal"),
                            request.getParameter("hisal"));
                } else {
                    tableContent = salaryGradeBean.showAll();
                }
                request.setAttribute("salgradeCollection", tableContent);
                log.info("show content from salgrade");
                sendMessage(Actions.DateFormat3.format(new Date()) + " show content from salgrade");

            }

            /*
             String table = request.getParameter("ShowTable");
             String criterionBtn = request.getParameter("Criterions");

             if (criterionBtn != null) {
             Object obj = new SalaryGrade();

             //Actions.fieldsDetermonation(request, obj);
             request.setAttribute("fields", salaryGradeBean.getFieldsName());
             request.setAttribute("className", obj.getClass().getSimpleName());
             request.setAttribute("table", table);
             }

             table = (table.isEmpty() || table == null ? request
             .getParameter("hiddenTableName") : table);

             if (table == null)
             throw new MySQLException("Select table!");

             List<SalaryGrade> tableContent = null;
             if (request.getParameter("grade") != null
             || request.getParameter("minsal") != null
             || request.getParameter("hisal") != null)
             tableContent = salaryGradeBean.selectSalaryGrade(
             request.getParameter("grade"),
             request.getParameter("minsal"),
             request.getParameter("hisal"));
             else
             tableContent = salaryGradeBean.showAll();
             request.setAttribute("salgradeCollection", tableContent);
             log.info("show content from salgrade");
             */        } catch (MySQLException e) {

            request.setAttribute("message", e.getMessage());
            log.error(e.getMessage(), e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " "+e.getMessage());
        } catch (RemoteException e) {
            log.error(e);
            sendMessage(Actions.DateFormat3.format(new Date()) + " "+e.getMessage());
        }
    }

    public void correctInfo(HttpServletRequest request, Logger log) {
        String url = "/correctPage.jsp";
        try {
            String correctedTable = request.getParameter("ShowTable");
            if (request.getParameter("Correct") != null) {

                String correctedId = request.getParameter("CorrectedId");

                Object corrected = null;

                corrected = salaryGradeBean.selectSalaryGrade(new Long(
                        correctedId));
                if (corrected == null) {
                    throw new IncorrectDataException("incorrect id parameter");
                }

                request.getSession().setAttribute("id", correctedId);

                /*Field[] fields = corrected.getClass().getDeclaredFields();
                 Map<String, Object> fieldValue = new HashMap<String, Object>();
                 for (Field itemF : fields) {
                 try {
                 fieldValue.put(itemF.getName(), itemF.get(corrected));
                 } catch (IllegalArgumentException e) {
                 log.error(e);
                 } catch (IllegalAccessException e) {
                 log.error(e);
                 }
                 }*/
                request.setAttribute("fields", salaryGradeBean.getFieldsName());
                request.setAttribute("correctedItem", corrected);
                request.setAttribute("table", correctedTable);
                url = "/correctPage.jsp";
            }

            String correctBtn = request.getParameter("CorrectElem");
            if (correctBtn != null) {

                if (correctBtn.contains("SalaryGrade")) {
                    correctSalGrade(request, salaryGradeBean, log);
                }
            }

        } catch (RemoteException e) {
            request.setAttribute("message", "problems to connect with EJB");
            log.error("problems to connect with EJB", e);
        } catch (IncorrectDataException e) {
            request.setAttribute("message", e.getMessage());
        }
    }

    /**
     * to update SalaryGrade object
     *
     * @param request
     * @param salaryGradeBean
     * @param log
     */
    private void correctSalGrade(HttpServletRequest request,
            SalaryGrades salaryGradeBean, Logger log) {
        SalaryGrade newSal = null;
        try {
            newSal = salaryGradeBean.selectSalaryGrade(new Long(
                    (String) request.getParameter("grade")));
            newSal.setHisal(new Float(request.getParameter("hisal")));
            newSal.setMinsal(new Float(request.getParameter("minsal")));
            salaryGradeBean.update(newSal);
            request.setAttribute("message", newSal + " was updated!");
            log.info("corrected " + newSal.getClass().getSimpleName()
                    + "with id " + newSal.getGrade());
        } catch (RemoteException e) {
            request.setAttribute("message", "problems to update salaryGrade "
                    + newSal.grade);
            log.error("problems to update salaryGrade " + newSal.grade, e);
        }
    }

    @Override
    public void sendMessage(String someMessage) {
        TextMessage message = null;
        try {
            message = ((QueueSession) getServletContext().getAttribute("session")).createTextMessage();
            message.setText(someMessage);
            ((QueueSender) getServletContext().getAttribute("sender")).send(message);
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(SalaryGradeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
