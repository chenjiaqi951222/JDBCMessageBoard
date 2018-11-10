package com.imooc.jdbc.filter;

import com.imooc.jdbc.bean.User;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        User user = (User)((HttpServletRequest)req).getSession().getAttribute("user");
        req.setAttribute("user",user);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
