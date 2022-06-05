package com.wyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    /**
     * 根据用户id查询权限菜单列表
     *
     * @param userId
     * @return
     */
    List<Permission> findPermissionByUserId(Long userId);

}
