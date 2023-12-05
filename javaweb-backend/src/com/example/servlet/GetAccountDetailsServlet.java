package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.Account;
import com.example.entity.AccountDetails;
import com.example.entity.RestBean;
import com.example.service.AccountService;
import com.example.service.impl.AccountServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/account/me")
public class GetAccountDetailsServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Account account = (Account) req.getSession().getAttribute("account");
        AccountDetails accountDetails = accountService.getAccountDetails(account);
        resp.getWriter().write(JSON.toJSONString(RestBean.success(accountDetails)));
    }
}
