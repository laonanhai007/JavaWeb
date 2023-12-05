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

@WebServlet("/api/account/update-email")
public class UpdateEmailServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        Account account = (Account) req.getSession().getAttribute("account");
        String msg = accountService.updateEmail(email, account.getId());
        if (msg == null) {
            resp.getWriter().write(JSON.toJSONString(RestBean.success("更新邮箱成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(380, msg)));
        }
    }
}
