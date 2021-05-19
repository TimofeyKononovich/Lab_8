package main.java.bsu.frct.java.lab8.servlet;

import bsu.frct.java.lab8.entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserListServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");

        PrintWriter pw = response.getWriter();
        Integer count = activeUsers.size();
        pw.println("<!DOCTYPE html>\n<html><head>" +
                "<meta charset='UTF-8' />" +
                "<meta http-equiv='refresh' content='5'>" +
                "</head>");
        pw.println("<body>");
        pw.println("<style type=\"text/css\">"+
                "SELECT { width: 100%;} </style>");
        pw.println("<form id = mform action=\"/Lab_8/users.do\" method=\"post\">");
        pw.println("<select size=\""+new Integer(count.intValue()+2).toString()+"\" name=\"userlist\" " +
                "onchange=\"javascript:document.forms['mform'].submit();\">");
        pw.println("<option value = 'to all'>All</option>");

        String uname = (String) request.getSession().getAttribute("name");

        for (ChatUser aUser: activeUsers.values()) {
            if (aUser.getName().equals(uname)) {
                continue;
            }
            pw.println("<option>"+aUser.getName()+"</option>");
        }

        pw.println("</select>");
        pw.println("</form>");

        String privateMessage = (String)request.getSession().getAttribute("privatem");
        boolean b = false;

        for (ChatUser aUser: activeUsers.values()) {
            if (aUser.getName().equals(privateMessage)) {
                b = true;
            }
        }

        if (!b) {
            privateMessage="to all";
        }
        String m="You send message ";
        if(privateMessage==null || "to all".equals(privateMessage)) {
            m +=" All";
        } else {
            m += ("\nto user: " + privateMessage);
        }

        pw.println(m);
        pw.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String privateMessage = request.getParameter("userlist");
        request.getSession().setAttribute("privatem", privateMessage);
        doGet(request, response);
    }
}