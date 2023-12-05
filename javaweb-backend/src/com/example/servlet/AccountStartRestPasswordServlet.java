package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.RestBean;
import com.example.service.AccountService;
import com.example.service.impl.AccountServiceImpl;
import com.example.util.Const;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/auth/start-reset")
public class AccountStartRestPasswordServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        String msg = accountService.startResetPassword(email, code, req);
        if (msg == null) {
            req.getSession().removeAttribute(email + Const.RESET_CODE);
            req.getSession().setAttribute("email", email);
            resp.getWriter().write(JSON.toJSONString(RestBean.success()));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(399, msg)));
        }
    }
}
