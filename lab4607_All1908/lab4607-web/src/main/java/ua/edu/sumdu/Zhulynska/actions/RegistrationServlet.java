package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import ua.edu.sumdu.Zhulynska.dates.DateClass;
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Departments;
import ua.edu.sumdu.j2ee.Zhulynska.employeeBean.Employees;
import ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean.SalaryGrades;
import ua.edu.sumdu.j2ee.Zhulynska.userBean.Users;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    QueueSession session = null;
    @EJB
    private Users userBean;
    @EJB
    private Employees empBean;
    @EJB
    private Departments departmentBean;
    @EJB
    private SalaryGrades salaryGradeBean;

    @Override
    public void init() throws ServletException {
        //super.init();

        final String QUEUE_LOOKUP = "queue/MyQueue";
        final String CONNECTION_FACTORY = "ConnectionFactory";
        try {
            Context context = new InitialContext();
            QueueConnectionFactory factory
                    = (QueueConnectionFactory) context.lookup(CONNECTION_FACTORY);
            QueueConnection connection = factory.createQueueConnection();
            session
                    = connection.createQueueSession(false,
                            QueueSession.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup(QUEUE_LOOKUP);
            QueueSender sender = session.createSender(queue);
            //TextMessage message = session.createTextMessage();

            getServletContext().setAttribute("session", session);
            getServletContext().setAttribute("sender", sender);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger log = Logger.getLogger(RegistrationServlet.class);
        RequestDispatcher r = null;
        String login1 = request.getParameter("login");
        String password1 = request.getParameter("password");
        request.setAttribute("currentUser", login1);
        ArrayList<String> logsCollection = (ArrayList<String>) request.getSession().getAttribute("logsCollection");

        if (request.getParameter("LogIn") != null) {
            if (userBean.findByName(login1, password1)) {
                log.info("user " + login1 + " is successfully logged");
                sendMessage(ServletFrame.DateFormat3.format(new Date()) + " user " + login1 + " is successfully logged");

                logsCollection.add("user " + login1 + " is successfully logged");

                sendBeans(request, log);

                r = getServletContext().getRequestDispatcher("/default.jsp");
            } else {
                r = getServletContext().getRequestDispatcher("/loginForm.jsp");
                request.setAttribute("message", "user " + login1 + " doesn't logged");
                log.info(ServletFrame.DateFormat3.format(new Date()) + " user " + login1 + " doesn't logged");
                logsCollection.add(new Date() + "user " + login1 + " doesn't logged");
                sendMessage(ServletFrame.DateFormat3.format(new Date()) + "user " + login1 + " doesn't logged");
            }
        }
        if (request.getParameter("Registration") != null) {
            r = getServletContext().getRequestDispatcher("/registryForm.jsp");
        }
        if (request.getParameter("RegistryNew") != null) {
            String firstName = request.getParameter("firstNameNew");
            String surname = request.getParameter("surnameNew");
            String loginNew = request.getParameter("loginNew");
            String passwordNew = request.getParameter("passwordNew");
            try {
                userBean.registryUser(firstName, surname, loginNew, passwordNew);
            } catch (Exception e) {
                request.setAttribute("message", "incorrect parameters!");
                logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + "incorrect registration parameters!");
            }
            logsCollection.add(ServletFrame.DateFormat3.format(new Date()) + " user " + loginNew + "is registered");
            log.info("user " + loginNew + "is registered");
            sendMessage(ServletFrame.DateFormat3.format(new Date()) + " user " + loginNew + "is registered");
            r = getServletContext().getRequestDispatcher("/loginForm.jsp");
        }
        r.forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            session.close();
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    public void sendBeans(HttpServletRequest request, Logger log) {
        request.getSession().setAttribute("depts", departmentBean);
        request.getSession().setAttribute("salaryGradeBean", salaryGradeBean);
        //request.getSession().setAttribute("MDB", mdb);
        request.getSession().setAttribute("empBean", empBean);
        request.getSession().setAttribute("days", DateClass.getDays());
        request.getSession().setAttribute("months", DateClass.getMonths());
        request.getSession().setAttribute("years", DateClass.getYears());

    }

}
