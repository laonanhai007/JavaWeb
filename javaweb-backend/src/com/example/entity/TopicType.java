package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (TopicType)表实体类
 *
 * @author makejava
 * @since 2023-12-04 17:09:57
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicType {
    
    private Integer id;
    //类型名称
    private String name;
    //类型介绍
    private String desc;
    //类型颜色标识
    private String color;

}

