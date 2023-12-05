package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.RestBean;
import com.example.service.AccountService;
import com.example.service.impl.AccountServiceImpl;
import com.example.util.Const;
import jakarta.mail.MessagingException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Random;

@WebServlet("/api/auth/email-code")
public class SendEmailRegisterCodeServlet extends HttpServlet {
    AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        String msg = null;
        try {
            msg = accountService.sendEmailCode(email, code, false);
        } catch (MessagingException | GeneralSecurityException e) {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(500, "邮件发送异常,请联系管理员")));
        }
        if (msg == null) {
            req.getSession().setAttribute(email + Const.EMAIL_CODE, String.valueOf(code));
            resp.getWriter().write(JSON.toJSONString(RestBean.success("邮件已发送,请及时查收")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(399, msg)));
        }
    }
}
