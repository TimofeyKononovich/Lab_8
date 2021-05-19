package main.java.bsu.frct.java.lab8.servlet;

import bsu.frct.java.lab8.entity.ChatMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageListServlet extends ChatServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");

        PrintWriter pw = response.getWriter();
        pw.println("<!DOCTYPE html>\n<html><head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>" +
                "<meta http-equiv='refresh' content='10'></head>");
        pw.println("<body>");
        pw.println("<div><strong>" + request.getSession().getAttribute("name") +": Online"+ "</strong></div>");

        for (int i = messages.size() - 1; i >= 0; i--) {
            ChatMessage aMessage = messages.get(i);
            String privateMessage = messages.get(i).getPrivateMessage();
            String author = messages.get(i).getAuthor().getName();
            String uname = (String) request.getSession().getAttribute("name");

            if(privateMessage != null){
                if(privateMessage.equals(uname)||author.equals(uname)){
                    pw.println("<div><strong>" + aMessage.getAuthor().getName()
                            + "</strong>: " + aMessage.getMessage() + "</div>");
                }

            }else{
                pw.println("<div><strong>" + aMessage.getAuthor().getName()
                        + "</strong>: " + aMessage.getMessage() + "</div>");
            }
        }

        pw.println("</body></html>");
    }
}
