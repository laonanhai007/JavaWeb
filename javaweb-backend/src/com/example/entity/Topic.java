package com.example.entity;

import java.util.Date;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private Integer id;

    private Integer uid;
    //帖子标题
    private String title;
    //帖子内容
    private JSONObject content;
    //帖子类型
    private Integer type;
    //发表时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}

