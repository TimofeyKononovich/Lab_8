package main.java.bsu.frct.java.lab8.servlet;

import bsu.frct.java.lab8.entity.ChatMessage;
import bsu.frct.java.lab8.entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class NewMessageServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = null;
        String message = request.getParameter("message");

        if (message != null && !"".equals(message)) {
            String privatem = (String) request.getSession().getAttribute("privatem");

            if(privatem != null && !"toall".equals(privatem)) {
                name = privatem;
            }

            ChatUser author = activeUsers.get(request.getSession().getAttribute("name"));
            synchronized (messages) {
                messages.add(new ChatMessage(message, author,
                        Calendar.getInstance().getTimeInMillis(),name));
            }
        }

        response.sendRedirect("/Lab_8/compose_message.html");
    }

}