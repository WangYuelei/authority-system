package com.wyl.controller;

import com.wyl.config.redis.RedisService;
import com.wyl.utils.JwtUtils;
import com.wyl.utils.Result;
import com.wyl.vo.TokenVo;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
}
