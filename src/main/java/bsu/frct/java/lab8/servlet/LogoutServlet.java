package main.java.bsu.frct.java.lab8.servlet;

import bsu.frct.java.lab8.entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("name");

        if (name != null) {
            ChatUser user = activeUsers.get(name);

            if (user.getSessionId().equals(request.getSession().getId())) {
                synchronized (activeUsers) {
                    activeUsers.remove(name);
                }

                request.getSession().setAttribute("name", null);
                response.addCookie(new Cookie("sessionId", null));
                response.sendRedirect(response.encodeRedirectURL("/Lab_8/"));
            } else {
                response.sendRedirect(response.encodeRedirectURL("/Lab_8/view.html"));
            }

        } else {
            response.sendRedirect(response.encodeRedirectURL("/Lab_8/view.html"));
        }

    }

}
