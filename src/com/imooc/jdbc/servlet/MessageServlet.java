package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.MessageService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 留言处理
 */
public class MessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private MessageService messageService;
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.service(req, res);
        String pathName = req.getServletPath();
        if (Objects.equals("/addMessagePrompt.do", pathName)) {
            req.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(req, res);
        } else if (Objects.equals("/addMessage.do", pathName)) {
            User user = (User) req.getSession().getAttribute("user");
            if (null == user) {
                req.getRequestDispatcher("/message/list.do").forward(req, res);
            } else {
                String title = req.getParameter("title");
                String content = req.getParameter("content");
                boolean result = messageService.addMessage(new Message(user.getId(),user.getName(),title,content));
                if (result) {
                    req.getRequestDispatcher("/message/list.do").forward(req, res);
                } else {
                    req.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(req, res);
                }
            }
        } else {
            req.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, res);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        messageService = new MessageService();
    }
}
