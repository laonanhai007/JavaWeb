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


@WebServlet("/api/auth/end-reset")
public class AccountEndRestPasswordServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String password = req.getParameter("password");
        String email = (String) req.getSession().getAttribute("email");
        String msg = accountService.endRestPassword(email, password);
        if (msg == null) {
            req.getSession().removeAttribute("email");
            resp.getWriter().write(JSON.toJSONString(RestBean.success("更改密码成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(398, msg)));
        }
    }
}
