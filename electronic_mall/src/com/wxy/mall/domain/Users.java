package com.wxy.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户表
 *
 * @author 魏心怡
 * @version 1.0.0
 * @date 2023/5/30
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码， 加密
     */
    private String passWord;

    /**
     * 电话
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
