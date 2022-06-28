package com.wyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyl.dao.PermissionMapper;
import com.wyl.dao.UserMapper;
import com.wyl.entity.Permission;
import com.wyl.entity.User;
import com.wyl.service.PermissionService;
import com.wyl.utils.MenuTree;
import com.wyl.vo.RolePermissionVo;
import com.wyl.vo.query.PermissionQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private UserMapper userMapper;

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
        params.put("orderBy", "orderNum");
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

    @Override
    public RolePermissionVo findPermissionTree(Long userId, Long roleId) {
        //1.查询当前用户信息
        User user = userMapper.selectById(userId);
        List<Permission> list = null;
        //2.判断当前用户角色.如果是管理员,则查询所有权限;如果不是管理员,则只查询自己所拥有的权限
        if (!ObjectUtils.isEmpty(user.getIsAdmin()) && user.getIsAdmin() == 1) {
            //查询所有权限
            list = baseMapper.selectList(null);
        } else {
            //根据用户ID查询
            list = baseMapper.findPermissionByUserId(userId);
        }
        //3.组装成数数据
        List<Permission> permissionList = MenuTree.makeMenuTree(list, 0l);
        //4.查询要分配角色的原有权限
        List<Permission> rolePermissions = baseMapper.findPermissionByRoleId(roleId);
        //5.找出该角色存在的数据
        List<Long> listIds = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull) //等同于obj->obj!=null;
                .forEach(item -> {
                    Optional.ofNullable(rolePermissions).orElse(new ArrayList<>())
                            .stream()
                            .filter(Objects::nonNull)
                            .forEach(obj -> {
                                if (item.getId().equals(obj.getId())) {
                                    listIds.add(obj.getId());
                                    return;
                                }
                            });
                });
        //创建
        RolePermissionVo vo = new RolePermissionVo();
        vo.setPermissionList(permissionList);
        vo.setCheckList(listIds.toArray());
        return vo;
    }
}
