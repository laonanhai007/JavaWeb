package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (AccountDetails)表实体类
 *
 * @author makejava
 * @since 2023-12-03 19:13:10
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {

    private Integer id;

    private Integer uid;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    //1为男,0为女
    private Integer gender;
    //qq号
    private String qq;
    //微信号
    private String wx;
    //个人博客地址
    private String blog;
    //专业
    private String specialty;
    //年级
    private String grade;
    //    电话
    private String phone;
    //个人简介
    private String desc;

}

