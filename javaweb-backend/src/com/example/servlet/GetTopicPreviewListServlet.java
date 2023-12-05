package com.example.servlet;

import com.alibaba.fastjson2.JSON;
import com.example.entity.RestBean;
import com.example.entity.vo.TopicPreviewVo;
import com.example.service.TopicService;
import com.example.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/forum/list-topic")
public class GetTopicPreviewListServlet extends HttpServlet {
    TopicService topicService =new TopicServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer page = Integer.valueOf(req.getParameter("page"));
        Integer type = Integer.valueOf(req.getParameter("type"));
        List<TopicPreviewVo> topicPreviewVos = topicService.listTopicPreviewVos(page, type);
        resp.getWriter().write(JSON.toJSONString(RestBean.success(topicPreviewVos)));
    }
}
