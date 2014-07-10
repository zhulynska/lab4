package ua.edu.sumdu.Zhulynska.actions;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

public interface ActionsFrame {

/**
 * used to set a list of neccessary actions	in servlets
 * @param request
 * @param log
 */
	void removeInfo(HttpServletRequest request, Logger log);
	void addInfo(HttpServletRequest request, Logger log);
	void showAll(HttpServletRequest request, Logger log);
	void correctInfo(HttpServletRequest request, Logger log);
        void sendMessage(String message);
}
