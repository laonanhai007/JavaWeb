package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.entity.AccountDetails;
import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.vo.TopicCollectPreviewVo;
import com.example.entity.vo.TopicDetailsVo;
import com.example.entity.vo.TopicInteractVo;
import com.example.entity.vo.TopicPreviewVo;
import com.example.mapper.AccountMapper;
import com.example.mapper.TopicMapper;
import com.example.mapper.impl.AccountMapperImpl;
import com.example.mapper.impl.TopicMapperImpl;
import com.example.service.TopicService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TopicServiceImpl implements TopicService {
    TopicMapper topicMapper = new TopicMapperImpl();
    AccountMapper accountMapper = new AccountMapperImpl();

    @Override
    public List<TopicType> listTopicTypes() {
        return topicMapper.selectTopicTypes();
    }

    @Override
    public String createTopic(Topic topic) {
        topic.setCreateTime(new Date());
        topic.setUpdateTime(new Date());
        if (topicMapper.insertTopic(topic)) {
            return null;
        } else {
            return "数据库异常";
        }
    }

    @Override
    public List<TopicPreviewVo> listTopicPreviewVos(Integer page, Integer type) {
        List<Topic> topicList;
        if (type == 0) {
            topicList = topicMapper.selectTopics(page);
        } else {
            topicList = topicMapper.selectTopicsByType(type, page);
        }
        return topicList.stream().map(topic -> {
            TopicPreviewVo topicPreviewVo = new TopicPreviewVo();
            topicPreviewVo.setId(topic.getId());
            topicPreviewVo.setTitle(topic.getTitle());
            topicPreviewVo.setContent(getPreviewContent(topic.getContent()));
            topicPreviewVo.setType(topic.getType());
            topicPreviewVo.setCreateTime(topic.getCreateTime());
            topicPreviewVo.setAccount(accountMapper.selectAccountPreviewVoById(topic.getUid()));
            topicPreviewVo.setLike(topicMapper.selectCountTopicInteractById("like", topic.getId()));
            topicPreviewVo.setCollect(topicMapper.selectCountTopicInteractById("collect", topic.getId()));
            return topicPreviewVo;
        }).collect(Collectors.toList());
    }

    @Override
    public TopicDetailsVo getTopicDetails(Integer tid, Integer id) {
        TopicDetailsVo topicDetailsVo = new TopicDetailsVo();
        Topic topic = topicMapper.selectTopicById(tid);
        AccountDetails accountDetails = accountMapper.selectAccountDetailsByUid(topic.getUid());
        topicDetailsVo.setTopic(topic);
        topicDetailsVo.setAccount(accountDetails);
        return topicDetailsVo;
    }

    @Override
    public TopicInteractVo getTopicInteract(Integer tid, Integer id) {
        TopicInteractVo topicInteractVo = new TopicInteractVo();
        topicInteractVo.setCollect(topicMapper.selectTopicInteractByTidAndUid(tid, id, "collect"));
        topicInteractVo.setLike(topicMapper.selectTopicInteractByTidAndUid(tid, id, "like"));
        return topicInteractVo;
    }

    @Override
    public String updateTopic(Topic topic) {

        if (topicMapper.updateTopicById(topic)) {
            return null;
        } else {
            return "数据库异常";
        }
    }

    @Override
    public List<TopicCollectPreviewVo> getTopicCollectList(Integer uid) {
        return topicMapper.selectCollectTopicsByUid(uid);
    }

    @Override
    public boolean topicInteract(Integer tid, String type, boolean state, Integer uid) {
        boolean a = false;
        if (state) {
            a = topicMapper.insertInteract(tid, uid, type);
        } else {
            a = topicMapper.deleteInteract(tid, uid, type);
        }
        return a;
    }


    private String getPreviewContent(JSONObject content) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray ops = content.getJSONArray("ops");
        for (Object json : ops) {
            JSONObject jsonObject = (JSONObject) json;
            String str = (String) jsonObject.get("insert");
            str = str.replaceAll("\n", "");
            str = str.trim();
            if (str.length() + stringBuilder.length() > 100) {
                int less = str.length() + stringBuilder.length() - 100;
                str = str.substring(0, str.length() - less - 3);
                stringBuilder.append(str);
                stringBuilder.append("...");
                break;
            }
            stringBuilder.append(str);
        }
        return String.valueOf(stringBuilder);
    }


}
