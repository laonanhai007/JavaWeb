package com.example.service;

import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.vo.TopicCollectPreviewVo;
import com.example.entity.vo.TopicDetailsVo;
import com.example.entity.vo.TopicInteractVo;
import com.example.entity.vo.TopicPreviewVo;

import java.util.List;

public interface TopicService {
    List<TopicType> listTopicTypes();

    String createTopic(Topic topic);

    List<TopicPreviewVo> listTopicPreviewVos(Integer page, Integer type);

    TopicDetailsVo getTopicDetails(Integer tid, Integer id);

    TopicInteractVo getTopicInteract(Integer tid, Integer id);

    String updateTopic(Topic topic);

    List<TopicCollectPreviewVo> getTopicCollectList(Integer uid);

    boolean topicInteract(Integer tid, String type, boolean state, Integer uid);
}
