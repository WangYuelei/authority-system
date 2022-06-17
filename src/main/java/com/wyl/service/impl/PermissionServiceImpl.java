package com.wyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyl.dao.PermissionMapper;
import com.wyl.entity.Permission;
import com.wyl.service.PermissionService;
import com.wyl.utils.MenuTree;
import com.wyl.vo.query.PermissionQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionByUserId(Long userId) {
        return baseMapper.findPermissionByUserId(userId);
    }

    /**
     * 查询菜单列表
     *
     * @param permissionQueryVo
     * @return
     */
    @Override
    public List<Permission> findPermissionList(PermissionQueryVo permissionQueryVo) {
        Map<Object, Object> params = new HashMap<>();
        params.put("orderBy", "order_num");
        //调用查询菜单列表的方法
        List<Permission> permissionList = permissionMapper.findPermissionList(params);
        //生成菜单树
        List<Permission> menuTree = MenuTree.makeMenuTree(permissionList, 0L);
        //返回数据
        return menuTree;
    }

    /**
     * 查询上级菜单
     *
     * @return
     */
    @Override
    public List<Permission> findParentPermissionList() {
        //构建查询对象
        Map<Object, Object> params = new HashMap<>();
        params.put("orderBy", "order_num");
        params.put("type", Arrays.asList(0, 1));
        //查询菜单列表
        List<Permission> permissionList = permissionMapper.findPermissionList(params);
        //构建顶级菜单信息,如果数据聚众的菜单表没有数据,选择上级菜单时显示顶级菜单
        Permission permission = new Permission();
        permission.setId(0L);
        permission.setParentId(-1L);
        permission.setLabel("顶级菜单");
        //将顶级菜单添加到集合
        permissionList.add(permission);
        //生成菜单数据
        List<Permission> menuTree = MenuTree.makeMenuTree(permissionList, -1L);
        return menuTree;
    }

    /**
     * 检查是否有子菜单
     *
     * @param id
     * @return
     */
    @Override
    public boolean hasChildrenOfPermission(Long id) {
        //创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //查询父级ID
        queryWrapper.eq("parent_id", id);
        //判断数量是否大于0，如果大于0则表示存在
        if (baseMapper.selectCount(queryWrapper) > 0) {
            return true;
        }
        return false;
    }
}
