package com.wyl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 角色权限集合
     */
    private Object[] roles;
}
