package com.example.filter;

import com.alibaba.fastjson2.JSON;
import com.example.entity.Account;
import com.example.entity.RestBean;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/javaweb/api/auth")) {
            filterChain.doFilter(request, response);
        } else {
            Account account = (Account) request.getSession().getAttribute("account");
            if (account == null) {
                response.getWriter().write(JSON.toJSONString(RestBean.failure(401, "未登录")));
                return;
            }
            filterChain.doFilter(request, response);
        }
    }
}
