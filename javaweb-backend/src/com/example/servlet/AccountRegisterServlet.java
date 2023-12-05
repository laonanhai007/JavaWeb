package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.Account;
import com.example.entity.RestBean;
import com.example.service.AccountService;
import com.example.service.impl.AccountServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/auth/register")
public class AccountRegisterServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String code = req.getParameter("code");
        Account account = new Account(username, password, email);
        String msg = accountService.register(account, code, req);
        if (msg == null) {
            req.getSession().removeAttribute(email + "code");
            resp.getWriter().write(JSON.toJSONString(RestBean.success("注册成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(402, msg)));
        }
    }
}
