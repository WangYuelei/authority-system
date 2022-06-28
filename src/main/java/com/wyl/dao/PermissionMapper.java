package com.wyl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id查询权限菜单列表
     *
     * @param userId
     * @return
     */
    List<Permission> findPermissionByUserId(Long userId);

    List<Permission> findPermissionList(Map permissionQueryVo);
    /**
     * 根据角色ID查询权限列表
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(Long roleId);
}
