package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.Account;
import com.example.entity.AccountDetails;
import com.example.entity.RestBean;
import com.example.service.AccountService;
import com.example.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/account/save")
public class SaveAccountDetailsServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDetails accountDetails = new AccountDetails();
        Account account = (Account) req.getSession().getAttribute("account");
        accountDetails.setUid(account.getId());
        accountDetails.setNickName(req.getParameter("nickName"));
        accountDetails.setGender(Integer.valueOf(req.getParameter("gender")));
        accountDetails.setQq(req.getParameter("qq"));
        accountDetails.setWx(req.getParameter("wx"));
        accountDetails.setBlog(req.getParameter("blog"));
        accountDetails.setSpecialty(req.getParameter("specialty"));
        accountDetails.setGrade(req.getParameter("grade"));
        accountDetails.setPhone(req.getParameter("phone"));
        accountDetails.setDesc(req.getParameter("desc"));
        String msg = accountService.saveAccountDetails(accountDetails);
        if (msg == null) {
            resp.getWriter().write(JSON.toJSONString(RestBean.success("保存用户信息成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(398, msg)));
        }

    }
}
