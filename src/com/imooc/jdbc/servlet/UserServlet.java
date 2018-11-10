package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Objects;

/**
 * 用户Servlet
 */
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private UserService userService = new UserService();

    @Override
    public void init() throws ServletException {
        super.init();

    }

    /**
     * 显示和修改用户信息的Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathName = request.getServletPath();
        //显示用户信息
        if (Objects.equals("/userInfo.do", pathName)) {
            request.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(request, response);
        } else if (Objects.equals("/editUserPrompt.do", pathName)) {
            //1.从session中获取，其实就是与上一个if同样的方式
            //2.可扩展的使用方式:根据页面ID从数据库中查询
            Long id = Long.valueOf(request.getParameter("id"));
            User user = userService.getUserById(id);
            if (null != user) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/biz/edit_user.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
            }
        } else if (Objects.equals("/editUser.do", pathName)) {
            Long id = Long.valueOf(request.getParameter("id"));
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String realName = request.getParameter("realName");
            String birthday = request.getParameter("birthday");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setPassword(password);
            user.setRealName(realName);
            try {
                user.setBirthday(new Date(new SimpleDateFormat("yyyy-mm-dd").parse(birthday).getTime()));
            } catch (ParseException e) {
                System.out.println("格式化Birthday字段失败");
                e.printStackTrace();
            }
            user.setPhone(phone);
            user.setAddress(address);
            boolean result = userService.updateUser(user);
            if (result) {
                if (null != user) {
                    request.getSession().setAttribute("user",user);
                    request.setAttribute("user",user);
                    request.getRequestDispatcher("/WEB-INF/views/biz/edit_user.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }
    }
}
