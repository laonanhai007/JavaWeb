package com.example.mapper.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.Topic;
import com.example.entity.TopicType;
import com.example.entity.vo.TopicCollectPreviewVo;
import com.example.mapper.TopicMapper;
import com.example.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopicMapperImpl implements TopicMapper {
    @Override
    public List<TopicType> selectTopicTypes() {
        List<TopicType> list = new ArrayList<>();
        String sql = "select id, name, `desc`, color from topic_type";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TopicType topicType = new TopicType();
                topicType.setId(resultSet.getInt(1));
                topicType.setName(resultSet.getString(2));
                topicType.setDesc(resultSet.getString(3));
                topicType.setColor(resultSet.getString(4));
                list.add(topicType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean insertTopic(Topic topic) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "insert into topic(uid, title, content, type, create_time, update_time) VALUES (?,?,?,?,?,?) ";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, topic.getUid());
            preparedStatement.setString(2, topic.getTitle());
            preparedStatement.setString(3, String.valueOf(topic.getContent()));
            preparedStatement.setInt(4, topic.getType());
            preparedStatement.setString(5, dateFormat.format(topic.getCreateTime()));
            preparedStatement.setString(6, dateFormat.format(topic.getUpdateTime()));
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement, connection);
            return i > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> selectTopics(Integer page) {
        List<Topic> topicList = new ArrayList<>();
        String sql = "select * from topic limit ?,10";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (page - 1) * 10);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Topic topic = new Topic();
                topic.setId(resultSet.getInt(1));
                topic.setUid(resultSet.getInt(2));
                topic.setTitle(resultSet.getString(3));
                String content = resultSet.getString(4);
                topic.setContent(JSONObject.parse(content));
                topic.setType(resultSet.getInt(5));
                topic.setCreateTime(resultSet.getDate(6));
                topic.setUpdateTime(resultSet.getDate(7));
                topicList.add(topic);
            }
            JDBCUtil.close(resultSet, preparedStatement, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return topicList;
    }

    @Override
    public List<Topic> selectTopicsByType(Integer type, Integer page) {
        List<Topic> topicList = new ArrayList<>();
        String sql = "select * from topic where type = ? limit ?,10";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, type);
            preparedStatement.setInt(2, (page - 1) * 10);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Topic topic = new Topic();
                topic.setId(resultSet.getInt(1));
                topic.setUid(resultSet.getInt(2));
                topic.setTitle(resultSet.getString(3));
                String content = resultSet.getString(4);
                topic.setContent(JSONObject.parse(content));
                topic.setType(resultSet.getInt(5));
                topic.setCreateTime(resultSet.getDate(6));
                topic.setUpdateTime(resultSet.getDate(7));
                topicList.add(topic);
            }
            JDBCUtil.close(resultSet, preparedStatement, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return topicList;
    }

    @Override
    public int selectCountTopicInteractById(String interact, Integer id) {
        String sql = "select count(*) from topic_interact_" + interact + " where tid = ?";
        int count = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public Topic selectTopicById(Integer tid) {
        Topic topic = new Topic();
        String sql = "select id, uid, title, content, type, create_time, update_time from topic where id = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topic.setId(resultSet.getInt(1));
                topic.setUid(resultSet.getInt(2));
                topic.setTitle(resultSet.getString(3));
                topic.setContent(JSONObject.parseObject(resultSet.getString(4)));
                topic.setType(resultSet.getInt(5));
                topic.setCreateTime(resultSet.getDate(6));
                topic.setUpdateTime(resultSet.getDate(7));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return topic;
    }

    @Override
    public boolean selectTopicInteractByTidAndUid(Integer tid, Integer id, String type) {

        String sql = "select * from topic_interact_" + type + " where tid = ? and uid = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tid);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            JDBCUtil.close(resultSet, preparedStatement, connection);
            return next;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateTopicById(Topic topic) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "update topic set title = ?,content = ?,type = ?,update_time = ? where id =  ? and uid = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, topic.getTitle());
            preparedStatement.setString(2, String.valueOf(topic.getContent()));
            preparedStatement.setInt(3, topic.getType());
            preparedStatement.setString(4, dateFormat.format(topic.getUpdateTime()));
            preparedStatement.setInt(5, topic.getId());
            preparedStatement.setInt(6, topic.getUid());
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement, connection);
            return i > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<TopicCollectPreviewVo> selectCollectTopicsByUid(Integer uid) {
        List<TopicCollectPreviewVo> vos = new ArrayList<>();
        String sql = "SELECT t.id,t.title FROM " + "topic t,topic_interact_collect tc " + "WHERE t.id = tc.tid\n" + "AND tc.uid  = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TopicCollectPreviewVo vo = new TopicCollectPreviewVo();
                vo.setId(resultSet.getInt(1));
                String title = resultSet.getString(2);
                if (title.length() > 30) {
                    title = title.substring(0, 25) + "...";
                }
                vo.setTitle(title);
                vos.add(vo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return vos;
    }

    @Override
    public boolean insertInteract(Integer tid, Integer uid, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "insert into topic_interact_" + type + " (tid,uid,create_time) values (?,?,?)";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tid);
            preparedStatement.setInt(2, uid);
            preparedStatement.setString(3, dateFormat.format(new Date()));
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement,connection);
            return i>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteInteract(Integer tid, Integer uid, String type) {
        String sql = "delete from topic_interact_" + type + " where tid = ? and uid = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tid);
            preparedStatement.setInt(2, uid);
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement,connection);
            return i>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
