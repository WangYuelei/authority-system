package com.wyl.controller;


import com.wyl.entity.Permission;
import com.wyl.service.PermissionService;
import com.wyl.utils.Result;
import com.wyl.vo.query.PermissionQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 查询菜单列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result getMenuList(PermissionQueryVo permissionQueryVo) {
        //查询菜单列表
        List<Permission> permissionList = permissionService.findPermissionList(permissionQueryVo);
        //返回数据
        return Result.ok(permissionList);
    }

    /**
     * 查询上级菜单列表
     *
     * @return
     */
    @GetMapping("/parent/list")
    public Result getParentList() {
        //查询上级菜单列表
        List<Permission> permissionList = permissionService.findParentPermissionList();
        //返回数据
        return Result.ok(permissionList);
    }

    /**
     * 根据id查询菜单信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getMenuById(@PathVariable("id") Long id) {
        return Result.ok(permissionService.getById(id));
    }
}

