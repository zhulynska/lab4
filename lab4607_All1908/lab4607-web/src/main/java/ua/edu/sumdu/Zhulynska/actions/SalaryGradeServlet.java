package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.rmi.RemoteException;
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
import ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean.SalaryGrade;
import ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean.SalaryGrades;

/**
 * Servlet implementation class SalaryGradeServlet
 */
@WebServlet("/SalaryGradeServlet")
public class SalaryGradeServlet extends ServletFrame {

    private static final long serialVersionUID = 1L;
    @EJB
    private SalaryGrades salaryGradeBean;

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Logger log = null;
        String pageName = "salary.jsp";
        String paramName = null;
        ArrayList<String> logsCollection = null;

        try {
            logsCollection = (ArrayList<String>) request.getSession().getAttribute("logsCollection");
            log = Logger.getLogger(SalaryGradeServlet.class);
            Enumeration parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                paramName = (String) parameterNames.nextElement();

                if (paramName.contains("Remove")) {
                    if (request.getParameter(paramName) != null) {
                        removeInfo(request, log, paramName, logsCollection);
                    }
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

                if (request.getParameter(paramName).contains("Correct") && request.getParameter(paramName) != null) {
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
        List<SalaryGrade> collection = null;
        if (request.getParameter("gradeSelect") != null
                || request.getParameter("minsalSelect") != null
                || request.getParameter("hisalSelect") != null) {
            collection = salaryGradeBean.selectSalaryGrade(
                    request.getParameter("gradeSelect"),
                    request.getParameter("minsalSelect"),
                    request.getParameter("hisalSelect"));

            request.setAttribute("collection", collection);
        }
    }

    public void correctInfo(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection) {
        String salGr = parameterName.substring(7);
        SalaryGrade corrected = salaryGradeBean.selectSalaryGrade(new Long(salGr));
        corrected.setHisal(new Float(request.getParameter("hisal" + salGr)));
        corrected.setMinsal(new Float(request.getParameter("minsal" + salGr)));
        salaryGradeBean.update(corrected);

        request.setAttribute("message", "SalaryGrade " + salGr + " was updated");
        log.info("SalaryGrade " + salGr + " was updated");
        sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "SalaryGrade " + salGr + " was updated");
        logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + "SalaryGrade " + salGr + " was updated");
    }

    public void removeInfo(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection) {
        Long sal = new Long(parameterName.substring(6));
        salaryGradeBean.deleteSalaryGrade(sal);
        request.getSession().setAttribute("message", "component with id " + sal
                + " removed");
        log.info("SalaryGrade " + sal + " was removed!");
        request.setAttribute("message", "SalaryGrade " + sal + " was removed!");
        sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "SalaryGrade " + sal + " was removed!");
        logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + "SalaryGrade " + sal + " was removed!");
    }

    public void addInfo(HttpServletRequest request, Logger log, ArrayList<String> logsCollection) {
        SalaryGrade newSal = new SalaryGrade();
        String salId = request.getParameter("grade");
        newSal.setGrade(new Long(salId));
        newSal.setMinsal(new Float(request.getParameter("minsal")));
        newSal.setHisal(new Float(request.getParameter("hisal")));

        ArrayList<SalaryGrade> all = (ArrayList) salaryGradeBean.showAll();
        boolean isHere = false;
        for (SalaryGrade sal : all) {
            if (sal.grade.equals(new Long(salId))) {
                isHere = true;
            }
        }
        if (!isHere) {
            salaryGradeBean.createSalGrade(newSal);
            request.setAttribute("message", newSal.toString()
                    + "was created!");
            log.info("add new " + "SalaryGrade " + salId + " was added!");
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " " + "SalaryGrade " + salId + " was added!");
            logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " " + "SalaryGrade " + salId + " was added!");
        }
    }
}
