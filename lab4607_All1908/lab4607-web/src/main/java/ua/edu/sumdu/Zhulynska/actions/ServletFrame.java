package ua.edu.sumdu.Zhulynska.actions;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import javax.jms.JMSException;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.jboss.logging.Logger;

/**
 * Servlet implementation class Actions
 */
@WebServlet("/ServletFrame")
public abstract class ServletFrame extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static final SimpleDateFormat DateFormat1 = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    public static final SimpleDateFormat DateFormat2 = new SimpleDateFormat(
            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat DateFormat3 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public abstract void removeInfo(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection);

    public abstract void addInfo(HttpServletRequest request, Logger log, ArrayList<String> logsCollection);

    public abstract void correctInfo(HttpServletRequest request, Logger log, String parameterName, ArrayList<String> logsCollection);
    
    public abstract void filterInfo(HttpServletRequest request, Logger log, String filterButton, ArrayList<String> logs)throws RemoteException;

    
    /**
     * to write logs to dataBase using MBD
     * @param someMessage - log
     */
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
