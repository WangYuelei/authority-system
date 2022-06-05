package com.wyl.dao;

import com.wyl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id查询权限菜单列表
     *
     * @param userId
     * @return
     */
    List<Permission> findPermissionByUserId(Long userId);
}
