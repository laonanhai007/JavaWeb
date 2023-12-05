package com.example.mapper;

import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.vo.TopicCollectPreviewVo;
import com.example.entity.vo.TopicPreviewVo;

import java.util.List;

public interface TopicMapper {
    List<TopicType> selectTopicTypes();

    boolean insertTopic(Topic topic);

    List<Topic> selectTopics(Integer page);

    List<Topic> selectTopicsByType(Integer type, Integer page);

    int selectCountTopicInteractById(String interact,Integer id);

    Topic selectTopicById(Integer tid);

    boolean selectTopicInteractByTidAndUid(Integer tid, Integer id, String s);

    boolean updateTopicById(Topic topic);

    List<TopicCollectPreviewVo> selectCollectTopicsByUid(Integer uid);

    boolean insertInteract(Integer tid, Integer uid, String type);

    boolean deleteInteract(Integer tid, Integer uid, String type);
}
