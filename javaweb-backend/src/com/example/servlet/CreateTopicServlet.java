package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.entity.Account;
import com.example.entity.RestBean;
import com.example.entity.Topic;
import com.example.service.TopicService;
import com.example.service.impl.TopicServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/forum/create-topic")
public class CreateTopicServlet extends HttpServlet {
    TopicService topicService = new TopicServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        JSONObject content = JSONObject.parseObject(req.getParameter("content"));
        String type = req.getParameter("type");
        Account account = (Account) req.getSession().getAttribute("account");
        Topic topic = new Topic();
        topic.setUid(account.getId());
        topic.setTitle(title);
        topic.setType(Integer.valueOf(type));
        topic.setContent(content);
        String msg = topicService.createTopic(topic);
        if (msg == null) {
            resp.getWriter().write(JSON.toJSONString(RestBean.success("发表文章成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(378, msg)));
        }
    }
}
