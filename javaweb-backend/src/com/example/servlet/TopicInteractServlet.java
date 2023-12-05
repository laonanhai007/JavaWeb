package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.Account;
import com.example.entity.RestBean;
import com.example.service.TopicService;
import com.example.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/forum/interact")
public class TopicInteractServlet extends HttpServlet {
    TopicService topicService = new TopicServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer tid = Integer.valueOf(req.getParameter("tid"));
        String type = req.getParameter("type");
        boolean state = Boolean.parseBoolean(req.getParameter("state"));
        Account account = (Account) req.getSession().getAttribute("account");
        if (topicService.topicInteract(tid, type, state, account.getId())) {
            resp.getWriter().write(JSON.toJSONString(RestBean.success("成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(350, "数据库异常")));
        }
    }
}
