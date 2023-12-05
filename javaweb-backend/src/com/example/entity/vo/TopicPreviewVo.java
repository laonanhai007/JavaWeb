package com.example.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicPreviewVo {
    Integer id;
    String title;
    String content;
    Integer type;
    Date createTime;
    int collect;
    int like;
    AccountPreviewVo account;
}
