package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.RestBean;
import com.example.service.AccountService;
import com.example.service.impl.AccountServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/auth/login")
public class AccountLoginServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String text = req.getParameter("text");
        String password = req.getParameter("password");
        String message = accountService.login(text, password, req);
        if (message == null) {
            resp.getWriter().write(JSON.toJSONString(RestBean.success("登录成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(401, message)));
        }
    }
}
