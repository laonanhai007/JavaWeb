package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.Account;
import com.example.entity.RestBean;
import com.example.service.AccountService;
import com.example.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/account/update-password")
public class UpdatePasswordServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        Account account = (Account) req.getSession().getAttribute("account");
        String msg = accountService.updatePassword(oldPassword, newPassword, account);
        if (msg == null) {
            resp.getWriter().write(JSON.toJSONString(RestBean.success("更新密码成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(380, msg)));
        }
    }
}
