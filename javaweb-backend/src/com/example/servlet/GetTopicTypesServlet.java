package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.RestBean;
import com.example.entity.TopicType;
import com.example.service.TopicService;
import com.example.service.impl.TopicServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/forum/types")
public class GetTopicTypesServlet extends HttpServlet {
    TopicService topicService = new TopicServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<TopicType> listTopicTypes = topicService.listTopicTypes();
        resp.getWriter().write(JSON.toJSONString(RestBean.success(listTopicTypes)));
    }
}
