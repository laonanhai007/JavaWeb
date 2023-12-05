package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.entity.Account;
import com.example.entity.RestBean;
import com.example.entity.Topic;
import com.example.service.TopicService;
import com.example.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

@WebServlet("/api/forum/update-topic")
public class UpdateTopicServlet extends HttpServlet {
    TopicService topicService = new TopicServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        String title = req.getParameter("title");
        JSONObject content = JSONObject.parseObject(req.getParameter("content"));
        String type = req.getParameter("type");
        Account account = (Account) req.getSession().getAttribute("account");
        Topic topic = new Topic();
        topic.setId(id);
        topic.setUid(account.getId());
        topic.setTitle(title);
        topic.setType(Integer.valueOf(type));
        topic.setContent(content);
        topic.setUpdateTime(new Date());
        String msg = topicService.updateTopic(topic);
        if (msg == null) {
            resp.getWriter().write(JSON.toJSONString(RestBean.success("更新帖子成功")));
        } else {
            resp.getWriter().write(JSON.toJSONString(RestBean.failure(350, msg)));
        }
    }
}
