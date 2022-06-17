package com.wyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.entity.Permission;
import com.wyl.vo.query.PermissionQueryVo;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    /**
     * 根据用户id查询权限菜单列表
     *
     * @param userId
     * @return
     */
    List<Permission> findPermissionByUserId(Long userId);

    /**
     * 查询菜单列表
     * @param permissionQueryVo
     * @return
     */
     List<Permission> findPermissionList(PermissionQueryVo permissionQueryVo);

    /**
     * 查询上级菜单
     * @return
     */
     List<Permission> findParentPermissionList();

    /**
     * 检查是否有子菜单
     * @param id
     * @return
     */
     boolean hasChildrenOfPermission(Long id);
}
