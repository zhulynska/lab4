package ua.edu.sumdu.Zhulynska.actions;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

/**
 * Servlet implementation class Actions
 */
@WebServlet("/Actions")
public class Actions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final SimpleDateFormat DateFormat1 = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);
	public static final SimpleDateFormat DateFormat2 = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        public static final SimpleDateFormat DateFormat3 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String table = request.getParameter("ShowTable");
		String tableHidden = request.getParameter("ShowTable"); // hidden

		String criterionBtn = request.getParameter("Criterions");

		String redirectPage = request.getParameter("pageName");
		try {
			if (table.isEmpty() || table == null)
				throw new MySQLException("Select table!");

			if (criterionBtn != null) {

				if (table.isEmpty())
					throw new MySQLException("Select table!");
			}

			redirectPage = (table.equals("salgrade") || tableHidden
					.equals("salgrade")) ? "SalaryGradeServlet" : redirectPage;
			redirectPage = (table.equals("emp") || tableHidden.equals("emp")) ? "EmployeeServlet"
					: redirectPage;
			redirectPage = (table.equals("dept") || tableHidden.equals("dept")) ? "DepartmentServlet"
					: redirectPage;

		} catch (MySQLException e) {
			request.setAttribute("message", e.getMessage());
		}
		getServletContext().getRequestDispatcher("/" + redirectPage).forward(
				request, response);
	}

	static void controllerActions(String pageName, ActionsFrame obj, HttpServletRequest request,
			HttpServletResponse response, Logger log) {
		//String pageName = request.getParameter("pageName");
		if (pageName.equals("defaultPage.jsp")) {
			obj.showAll(request, log);
		}
		if (pageName.equals("addPage.jsp")) {
			obj.addInfo(request, log);
		}
		if (pageName.equals("correctPage.jsp")) {
			obj.correctInfo(request, log);
		}
		if (pageName.equals("removePage.jsp")) {
			obj.removeInfo(request, log);
		}
	}

	
	/*static void fieldsDetermonation(HttpServletRequest request, Object obj) {

		Field[] f = obj.getClass().getDeclaredFields();
		request.setAttribute("fields", f);
		request.setAttribute("className", obj.getClass().getSimpleName());
	}*/
}
