package main.java.bsu.frct.java.lab8.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class LoginServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;
    private int sessionTimeout = 10 * 60;

    @Override
    public void init() throws ServletException {
        super.init();

        String value = getServletConfig().getInitParameter("SESSION_TIMEOUT");
        if (value != null) {
            sessionTimeout = Integer.parseInt(value);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("name");
        String errorMessage = (String) request.getSession().getAttribute("error");
        String previousSessionId = null;

        if (name == null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("sessionId")) {
                    previousSessionId = cookie.getValue();
                    break;
                }
            }

            if (previousSessionId != null) {
                for (ChatUser user : activeUsers.values()) {
                    if (user.getSessionId().equals(previousSessionId)) {
                        name = user.getName();
                        user.setSessionId(request.getSession().getId());
                    }
                }
            }
        }

        if (name != null && !"".equals(name)) {
            errorMessage = processLogonAttempt(name, request, response);
        }

        response.setCharacterEncoding("utf8");
        PrintWriter pw = response.getWriter();
        pw.println("<html><head><title>Mega-chat!</title>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>");

        if (errorMessage != null) {
            pw.println("<p><font color='red'>" + errorMessage + "</font></p>");
        }

        pw.println("<form action='/Lab_8/' method='post'>Enter name:" +
                "<input type='text' name='name' value=''>" +
                "<input type='submit' value='Log in'>");
        pw.println("</form></body></html>");

        request.getSession().setAttribute("error", null);

    }
    @Override
    protected void doPost(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String errorMessage;

        if (name == null || "".equals(name)) {
            errorMessage = "Username cannot be empty!";
        } else {
            errorMessage = processLogonAttempt(name, request, response);
        }

        if (errorMessage != null) {
            request.getSession().setAttribute("name", null);
            request.getSession().setAttribute("error", errorMessage);
            response.sendRedirect(response.encodeRedirectURL("/Lab_8/"));
        }

    }
    String processLogonAttempt(String name, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String sessionId = request.getSession().getId();
        ChatUser user = activeUsers.get(name);

        if (user == null) {
            user = new ChatUser(name, Calendar.getInstance().getTimeInMillis(), sessionId);

            synchronized (activeUsers) {
                activeUsers.put(user.getName(), user);
            }
        }

        if (user.getSessionId().equals(sessionId) ||
                user.getLastInteractionTime()<(Calendar.getInstance().getTimeInMillis()- sessionTimeout*1000)) {

            request.getSession().setAttribute("name", name);
            user.setLastInteractionTime(Calendar.getInstance().getTimeInMillis());

            Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
            sessionIdCookie.setMaxAge(60*60*24*365);
            response.addCookie(sessionIdCookie);

            request.getSession().setAttribute("privatem", null);
            response.sendRedirect(response.encodeRedirectURL("/Lab_8/view.do"));

            return null;

        } else {
            return "Sorry, but name <strong>" + name + "</strong> already " +
                    "occupied by someone. Please choose another name!";
        }

    }
}