package com.wyl.config.security.service;

import com.wyl.entity.Permission;
import com.wyl.entity.User;
import com.wyl.service.PermissionService;
import com.wyl.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户认证处理器
 */
@Component
public class CustomerUserDetailsService implements UserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用根据用户名查询用户信息的方法
        User user = userService.findUserByUserName(username);
        //如果对象为空,则认证失败
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        //查询当前登录用户的权限列表
        List<Permission> permissionList = permissionService.findPermissionByUserId(user.getId());
        //获取对应的权限编码
        List<String> codeList = permissionList.stream()
                .filter(Objects::nonNull)
                .map(Permission::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        //将权限编码列表转换成数据
        String[] codes = codeList.toArray(new String[codeList.size()]);
        //设置权限列表
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(codes);
        //将权限列表放入到user中
        user.setAuthorities(authorityList);
        //设置用户拥有的菜单信息
        user.setPermissionList(permissionList);
        return user;
    }
}
