package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (AccountPrivacy)表实体类
 *
 * @author makejava
 * @since 2023-12-04 15:27:41
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPrivacy {
    
    private Integer id;
    
    private Integer uid;
    //1是 0否
    private Integer isShowGender;
    
    private Integer isShowQq;
    
    private Integer isShowWx;
    
    private Integer isShowBlog;
    
    private Integer isShowSpecialty;
    
    private Integer isShowGrade;
    
    private Integer isShowPhone;

}

