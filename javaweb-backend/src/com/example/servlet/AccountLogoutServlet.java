package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.RestBean;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/account/logout")
public class AccountLogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("account");
        resp.getWriter().write(JSON.toJSONString(RestBean.success("退出登录成功")));
    }
}
