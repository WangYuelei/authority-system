package com.wyl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.entity.Role;
import com.wyl.vo.query.RoleQueryVo;

public interface RoleService extends IService<Role> {
    /**
     * 根据用户查询角色列表 非管理员只能查询自己创建的角色信息。
     * @param page
     * @param roleQueryVo
     * @return
     */
    IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVo roleQueryVo);
}
