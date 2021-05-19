package main.java.bsu.frct.java.lab8.servlet;

import bsu.frct.java.lab8.entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ViewServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");
        boolean b=false;

        for (ChatUser aUser: activeUsers.values()) {
            if(aUser.getName().equals(request.getSession().getAttribute("name"))){
                b = true;
            }
        }

        if (!b) {
            response.sendRedirect(response.encodeRedirectURL("/Lab_8/login.do"));
        }

        PrintWriter pw = response.getWriter();
        pw.println("<!DOCTYPE html>\n<html>" +
                "<head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>" +
                "<title>Mega-chat: Message</title>" +
                "</head>");
        pw.println( "<frameset rows=\"90,10\">" +
                "<frameset cols=\"80,20\">"+
                "<frame name=\"frameMessages\" src=\"/Lab_8/messages.do\">"+
                "<frame name=\"frameUsers\" src=\"/Lab_8/users.do\" noresize>" +
                "</frameset>"+
                "<frame name=\"frameMessage\" src=\"/Lab_8/compose_message.html\" noresize>"+
                "</frameset>");
        pw.println("</html>");

    }

}
