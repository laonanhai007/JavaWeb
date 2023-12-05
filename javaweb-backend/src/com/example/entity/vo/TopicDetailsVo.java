package com.example.entity.vo;

import com.example.entity.AccountDetails;
import com.example.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDetailsVo {
    Topic topic;
    AccountDetails account;
}
