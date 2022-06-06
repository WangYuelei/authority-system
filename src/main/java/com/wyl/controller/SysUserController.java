package com.wyl.controller;

import com.wyl.config.redis.RedisService;
import com.wyl.entity.Permission;
import com.wyl.entity.User;
import com.wyl.entity.UserInfo;
import com.wyl.utils.JwtUtils;
import com.wyl.utils.MenuTree;
import com.wyl.utils.Result;
import com.wyl.vo.RouterVo;
import com.wyl.vo.TokenVo;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisService redisService;

    /**
     * 刷新token的方法
     *
     * @param request
     * @return
     */
    @PostMapping("/refreshToken")
    public Result<?> refreshToken(HttpServletRequest request) {
        //从header中获取token信息
        String token = request.getHeader("token");
        //判断header中是否存在token信信信
        if (ObjectUtils.isEmpty(token)) {
            //从请求参数中获取token
            token = request.getParameter("token");
        }
        //从spring security中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户身份信息
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        //定义变量保存新的token信息
        String newToken = "";
        //验证提交过来的token信息是否合法
        if (jwtUtils.validateToken(token, principal)) {
            //重新生成token
            newToken = jwtUtils.refreshToken(token);
        }
        //获取本次token的过期时间
        long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(newToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        //清除原来的token
        String oldTokenKey = "token_" + token;
        redisService.del(oldTokenKey);
        //将新的token信息保存到缓存中
        String newTokenKey = "token_" + newToken;
        redisService.set(newTokenKey, newToken, jwtUtils.getExpiration() / 1000);
        //创建TokeVo对象
        TokenVo tokenVo = new TokenVo(expireTime, newToken);
        //返回数据
        return Result.ok(tokenVo).message("token刷新成功");
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    @GetMapping("/getInfo")
    public Result<?> getInfo() {
        //从Spring Security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //判单身份信息authentication是否为空
        if (authentication == null) {
            return Result.error().message("用户信息查询失败");
        }
        //获取用户信息
        User user = (User) authentication.getPrincipal();
        //获取用户拥有的权限信息
        List<Permission> permissionList = user.getPermissionList();
        //获取权限编码
        Object[] roles = permissionList.stream().filter(Objects::nonNull).map(Permission::getCode).toArray();
        //创建用户信息
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), user.getAvatar(), null, roles);
        //返回数据
        return Result.ok(userInfo).message("查询用户信息成功");

    }

    /**
     * 获取登录用户的菜单数据
     *
     * @return
     */
    @GetMapping("/getMenuList")
    public Result<?> getMenuList() {
        //从Spring Security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        User user = (User) authentication.getPrincipal();
        //获取用户拥有的权限信息
        List<Permission> permissionList = user.getPermissionList();
        //筛选当前用户拥有的目录和菜单数据
        List<Permission> collect = permissionList.stream()
                //只筛选目录和菜单数据,按钮不需要添加到路由菜单中 0:目录 1:菜单 2:按钮
                .filter(item -> item != null && item.getType() != 2)
                .collect(Collectors.toList());
        //生成路由数据
        List<RouterVo> routerVoList = MenuTree.makeRouter(collect, 0L);
        //返回数据
        return Result.ok(routerVoList).message("菜单数据获取成功");
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request, HttpServletResponse response) {
        //获取token信息
        String token = request.getHeader("token");
        //如果头部信息没post携带token,则从参数中获取
        if (ObjectUtils.isEmpty(token)) {
            //从参数中获取token
            token = request.getParameter("token");
        }
        //从spring security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息是否为空,如果不为空,则需要清空用户数据
        if (authentication != null) {
            //清除用户信息
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            //清除redis缓存的token
            redisService.del("token_" + token);
            return Result.ok().message("用户退出登录成功");
        }
        return Result.error().message("用户退出失败");
    }
}
