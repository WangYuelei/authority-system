package com.wyl.utils;

import com.wyl.entity.Permission;
import com.wyl.vo.RouterVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 菜单树工具类
 */
public class MenuTree {
    /**
     * 生成路由
     *
     * @param menuList 菜单列表
     * @param pid      父菜单id
     * @return List<RouterVo>
     */
    public static List<RouterVo> makeRouter(List<Permission> menuList, Long pid) {
        //创建集合保存路由信息
        List<RouterVo> routerVosList = new ArrayList<>();
        //判断菜单列表是否为空,如果不为空则使用菜单列表,否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                //筛选不为空的菜单及与菜单父id相同的数据
                .stream().filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    //创建路由对象信息
                    RouterVo routerVo = new RouterVo();
                    //路由名称
                    routerVo.setName(item.getName());
                    //路由地址
                    routerVo.setPath(item.getPath());
                    //判断当前菜单是否是一级菜单
                    if (item.getParentId() == 0L) {
                        //一级菜单组件
                        routerVo.setComponent("Layout");
                        //显示路由
                        routerVo.setAlwaysShow(true);
                    } else {
                        //具体某一个组件
                        routerVo.setComponent(item.getUrl());
                        //折叠路由
                        routerVo.setAlwaysShow(false);
                    }
                    //设置Meta信息
                    routerVo.setMeta(routerVo.new Meta(item.getLabel(), item.getIcon(), item.getCode().split(",")));
                    //递归生成路由
                    List<RouterVo> children = makeRouter(menuList, item.getId());
                    //设置子路由带路由对象中
                    routerVo.setChildren(children);
                    //将路由信息添加到集合中
                    routerVosList.add(routerVo);
                });
        //返回路由信息
        return routerVosList;
    }

    /**
     * 生成菜单树
     *
     * @param menuList 菜单列表
     * @param pid      父菜单id
     * @return List<Permission>
     */
    public static List<Permission> makeMenuTree(List<Permission> menuList, Long pid) {
        //创建集合保存菜单数据
        List<Permission> permissionList = new ArrayList<>();
        //判断菜单列表是否为空,如果不为空则使用菜单列表,否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && Objects.equals(item.getParentId(), pid))
                .forEach(item -> {
                    //创建权限菜单对象
                    Permission permission = new Permission();
                    //将原有的属性赋值给菜单对象
                    BeanUtils.copyProperties(item, permission);
                    //获取每一个item对象的子菜单,递归生成菜单树
                    List<Permission> children = makeMenuTree(menuList, item.getId());
                    //设置子菜单
                    permission.setChildren(children);
                    //讲菜单对象放入到集合
                    permissionList.add(permission);
                });
        //返回菜单信息
        return permissionList;
    }
}
