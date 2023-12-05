package com.example.test;

import com.example.entity.vo.TopicDetailsVo;
import com.example.entity.vo.TopicPreviewVo;
import com.example.mapper.AccountMapper;
import com.example.mapper.TopicMapper;
import com.example.mapper.impl.AccountMapperImpl;
import com.example.mapper.impl.TopicMapperImpl;
import com.example.service.TopicService;
import com.example.service.impl.TopicServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MainTest {
    AccountMapper accountMapper = new AccountMapperImpl();
    TopicMapper topicMapper = new TopicMapperImpl();
    TopicService topicService = new TopicServiceImpl();

    @Test
    public void test() {
        List<TopicPreviewVo> topicPreviewVos = topicService.listTopicPreviewVos(1, 0);
        System.out.println(topicPreviewVos);

    }

    @Test
    public void test2() {
        TopicDetailsVo topicDetails = topicService.getTopicDetails(1, 1);
        System.out.println("topicDetails = " + topicDetails);
    }

    @Test
    public void test3() {
        System.out.println(topicMapper.selectTopicInteractByTidAndUid(1, 1, "like"));
    }

    @Test
    public void test4() {
        System.out.println(topicMapper.insertInteract(1, 3, "collect"));
    }


    @Test
    public void test5() {
        System.out.println(topicMapper.deleteInteract(1, 3, "collect"));
    }
}
